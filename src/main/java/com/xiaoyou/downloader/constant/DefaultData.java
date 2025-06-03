package com.xiaoyou.downloader.constant;

import com.xiaoyou.downloader.DownloadListener;

public class DefaultData {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";

    // public static void main(String[] args) {
    //     System.out.println(RED + "这是红色文本" + RESET);
    //     System.out.println(GREEN + "这是绿色文本" + RESET);
    //     System.out.println(YELLOW + "这是黄色文本" + RESET);
    //     System.out.println(BLUE + "这是蓝色文本" + RESET);
    // }


    // 默认下载路径
    public static final String DEFAULT_SAVE_DIR = System.getProperty("user.dir") + "/download/";
    // 默认开启的线程数
    public static final Integer DEFAULT_THREAD_COUNT = 3;
    // 默认的回调函数实现
    public static final DownloadListener DEFAULT_LISTENER = new DownloadListener() {

        @Override
        public void onProgress(String fileName, int percent) {

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
            // 设定文件名宽度为 20 个字符，左对齐 (%-20s)

            String output = String.format("\r文件 %-40s 下载进度: [%s] %3d%%", fileName, bar, percent);

            System.out.println(output);

            if (percent == 100) {
                onSuccess(fileName);
            }
        }

        // @Override
        // public void onProgress(String taskId, int percent) {
        //     int barWidth = 30;
        //     int doneWidth = (percent * barWidth) / 100;
        //     StringBuilder bar = new StringBuilder();
        //     for (int i = 0; i < doneWidth; i++) bar.append("#");
        //     for (int i = doneWidth; i < barWidth; i++) bar.append(" ");
        //
        //     synchronized (System.out) {
        //         // 光标上移到对应行首
        //         System.out.print("\033[" + lineNumberFromBottom + "F"); // 上移 N 行到该任务行
        //         System.out.print("\r任务 " + taskId + " 下载进度: [" + bar + "] " + percent + "%");
        //
        //         // 光标回到底部
        //         System.out.print("\033[" + lineNumberFromBottom + "E");
        //         System.out.flush();
        //     }
        //
        //     System.out.println(); // 成功时可在最底部打印新行
        //     if (percent == 100) {
        //         // System.out.println(); // 成功时可在最底部打印新行
        //         onSuccess(taskId);
        //     }
        // }

        @Override
        public void onSuccess(String fileName) {
            String output = String.format(GREEN + "\r文件 %-40s %-10s" + RESET, fileName, "下载成功！");
            System.out.println(output);
        }

        @Override
        public void onFailed(String fileName, String reason) {
            String output = String.format(RED + "\r文件 %-40s 下载失败，原因： %-10s" + RESET, fileName, reason);
            System.out.println(output);
        }

        @Override
        public void onChecksumFailed(String fileName) {
            String output = String.format(RED + "\r文件 %-40s %-10s" + RESET, fileName, Error.MD5_FILE_VERIFICATION_FAILED + RESET);
            System.out.println(output);
        }

        @Override
        public void onChecksumSuccess(String fileName) {
            String output = String.format(GREEN + "\r文件 %-40s %-10s" + RESET, fileName, Error.MD5_FILE_VERIFICATION_SUCCESS + RESET);
            System.out.println(output);
        }

        @Override
        public void onFileExists(String fileName) {
            String output = String.format(YELLOW + "\r文件 %-40s %-10s" + RESET, fileName, Error.FILE_ALREADY_EXISTS + RESET);
            System.out.println(output);
        }

        @Override
        public void onNotSupportResume(String fileName) {
            String output = String.format(RED + "\r文件 %-40s %-10s" + RESET, fileName, Error.FILE_NOT_SUPPORT_RESUME + RESET);
            System.out.println(output);
        }
    };
}
