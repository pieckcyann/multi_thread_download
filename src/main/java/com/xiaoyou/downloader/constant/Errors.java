package com.xiaoyou.downloader.constant;

public class Errors {
    // 无法获取文件长度
    public static final String GET_FILE_SIZE_FAILED = "无法获取文件长度";
    // 服务器不支持断点续传
    public static final String FILE_NOT_SUPPORT_RESUME = "服务器不支持断点续传";
    // MD5 文件校验失败
    public static final String MD5_FILE_VERIFICATION_FAILED  = "MD5 校验失败！";
    // 文件已存在
    public static final String FILE_ALREADY_EXISTS  = "文件已存在！";
}
