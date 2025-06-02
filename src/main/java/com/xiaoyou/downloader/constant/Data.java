package com.xiaoyou.downloader.constant;

import com.xiaoyou.downloader.DownloadListener;

public class Data {
    
    // 默认下载路径
    public static final String DEFAULT_SAVE_DIR = System.getProperty("user.dir") + "/download";
    // 默认开启的线程数
    public static final Integer DEFAULT_THREAD_COUNT = 3;
    // 默认的 MD5 值 (空)
    public static final String DEFAULT_EXPECTED_MD5 = null;
    // 默认的回调函数实现
    public static final DownloadListener DEFAULT_LISTENER = new DownloadListener() {
        @Override
        public void onProgress(String taskId, int percent) {
            int barWidth = 30; // 进度条长度
            int doneWidth = (percent * barWidth) / 100;
            StringBuilder bar = new StringBuilder();
            for (int i = 0; i < doneWidth; i++) {
                bar.append("#");
            }
            for (int i = doneWidth; i < barWidth; i++) {
                bar.append(" ");
            }
            // \r回车不换行打印
            System.out.print("\r任务 " + taskId + " 下载进度: [" + bar + "] " + percent + "%");
            if (percent == 100) {
                System.out.println(); // 换行
            }
        }
        
        @Override
        public void onSuccess(String taskId) {
            System.out.println("任务 " + taskId + " 下载成功！");
        }
        
        @Override
        public void onFailed(String taskId, String reason) {
            System.out.println("任务 " + taskId + " 下载失败，原因：" + reason);
        }
        
        @Override
        public void onFileExists(String taskId) {
            System.out.println("任务 " + taskId + Errors.FILE_ALREADY_EXISTS);
        }
        
        @Override
        public void onChecksumFailed(String taskId) {
            System.out.println("任务 " + taskId + Errors.MD5_FILE_VERIFICATION_FAILED);
        }
        
        @Override
        public void onIsSupportResume(Boolean isSupport, String taskId) {
            if (!isSupport) System.out.println("任务 " + taskId + Errors.FILE_NOT_SUPPORT_RESUME);
        }
    };
    ;
}
