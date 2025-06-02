package com.xiaoyou.downloader.dto;

import com.xiaoyou.downloader.DownloadListener;

public class DownloadTaskData {
    private final String url;
    private final String savePath;
    private final int threadCount;
    private final String expectedMd5;
    private final DownloadListener listener;
    
    /**
     * @param url         // 下载文件的 URL
     */
    public DownloadTaskData(String url) {
        this.url = url;
        this.savePath = null;
        this.threadCount = 0;
        this.expectedMd5 = null;
        this.listener = null;
    }
    
    /**
     * @param url         // 下载文件的 URL
     * @param savePath    // 保存的路径 (包括文件名)
     */
    public DownloadTaskData(String url, String savePath) {
        this.url = url;
        this.savePath = savePath;
        this.threadCount = 0;
        this.expectedMd5 = null;
        this.listener = null;
    }
    
    /**
     * @param url         // 下载文件的 URL
     * @param savePath    // 保存的路径 (包括文件名)
     * @param threadCount // 指定的线程数
     */
    public DownloadTaskData(String url, String savePath, int threadCount) {
        this.url = url;
        this.savePath = savePath;
        this.threadCount = threadCount;
        this.expectedMd5 = null;
        this.listener = null;
    }
    /**
     * @param url         // 下载文件的 URL
     * @param savePath    // 保存的路径 (包括文件名)
     * @param threadCount // 指定的线程数
     * @param expectedMd5 // 预期的文件 MD5 校验值
     */
    public DownloadTaskData(String url, String savePath, int threadCount, String expectedMd5) {
        this.url = url;
        this.savePath = savePath;
        this.threadCount = threadCount;
        this.expectedMd5 = expectedMd5;
        this.listener = null;
    }

    /**
     * @param url         // 下载文件的 URL
     * @param savePath    // 保存的路径 (包括文件名)
     * @param threadCount // 指定的线程数
     * @param expectedMd5 // 预期的文件 MD5 校验值
     * @param listener    // 回调接口的实现类
     */
    public DownloadTaskData(String url, String savePath, int threadCount, String expectedMd5, DownloadListener listener) {
        this.url = url;
        this.savePath = savePath;
        this.threadCount = threadCount;
        this.expectedMd5 = expectedMd5;
        this.listener = listener;
    }

    public String getUrl() {
        return url;
    }

    public String getSavePath() {
        return savePath;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public String getExpectedMd5() {
        return expectedMd5;
    }

    public DownloadListener getListener() {
        return listener;
    }
}
