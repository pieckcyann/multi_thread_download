package com.xiaoyou.downloader;

import com.xiaoyou.downloader.constant.Errors;
import com.xiaoyou.downloader.utils.MD5Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.xiaoyou.downloader.utils.HttpUtils.*;

public class DownloadTask implements Runnable {
    private final String taskId;
    private final String url;
    private final String savePath;
    private final int threadCount;
    private final String expectedMd5;
    private final DownloadListener listener;

    public DownloadTask(String taskId, String url, String savePath, int threadCount, String expectedMd5, DownloadListener listener) {
        this.taskId = taskId;
        this.url = url;
        this.savePath = savePath;
        this.threadCount = threadCount;
        this.expectedMd5 = expectedMd5;
        this.listener = listener;
    }

    // 启动下载任务
    public void run() {
        try {
            System.out.println("任务 " + taskId + " 启动中...");
            File finalFile = new File(savePath);
            if (finalFile.exists()) {
                System.out.println("当前任务文件已存在！");

                String fileMd5 = MD5Utils.getFileMD5(finalFile);
                if (!fileMd5.equalsIgnoreCase(expectedMd5)) {
                    listener.onChecksumFailed(taskId);
                    return;
                } else {
                    System.out.println("MD5 校验成功：" + fileMd5 + "=" + expectedMd5);
                }

                return;
            }

            // 1. 获取文件总长度 (方便线程划分和进度统计)
            long fileSize = 0;
            try {
                fileSize = getFileSize(url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (fileSize <= 0) {
                listener.onFailed(taskId, Errors.GET_FILE_SIZE_FAILED);
                return;
            }

            // 2. 检查是否支持断点续传 (206)
            boolean isSupportResume = checkResumeSupport(url);

            listener.onIsSupportResume(isSupportResume, taskId); // 不支持断点续传 回调

            if (!isSupportResume) {
                listener.onFailed(taskId, Errors.FILE_NOT_SUPPORT_RESUME);
                return;
            }

            // 3. 划分线程区间
            long partSize = fileSize / threadCount;

            // 4.  创建线程数组
            File[] tempFiles = new File[threadCount];
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);

            List<Future<?>> futures = new ArrayList<>();

            // 5. 文件分片
            for (int i = 0; i < threadCount; i++) {
                long start = i * partSize;
                long end = (i == threadCount - 1) ? fileSize - 1 : (start + partSize - 1);


                File partFile = new File(savePath + ".part" + i);

                File parentDir = partFile.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    if (!parentDir.mkdirs()) {
                        throw new IOException("无法创建保存目录: " + parentDir.getAbsolutePath());
                    }
                }

                tempFiles[i] = partFile;

                // 线程池提交任务
                futures.add(executor.submit(
                        () -> {
                            new DownloadWorker(url, start, end, partFile).download();
                        }
                ));
            }

            // 等待所有任务完成
            for (Future<?> future : futures) {
                try {
                    future.get(); // 也可以捕获异常
                } catch (Exception e) {
                    listener.onFailed(taskId, "线程执行异常: " + e.getMessage());
                    executor.shutdownNow();
                    return;
                }
            }

            // 关闭线程池
            executor.shutdown();

            for (File part : tempFiles) {
                if (!part.exists()) {
                    listener.onFailed(taskId, "分片文件缺失: " + part.getAbsolutePath());
                    return;
                }
            }

            // 合并分片
            mergeFiles(List.of(tempFiles), finalFile);

            System.out.println(taskId + " 的合并已完成！");

            // MD5 校验
            String fileMd5 = MD5Utils.getFileMD5(finalFile);
            if (!fileMd5.equalsIgnoreCase(expectedMd5)) {
                listener.onChecksumFailed(taskId);
                return;
            } else {
                System.out.println("MD5 校验成功：" + fileMd5 + "=" + expectedMd5);
            }

            listener.onSuccess(taskId);

        } catch (Exception e) {
            listener.onFailed(taskId, e.getMessage());
            e.printStackTrace();
        }
    }
}
