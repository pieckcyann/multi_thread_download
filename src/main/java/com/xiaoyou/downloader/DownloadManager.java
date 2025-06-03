package com.xiaoyou.downloader;

import com.xiaoyou.downloader.dto.DownloadTaskData;
import com.xiaoyou.downloader.exception.DownloadException;

import java.io.IOException;

/**
 * 下载任务管理器接口，提供添加下载任务、启动任务等操作方法。
 */
public interface DownloadManager {

    /**
     * 添加一个下载任务，仅指定下载地址和校验的 MD5 值 (默认保存到带上钱项目目录下，线程数为 3)。
     *
     * @param url         下载地址
     * @param expectedMd5 期望的文件 MD5 值，用于完整性校验
     * @return 生成的任务 ID
     * @throws DownloadException 添加任务失败时抛出
     */
    default String addTask(String url, String expectedMd5) throws DownloadException, IOException {
        return addTask(new DownloadTaskData(url, expectedMd5));
    }

    /**
     * 添加一个下载任务，指定保存路径 (默认线程数为 3)。
     *
     * @param url         下载地址
     * @param expectedMd5 期望的文件 MD5 值
     * @param savePath    文件保存路径
     * @return 生成的任务 ID
     * @throws DownloadException 添加任务失败时抛出
     */
    default String addTask(String url, String expectedMd5, String savePath) throws DownloadException, IOException {
        return addTask(new DownloadTaskData(url, expectedMd5, savePath));
    }

    /**
     * 添加一个下载任务，指定保存路径和线程数。
     *
     * @param url         下载地址
     * @param expectedMd5 期望的文件 MD5 值
     * @param savePath    文件保存路径
     * @param threadCount 下载线程数
     * @return 生成的任务 ID
     * @throws DownloadException 添加任务失败时抛出
     */
    default String addTask(String url, String expectedMd5, String savePath, Integer threadCount) throws DownloadException, IOException {
        return addTask(new DownloadTaskData(url, expectedMd5, savePath, threadCount));
    }

    /**
     * 添加一个下载任务，指定保存路径、线程数、以及自定义的下载监听器。
     *
     * @param url         下载地址
     * @param expectedMd5 期望的文件 MD5 值
     * @param savePath    文件保存路径
     * @param threadCount 下载线程数
     * @param listener    下载进度监听器
     * @return 生成的任务 ID
     * @throws DownloadException 添加任务失败时抛出
     */
    default String addTask(String url, String expectedMd5, String savePath, Integer threadCount, DownloadListener listener) throws DownloadException, IOException {
        return addTask(new DownloadTaskData(url, expectedMd5, savePath, threadCount, listener));
    }

    /**
     * 添加一个下载任务，传入完整的任务数据对象。
     *
     * @param data 下载任务数据对象，按顺序依次指定下载地址、MD5 值、保存路径、线程数、监听器
     * @return 生成的任务 ID
     * @throws DownloadException 添加任务失败时抛出
     */
    String addTask(DownloadTaskData data) throws DownloadException, IOException;

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
