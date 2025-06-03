package com.xiaoyou.downloader;

/**
 * 下载监听器接口，提供下载过程中的各类回调。
 */
public interface DownloadListener {

    /**
     * 下载进度回调。
     *
     * @param fileName 任务文件名
     * @param percent  当前进度百分比
     */
    void onProgress(String fileName, int percent);

    /**
     * 下载成功回调。
     *
     * @param fileName 任务文件名
     */
    void onSuccess(String fileName);

    /**
     * 下载失败回调。
     *
     * @param fileName 任务文件名
     * @param reason   失败原因
     */
    void onFailed(String fileName, String reason);

    /**
     * 文件已存在时回调。
     *
     * @param fileName 任务文件名
     */
    void onFileExists(String fileName);

    /**
     * MD5 校验失败回调。
     *
     * @param fileName 任务文件名
     */
    void onChecksumFailed(String fileName);


    /**
     * MD5 校验成功回调。
     *
     * @param fileName 任务文件名
     */
    void onChecksumSuccess(String fileName);

    /**
     * 服务器端支持断点续传回调。
     *
     * @param fileName 任务文件名
     */
    void onNotSupportResume(String fileName);

}
