package com.xiaoyou.downloader;

import com.xiaoyou.downloader.constant.DefaultData;
import com.xiaoyou.downloader.dto.DownloadTaskData;
import com.xiaoyou.downloader.exception.DownloadException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.xiaoyou.downloader.utils.HttpUtils.getFileName;

public class DefaultDownloadManager implements DownloadManager {

    private final Map<String, DownloadTask> taskMap = new HashMap<>();

    @Override
    public String addTask(String url, String expectedMd5) throws DownloadException, IOException {
        return addTask(new DownloadTaskData(
                url,
                expectedMd5,
                DefaultData.DEFAULT_SAVE_DIR + getFileName(url),
                DefaultData.DEFAULT_THREAD_COUNT,
                DefaultData.DEFAULT_LISTENER
        ));
    }

    @Override
    public String addTask(String url, String expectedMd5, String savePath) throws DownloadException, IOException {
        return addTask(new DownloadTaskData(
                url,
                expectedMd5,
                savePath,
                DefaultData.DEFAULT_THREAD_COUNT,
                DefaultData.DEFAULT_LISTENER
        ));
    }

    @Override
    public String addTask(String url, String expectedMd5, String savePath, Integer threadCount) throws DownloadException, IOException {
        return addTask(new DownloadTaskData(
                url,
                expectedMd5,
                savePath,
                threadCount,
                DefaultData.DEFAULT_LISTENER
        ));
    }

    @Override
    public String addTask(String url, String expectedMd5, String savePath, Integer threadCount, DownloadListener listener) throws DownloadException, IOException {
        return addTask(new DownloadTaskData(
                url,
                expectedMd5,
                savePath,
                threadCount,
                listener
        ));
    }

    @Override
    public String addTask(DownloadTaskData data) throws DownloadException, IOException {
        if (data == null) {
            throw new DownloadException("下载任务数据不能为空");
        }
        if (data.getUrl() == null || data.getUrl().trim().isEmpty()) {
            throw new DownloadException("下载地址不能为空");
        }
        if (data.getSavePath() == null || data.getSavePath().trim().isEmpty()) {
            throw new DownloadException("保存路径不能为空");
        }
        if (data.getThreadCount() == 0 || data.getThreadCount() <= 0) {
            throw new DownloadException("线程数必须大于 0");
        }

        String taskId = generateTaskId();
        String fileName = getFileName(data.getUrl()); // 获取文件名

        DownloadTask task = new DownloadTask(taskId, fileName, data.getUrl(), data.getSavePath(), data.getThreadCount(), data.getExpectedMd5(), data.getListener());
        taskMap.put(taskId, task);
        return taskId;
    }

    // 启动所有下载任务
    // public void startAll() {
    //     for (DownloadTask task : taskMap.values()) {
    //         task.start();
    //     }
    // }

    public void startAll() {
        for (DownloadTask task : taskMap.values()) {
            new Thread(task).start(); //  每个任务都在自己的线程中启动
        }
    }

    // 启动指定 ID 任务
    public void startTask(String taskId) {
        DownloadTask task = taskMap.get(taskId);
        if (task != null) {
            new Thread(task).start();
        }
    }

    // 生成唯一的任务 ID
    private String generateTaskId() {
        return String.valueOf(System.nanoTime()); // 随机数创建 ID 字符串
    }

}
