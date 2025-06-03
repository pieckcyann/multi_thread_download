package com.xiaoyou.downloader.utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import java.io.*;
import java.util.List;

public class HttpUtils {
    // 获取远程文件大小 (原理：发送 HEAD 请求)
    public static long getFileSize(String fileUrl) throws IOException {

        //        // 1. 建立连接
        //        URL url = new URL(fileUrl); // 远程文件的 URL
        //        URLConnection connection = url.openConnection(); // 打开连接
        //
        //        // 2. 获取文件大小
        //        int fileLength = connection.getContentLength();
        //
        //        // 3. 关闭连接
        //        connection.getInputStream().close();
        //        return fileLength;

        // 1. 建立连接
        HttpURLConnection connection = (HttpURLConnection) new URL(fileUrl).openConnection();

        // 2. 只请求头部信息
        connection.setRequestMethod("HEAD");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        // 3. 校验响应码
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return connection.getContentLengthLong();  // 获取 Content-Length
        } else {
            throw new IOException("服务器返回了异常的响应码: " + responseCode + "\n");
        }
    }

    public static String getFileName(String fileUrl) throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        // 获取 Content-Disposition 头
        String disposition = conn.getHeaderField("Content-Disposition");
        String fileName = null;

        if (disposition != null && disposition.contains("filename=")) {
            // 有些是 filename="abc.txt"，有些没有引号
            int index = disposition.indexOf("filename=");
            fileName = disposition.substring(index + 9).replaceAll("\"", "");
        } else {
            // 退而求其次：从 URL 末尾获取
            String path = url.getPath();
            fileName = path.substring(path.lastIndexOf('/') + 1);
        }

        conn.disconnect();
        return fileName;
    }

    // 判断是否支持断点续传
    public static boolean checkResumeSupport(String fileUrl) {
        try {
            // 方法一：通过发送 HEAD 请求请求文件大小信息
            // // 1. 建立连接
            // URL url = new URL(fileUrl); // 远程文件的 URL
            // HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // 打开连接
            //
            // connection.setRequestMethod("HEAD");
            //
            // connection.setConnectTimeout(10000);
            // connection.setReadTimeout(10000);
            //
            // String acceptRanges = connection.getHeaderField("Accept-Ranges");
            // System.out.println("Accept-Ranges: " + acceptRanges);
            //
            // // TODO: 额外再检查远程文件是否为空
            // // int contentLength = connection.getContentLength();
            // // System.out.println("Content-Length: " + contentLength);
            //
            // connection.disconnect();
            //
            //
            // return "bytes".equalsIgnoreCase(acceptRanges);

            // 2. 发送 "Range" 范围请求
            HttpURLConnection connection = (HttpURLConnection) new URL(fileUrl).openConnection();
            connection.setRequestProperty("Range", "bytes=0-1");  // 请求一个范围
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_PARTIAL;  // HTTP_PARTIAL = 206 表示支持断点续传

        } catch (IOException e) {
            return false;
        }
    }


    public static void mergeFiles(List<File> partFiles, File outputFile) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile))) {
            for (File partFile : partFiles) {
                try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(partFile))) {
                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = bis.read(buffer)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                    }
                }
                // 删除临时分段文件
                if (!partFile.delete()) {
                    System.err.println("无法删除临时文件：" + partFile.getAbsolutePath());
                }
            }
        }
    }


}
