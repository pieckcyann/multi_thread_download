package com.xiaoyou.demo;

import com.xiaoyou.downloader.MultiDownloader;

import static com.xiaoyou.demo.DemoParam.SavePath3;
import static com.xiaoyou.demo.DemoParam.TestLink3;

public class MainDemo {
    
    public static void main(String[] args) {
        // -Dhttps.protocols=TLSv1,TLSv1.1,TLSv1.2
        // System.out.println("TLS protocol: " + System.getProperty("https.protocols"));
        
        // 创建下载管理器
        var manager = MultiDownloader.newManager();
        
        // manager.addTask(TestLink1);
        
        // manager.addTask(TestLink2, SavePath2);
        
        manager.addTask(TestLink3, SavePath3);
        
        
        //
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
        
        
        manager.startAll();
        
        
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
    
}
