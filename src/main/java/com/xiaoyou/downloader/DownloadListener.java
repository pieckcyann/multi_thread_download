package com.xiaoyou.downloader;

public interface DownloadListener {
    // 下载进度 回调
    void onProgress(String taskId, int percent);

    // 下载成功 回调
    void onSuccess(String taskId);

    // 下载失败 回调
    void onFailed(String taskId, String reason);

    // 文件已存在 回调
    void onFileExists(String taskId);

    // MD5 校验失败 回调
    void onChecksumFailed(String taskId);

     // 目标文件是否支持断点续传 回调
     void onIsSupportResume(Boolean isSupport,String taskId);
}
