package com.xiaoyou.downloader;

/**
 * 下载监听器接口，提供下载过程中的各类回调。
 */
public interface DownloadListener {
    
    /**
     * 下载进度回调。
     *
     * @param taskId  任务 ID
     * @param percent 当前进度百分比
     */
    void onProgress(String taskId, int percent);
    
    /**
     * 下载成功回调。
     *
     * @param taskId 任务 ID
     */
    void onSuccess(String taskId);
    
    /**
     * 下载失败回调。
     *
     * @param taskId 任务 ID
     * @param reason 失败原因
     */
    void onFailed(String taskId, String reason);
    
    /**
     * 文件已存在时回调。
     *
     * @param taskId 任务 ID
     */
    void onFileExists(String taskId);
    
    /**
     * MD5 校验失败回调。
     *
     * @param taskId 任务 ID
     */
    void onChecksumFailed(String taskId);
    
    /**
     * 判断是否支持断点续传回调。
     *
     * @param isSupport 是否支持
     * @param taskId    任务 ID
     */
    void onIsSupportResume(Boolean isSupport, String taskId);
    
}
