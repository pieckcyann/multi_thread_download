package com.xiaoyou.demo;

import com.xiaoyou.downloader.MultiDownloader;

import java.io.IOException;

import static com.xiaoyou.demo.DemoParam.*;

public class MainDemo {

    public static void main(String[] args) throws IOException {
        // -Dhttps.protocols=TLSv1,TLSv1.1,TLSv1.2
        // System.out.println("TLS protocol: " + System.getProperty("https.protocols"));

        // 创建下载管理器
        var manager = MultiDownloader.newManager();

        // 添加任务
        manager.addTask(TestLink1, ExpectedMdfive1);
        manager.addTask(TestLink2, ExpectedMdfive2, SavePath2);
        manager.addTask(TestLink3, ExpectedMdfive3, SavePath3, ThreadCount3);
        manager.addTask(TestLink4, ExpectedMdfive4, SavePath4, ThreadCount4, listener);

        // 启动所有任务
        manager.startAll();

    }

}
