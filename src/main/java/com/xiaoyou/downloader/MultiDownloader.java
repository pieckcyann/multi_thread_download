package com.xiaoyou.downloader;

/**
 * 下载库门面类，简化用户访问入口。
 * <p>
 * 使用示例：
 * <pre>
 *     DownloadManager manager = MultiDownloader.newManager();
 *     manager.addTask("http://example.com/file.zip");
 *     manager.startAll();
 * </pre>
 * <p>
 * 若需了解任务添加及管理接口，请查看 {@link com.xiaoyou.downloader.DownloadManager}
 * 若需了解监听器接口，请查看 {@link com.xiaoyou.downloader.DownloadListener}
 * 若需自定义监听器，请实现 {@link MultiDownloader.Listener}
 */
public class MultiDownloader {
    
    /**
     * 创建一个新的下载管理器实例。
     */
    public static DefaultDownloadManager newManager() {
        return new DefaultDownloadManager();
    }
    
    /**
     * 监听器接口直接暴露给用户
     */
    public interface Listener extends DownloadListener {
    
    }
    
}
