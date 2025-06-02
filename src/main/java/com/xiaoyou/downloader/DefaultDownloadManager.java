package com.xiaoyou.downloader;

import com.xiaoyou.downloader.constant.Data;
import com.xiaoyou.downloader.dto.DownloadTaskData;
import com.xiaoyou.downloader.exception.DownloadException;

import java.util.HashMap;
import java.util.Map;

public class DefaultDownloadManager implements DownloadManager {
    
    private final Map<String, DownloadTask> taskMap = new HashMap<>();
    
    @Override
    public String addTask(String url) {
        return addTask(new DownloadTaskData(
                url,
                Data.DEFAULT_SAVE_DIR + System.currentTimeMillis(),
                Data.DEFAULT_THREAD_COUNT,
                Data.DEFAULT_EXPECTED_MD5,
                Data.DEFAULT_LISTENER
        ));
    }
    
    @Override
    public String addTask(String url, String savePath) {
        return addTask(new DownloadTaskData(
                url,
                savePath,
                Data.DEFAULT_THREAD_COUNT,
                Data.DEFAULT_EXPECTED_MD5,
                Data.DEFAULT_LISTENER
        ));
    }
    
    @Override
    public String addTask(String url, String savePath, Integer threadCount) {
        return addTask(new DownloadTaskData(
                url,
                savePath,
                threadCount,
                Data.DEFAULT_EXPECTED_MD5,
                Data.DEFAULT_LISTENER
        ));
    }
    
    @Override
    public String addTask(String url, String savePath, Integer threadCount, String expectedMd5) {
        return addTask(new DownloadTaskData(
                url,
                savePath,
                threadCount,
                expectedMd5,
                Data.DEFAULT_LISTENER
        ));
    }
    
    @Override
    public String addTask(String url, String savePath, Integer threadCount, String expectedMd5, DownloadListener listener) {
        return addTask(new DownloadTaskData(
                url,
                savePath,
                threadCount,
                expectedMd5,
                listener
        ));
    }
    
    @Override
    public String addTask(DownloadTaskData data) {
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
        DownloadTask task = new DownloadTask(taskId, data.getUrl(), data.getSavePath(), data.getThreadCount(), data.getExpectedMd5(), data.getListener());
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
