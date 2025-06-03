package com.xiaoyou.demo;

import com.xiaoyou.downloader.DownloadListener;

import static com.xiaoyou.downloader.constant.DefaultData.DEFAULT_LISTENER;

public class DemoParam {

    public static final String TestLink1 = "https://dldir1.qq.com/qqfile/qq/QQNT/Windows/QQ_9.9.19_250523_x64_01.exe";
    public static final String ExpectedMdfive1 = "F56B3DEC8272C4CC7BECDCE3236153E9";

    public static final String TestLink2 = "https://dldir1v6.qq.com/weixin/Universal/Windows/WeChatWin.exe";
    public static final String ExpectedMdfive2 = "A5225DB8ECC745B7C2FABE8E1D761B9F";
    public static final String SavePath2 = "C:\\tasks\\multi_thread_download\\download\\WeChatWin.exe";


    public static final String TestLink3 = "https://dldir1.qq.com/qqfile/qq/PCQQ9.7.25/QQ9.7.25.29410.exe";
    public static final String ExpectedMdfive3 = "678A4446C69DEC1E63042282F3159D8E";
    public static final String SavePath3 = "C:\\tasks\\multi_thread_download\\download\\QQ9.7.25.29410.exe";
    public static final Integer ThreadCount3 = 5;

    public static final String TestLink4 = "https://official-package.wpscdn.cn/wps/download/WPS_Setup_21171.exe";
    public static final String ExpectedMdfive4 = "1C97E0D5AA440BA4165417B260F7AFF2";
    public static final String SavePath4 = "C:\\tasks\\multi_thread_download\\download\\WPS_Setup_21171.exe";
    public static final Integer ThreadCount4 = 5;

    public static final DownloadListener listener = DEFAULT_LISTENER;

    // manager.addTask(TestLink3, SavePath3, ThreadCount3);
    //
    // String taskId4 = manager.addTask(
    //         "https://dldir1.qq.com/qqfile/qq/QQNT/Windows/QQ_9.9.19_250523_x86_01.exe",
    //         "E:\\WORK\\xiaoyou\\01\\download\\QQ_9.9.19_250523_x86_01.exe",
    //         5,
    //         "F56B3DEC8272C4CC7BECDCE3236153E9"
    // );
    //
    // String taskId5 = manager.addTask(
    //         "https://dldir1v6.qq.com/weixin/Universal/Windows/WeChatWin.exe",
    //         "E:\\WORK\\xiaoyou\\01\\download\\QQ_9.9.19_250523_x86_01.exe",
    //         5,
    //         "F56B3DEC8272C4CC7BECDCE3236153E9",
    //         listener
    // );


    // // 添加下载任务 1 - tencent qq
    // String taskId1 = manager.addTask(
    //         new DownloadTaskData(
    //                 "https://dldir1.qq.com/qqfile/qq/QQNT/Windows/QQ_9.9.19_250523_x64_01.exe",
    //                 "E:\\WORK\\xiaoyou\\01\\download\\QQ_9.9.19_250523_x64_01.exe",
    //                 4,
    //                 "F56B3DEC8272C4CC7BECDCE3236153E9",
    //                 listener
    //         )
    // );

    // // 添加下载任务 2 - weixin
    // String taskId2 = manager.addTask(
    //         new DownloadTaskData(
    //                 "https://dldir1v6.qq.com/weixin/Universal/Windows/WeChatWin.exe",
    //                 "E:\\WORK\\xiaoyou\\01\\download\\WeChatWin.exe",
    //                 5,
    //                 "A5225DB8ECC745B7C2FABE8E1D761B9F",
    //                 listener
    //         )
    // );

    // // 添加下载任务 3 - 2GB
    // String taskId3 = manager.addTask(
    //         new DownloadTaskData(
    //                 "https://dlied4.myapp.com/myapp/1104466820/cos.release-40109/10040714_com.tencent.tmgp.sgame_a2480356_8.2.1.9_F0BvnI.apk",
    //                 "E:\\WORK\\xiaoyou\\01\\download\\10040714_com.tencent.tmgp.sgame_a2480356_8.2.1.9_F0BvnI.apk",
    //                 10,
    //                 "B20FDF2AB70AF1F2AE67DC62981A7F3B",
    //                 listener
    //         )
    // );


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
