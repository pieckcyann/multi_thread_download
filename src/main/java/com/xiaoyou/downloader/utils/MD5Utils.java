package com.xiaoyou.downloader.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    // 获取文件的 MD5 值
    public static String getFileMD5(File file) throws IOException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5"); // 创建 MD5 实例，指定 MD5 算法
        try (FileInputStream fis = new FileInputStream(file)) {
//            byte[] buffer = new byte[8192]; // 字节缓冲区 (8kb)
            byte[] buffer = new byte[1024]; // 字节缓冲区 (1kb)
            int numRead;
            while ((numRead = fis.read(buffer)) > 0) { // 按照字节环缓冲去大小读取内容
                md.update(buffer, 0, numRead); // 指定使用这部分字节内容更新哈希值
            }
        }
        byte[] md5Bytes = md.digest(); // public byte[] digest() 计算得到哈希值
        StringBuilder sb = new StringBuilder(); // StringBuffer: 线程不安全，速度快

        // 转为 16 进制
        for (byte b : md5Bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
