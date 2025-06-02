package com.xiaoyou.downloader;

import com.xiaoyou.downloader.dto.DownloadTaskData;
import com.xiaoyou.downloader.exception.DownloadException;

/**
 * 下载任务管理器接口，提供添加下载任务、启动任务等操作方法。
 */
public interface DownloadManager {
    
    /**
     * 添加一个下载任务，仅指定下载地址，使用默认保存路径与配置。
     *
     * @param url 下载地址
     * @return 生成的任务 ID
     * @throws DownloadException 添加任务失败时抛出
     */
    default String addTask(String url) throws DownloadException {
        return addTask(new DownloadTaskData(url));
    }
    
    /**
     * 添加一个下载任务，指定保存路径。
     *
     * @param url      下载地址
     * @param savePath 文件保存路径
     * @return 生成的任务 ID
     * @throws DownloadException 添加任务失败时抛出
     */
    default String addTask(String url, String savePath) throws DownloadException {
        return addTask(new DownloadTaskData(url, savePath));
    }
    
    /**
     * 添加一个下载任务，指定保存路径和线程数。
     *
     * @param url         下载地址
     * @param savePath    文件保存路径
     * @param threadCount 下载线程数
     * @return 生成的任务 ID
     * @throws DownloadException 添加任务失败时抛出
     */
    default String addTask(String url, String savePath, Integer threadCount) throws DownloadException {
        return addTask(new DownloadTaskData(url, savePath, threadCount));
    }
    
    /**
     * 添加一个下载任务，指定保存路径、线程数和期望的 MD5 校验值。
     *
     * @param url         下载地址
     * @param savePath    文件保存路径
     * @param threadCount 下载线程数
     * @param expectedMd5 期望的文件 MD5 值，用于完整性校验
     * @return 生成的任务 ID
     * @throws DownloadException 添加任务失败时抛出
     */
    default String addTask(String url, String savePath, Integer threadCount, String expectedMd5) throws DownloadException {
        return addTask(new DownloadTaskData(url, savePath, threadCount, expectedMd5));
    }
    
    /**
     * 添加一个下载任务，指定保存路径、线程数、期望的 MD5 值以及下载监听器。
     *
     * @param url         下载地址
     * @param savePath    文件保存路径
     * @param threadCount 下载线程数
     * @param expectedMd5 期望的文件 MD5 值
     * @param listener    下载进度监听器
     * @return 生成的任务 ID
     * @throws DownloadException 添加任务失败时抛出
     */
    default String addTask(String url, String savePath, Integer threadCount, String expectedMd5, DownloadListener listener) throws DownloadException {
        return addTask(new DownloadTaskData(url, savePath, threadCount, expectedMd5, listener));
    }
    
    /**
     * 添加一个下载任务，传入完整的任务数据对象。
     *
     * @param data 下载任务数据对象，包含下载地址、保存路径、线程数、监听器等信息
     * @return 生成的任务 ID
     * @throws DownloadException 添加任务失败时抛出
     */
    String addTask(DownloadTaskData data) throws DownloadException;
    
    /**
     * 启动所有已添加的下载任务。
     */
    void startAll();
    
    /**
     * 启动指定任务。
     *
     * @param taskId 任务 ID
     */
    void startTask(String taskId);
}
