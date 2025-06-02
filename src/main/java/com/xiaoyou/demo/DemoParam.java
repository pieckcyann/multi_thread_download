package com.xiaoyou.demo;

import com.xiaoyou.downloader.MultiDownloader;

public class DemoParam {
    
    public static final String TestLink1 = "https://dldir1.qq.com/qqfile/qq/QQNT/Windows/QQ_9.9.19_250523_x64_01.exe";
    public static final String TestLink2 = "https://dldir1.qq.com/qqfile/qq/QQNT/Windows/QQ_9.9.19_250523_x64_01.exe";
    public static final String SavePath2 = "E:\\WORK\\xiaoyou\\01\\download\\QQ_9.9.19_250523_x64_01.exe";
    public static final String TestLink3 = "https://dldir1.qq.com/qqfile/qq/PCQQ9.7.25/QQ9.7.25.29410.exe";
    public static final String SavePath3 = "E:\\WORK\\xiaoyou\\01\\download\\QQ9.7.25.29410.exe";
    public static final Integer ThreadCount3 = 5;
    public static final MultiDownloader.Listener listener = new MultiDownloader.Listener() {        // 实现回调接口
        @Override
        public void onProgress(String taskId, int percent) {
            System.out.println("任务 " + taskId + " 下载进度: " + percent + "%");
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
            System.out.println("任务 " + taskId + "文件已存在");
        }
        
        @Override
        public void onChecksumFailed(String taskId) {
            System.out.println("任务 " + taskId + "MD5 校验失败！");
        }
        
        @Override
        public void onIsSupportResume(Boolean isSupport, String taskId) {
            if (!isSupport) System.out.println("任务 " + taskId + "服务器不支持断点续传");
        }
    };
    
    
}
