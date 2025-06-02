package com.xiaoyou.demo;


import com.xiaoyou.downloader.DownloadListener;
import com.xiaoyou.downloader.DownloadManager;
import com.xiaoyou.downloader.constant.Errors;
import com.xiaoyou.downloader.dto.DownloadTaskData;

import java.io.IOException;

public class MainDemo {
    public static void main(String[] args) throws IOException {

        // 创建下载管理器
        DownloadManager manager = new DownloadManager();

        // -Dhttps.protocols=TLSv1,TLSv1.1,TLSv1.2
        System.out.println("TLS protocol: " + System.getProperty("https.protocols"));

        DownloadListener listener = new DownloadListener() {        // 实现回调接口
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

        // 添加下载任务 1 - tencent qq
        String taskId1 = manager.addTask(
                new DownloadTaskData(
                        "https://dldir1.qq.com/qqfile/qq/QQNT/Windows/QQ_9.9.19_250523_x64_01.exe",
                        "E:\\WORK\\xiaoyou\\01\\download\\QQ_9.9.19_250523_x64_01.exe",
                        4,
                        "F56B3DEC8272C4CC7BECDCE3236153E9",
                        listener
                )
        );

        // 添加下载任务 2 - weixin
        String taskId2 = manager.addTask(
                new DownloadTaskData(
                        "https://dldir1v6.qq.com/weixin/Universal/Windows/WeChatWin.exe",
                        "E:\\WORK\\xiaoyou\\01\\download\\WeChatWin.exe",
                        5,
                        "A5225DB8ECC745B7C2FABE8E1D761B9F",
                        listener
                )
        );

        // 添加下载任务 3 - 2GB
        String taskId3 = manager.addTask(
                new DownloadTaskData(
                        "https://dlied4.myapp.com/myapp/1104466820/cos.release-40109/10040714_com.tencent.tmgp.sgame_a2480356_8.2.1.9_F0BvnI.apk",
                        "E:\\WORK\\xiaoyou\\01\\download\\10040714_com.tencent.tmgp.sgame_a2480356_8.2.1.9_F0BvnI.apk",
                        10,
                        "B20FDF2AB70AF1F2AE67DC62981A7F3B",
                        listener
                )
        );


        manager.startAll();

        // manager.startTask(taskId1);
        // manager.startTask(taskId2);


        // File[] tempFiles = new File[4];
        // for (int i = 0; i < 4; i++) {
        //     tempFiles[i] = new File("C:/tasks/multi_thread_download/downloads/"  + ".part" + i);
        // }
        // File finalFile = new File("C:/tasks/multi_thread_download/downloads/QQ_9.9.19_250523_x64_01.exe");
        //
        // mergeFiles(List.of(tempFiles), finalFile);

    }
}
