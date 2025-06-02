package com.xiaoyou.downloader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadWorker {
    
    private final String url;
    private final long start;
    private final long end;
    private final File partFile;
    private final ProgressCallback progressCallback;
    
    public DownloadWorker(String url, long start, long end, File partFile, ProgressCallback progressCallback) {
        this.url = url;
        this.start = start;
        this.end = end;
        this.partFile = partFile;
        this.progressCallback = progressCallback;
    }
    
    public void download() {
        // 如果分片已存在
        long existingLength = partFile.exists() ? partFile.length() : 0;
        
        long newStart = start + existingLength;
        
        if (existingLength > 0) {
            System.out.println("断点续传：从上一次的" + newStart + " 位置开始下载。");
        }
        
        // 如果分片已完成，则无需下载
        if (existingLength >= (end - start + 1)) {
            System.out.println("断点续传：当前片段已完成，无需重新下载。");
            return;
        }
        
        System.out.println("线程启动: " + Thread.currentThread().getName() + ", 下载区间: " + start + "-" + end + ", 目标文件: " + partFile.getName());
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            // conn.setRequestProperty("Range", "bytes=" + start + "-" + end);
            conn.setRequestProperty("Range", "bytes=" + newStart + "-" + end);
            int responseCode = conn.getResponseCode();
            // System.out.println("线程 " + Thread.currentThread().getName() + " 响应码: " + responseCode);
            
            if (responseCode == 206) { // HTTP_PARTIAL
                try (InputStream ins = conn.getInputStream();
                     RandomAccessFile raf = new RandomAccessFile(partFile, "rw")) {
                    // raf.seek(0); // !!!
                    raf.seek(existingLength);
                    byte[] buffer = new byte[4096];
                    int len;
                    while ((len = ins.read(buffer)) != -1) {
                        raf.write(buffer, 0, len);
                        
                        // 调用回调，通知本线程下载了多少字节
                        if (progressCallback != null) {
                            progressCallback.onProgress(len);
                        }
                    }
                }
                // System.out.println("线程 " + Thread.currentThread().getName() + " 完成写入: " + partFile.getAbsolutePath());
            } else {
                System.out.println("线程 " + Thread.currentThread().getName() + " 未能获取有效内容，响应码: " + responseCode);
            }
            
            conn.disconnect();
        } catch (IOException e) {
            System.out.println("线程 " + Thread.currentThread().getName() + " 出错: " + e.getMessage());
        }
    }
    
}
