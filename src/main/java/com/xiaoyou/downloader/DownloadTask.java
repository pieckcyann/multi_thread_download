package com.xiaoyou.downloader;

import com.xiaoyou.downloader.constant.Error;
import com.xiaoyou.downloader.utils.MD5Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

import static com.xiaoyou.downloader.constant.DefaultData.BLUE;
import static com.xiaoyou.downloader.constant.DefaultData.RESET;
import static com.xiaoyou.downloader.utils.HttpUtils.*;

public class DownloadTask implements Runnable {

    private final String taskId;
    private final String fileName;
    private final String url;
    private final String savePath;
    private final int threadCount;
    private final String expectedMd5;
    private final DownloadListener listener;

    public DownloadTask(String taskId, String fileName, String url, String savePath, int threadCount, String expectedMd5, DownloadListener listener) {
        this.taskId = taskId;
        this.fileName = fileName;
        this.url = url;
        this.savePath = savePath;
        this.threadCount = threadCount;
        this.expectedMd5 = expectedMd5;
        this.listener = listener;
    }

    // 启动下载任务
    public void run() {
        try {
            System.out.println(BLUE + "文件 " + fileName + " 开始下载..." + RESET);

            // 检查目标文件是否已存在
            File finalFile = new File(savePath);
            if (finalFile.exists()) {
                listener.onFileExists(fileName);
                listener.onFailed(fileName, "文件已存在");
                return;
            }

            //  获取文件总长度和文件名 (方便线程划分和进度统计)
            long fileSize = 0;
            try {
                fileSize = getFileSize(url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (fileSize <= 0) {
                listener.onFailed(fileName, Error.GET_FILE_SIZE_FAILED);
                return;
            }

            // 检查是否支持断点续传 (206)
            boolean isSupportResume = checkResumeSupport(url);
            if (!isSupportResume) {
                listener.onNotSupportResume(fileName); // 出发
                listener.onFailed(fileName, Error.FILE_NOT_SUPPORT_RESUME);
                return;
            }

            // 划分线程区间
            long partSize = fileSize / threadCount;

            // 存储临时分片文件
            File[] tempFiles = new File[threadCount];

            // 创建固定大小的线程池，用于并发执行多个下载线程 (每个线程负责一个文件分片)
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);

            // 保存所有下载线程的执行结果(Future 对象)，方便后续等待全部下载完成
            List<Future<?>> futures = new ArrayList<>();

            // 计算所有已存在分片的长度和
            long existingTotal = 0;
            for (int i = 0; i < threadCount; i++) {
                File partFile = new File(savePath + ".part" + i);
                if (partFile.exists()) {
                    existingTotal += partFile.length();
                }
            }

            // 初始化 AtomicLong，起始值是已下载的字节数
            AtomicLong totalDownloaded = new AtomicLong(existingTotal);
            final int[] lastPercent = {-1};  // 用数组包裹，方便匿名内部类修改

            // 5. 文件分片
            for (int i = 0; i < threadCount; i++) {
                long start = i * partSize;
                long end = (i == threadCount - 1) ? fileSize - 1 : (start + partSize - 1);


                // 创建分片
                File partFile = new File(savePath + ".part" + i);
                File parentDir = partFile.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    if (!parentDir.mkdirs()) {
                        throw new IOException("无法创建保存目录: " + parentDir.getAbsolutePath());
                    }
                }
                tempFiles[i] = partFile;

                long finalFileSize = fileSize;
                ProgressCallback progressCallback = (bytes) -> {
                    long downloaded = totalDownloaded.addAndGet(bytes);
                    int percent = (int) ((downloaded * 100) / finalFileSize);

                    // 只有进度至少增长 1% 时才回调
                    if (percent > lastPercent[0]) {
                        lastPercent[0] = percent;
                        listener.onProgress(fileName, percent);
                    }
                };

                DownloadWorker worker = new DownloadWorker(url, start, end, partFile, progressCallback);
                futures.add(executor.submit(worker::download)); // 线程池提交任务

                // futures.add(executor.submit(
                //         () -> {
                //             new DownloadWorker(url, start, end, partFile, listener).download();
                //         }
                // ));
            }

            // 等待所有任务完成
            for (Future<?> future : futures) {
                try {
                    future.get(); // 也可以捕获异常
                } catch (Exception e) {
                    listener.onFailed(fileName, "线程执行异常: " + e.getMessage());
                    executor.shutdownNow();
                    return;
                }
            }

            // 关闭线程池
            executor.shutdown();

            for (File part : tempFiles) {
                if (!part.exists()) {
                    listener.onFailed(fileName, "分片文件缺失: " + part.getAbsolutePath());
                    return;
                }
            }

            // 合并分片
            mergeFiles(List.of(tempFiles), finalFile);
            // System.out.println(taskId + " 的合并已完成！");

            // MD5 校验
            String fileMd5 = MD5Utils.getFileMD5(finalFile);
            boolean isChecksumFailed = !fileMd5.equalsIgnoreCase(expectedMd5);

            if (isChecksumFailed) {
                listener.onChecksumFailed(fileName);
            } else {
                listener.onChecksumSuccess(fileName);
            }

        } catch (Exception e) {
            listener.onFailed(fileName, e.getMessage());
            e.printStackTrace();
        }
    }

}
