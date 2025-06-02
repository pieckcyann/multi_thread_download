package com.xiaoyou.downloader;


import com.xiaoyou.downloader.dto.DownloadTaskData;

import java.util.HashMap;
import java.util.Map;

public class DownloadManager {
    private final Map<String, DownloadTask> taskMap = new HashMap<>();

    // 添加下载任务，返回任务ID
    public String addTask(DownloadTaskData data) {
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
