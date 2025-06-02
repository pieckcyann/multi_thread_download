
# MultiDownloader — 多线程断点续传下载库

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

## 项目简介

MultiDownloader 是一个基于 Java 实现的多线程断点续传下载库，支持：

* 多线程并发下载，大幅提升下载速度
* 断点续传，支持服务器是否支持的检测
* 文件完整性校验（MD5）
* 下载状态回调（进度、成功、失败、文件已存在、MD5 校验失败等）
* 灵活的任务配置：支持自定义线程数、保存路径、回调监听等

设计目标是为开发者提供简单易用且稳定的文件下载能力，可集成到各种 Java 应用中。

---

## 主要功能

* 自动检测服务器支持断点续传（HTTP 206）
* 支持文件MD5校验，确保下载文件完整无误
* 支持多线程分片下载，线程数可配置
* 支持多任务管理，任务启动、暂停、失败通知
* 任务监听器提供详尽状态回调，方便界面或业务逻辑更新
* 任务数据类支持多种构造参数，灵活创建下载任务

---

## 快速开始

### 1. 添加依赖

直接将项目源码导入你的 Java 工程即可，无需额外依赖。

---

### 2. 使用示例

```java
import com.xiaoyou.downloader.MultiDownloader;
import com.xiaoyou.downloader.DownloadListener;
import com.xiaoyou.downloader.dto.DownloadTaskData;

public class Demo {
    public static void main(String[] args) {
        var manager = MultiDownloader.newManager();

        DownloadListener listener = new DownloadListener() {
            @Override
            public void onProgress(String taskId, int percent) {
                System.out.println(taskId + " 进度: " + percent + "%");
            }
            @Override
            public void onSuccess(String taskId) {
                System.out.println(taskId + " 下载成功！");
            }
            @Override
            public void onFailed(String taskId, String reason) {
                System.out.println(taskId + " 下载失败：" + reason);
            }
            @Override
            public void onFileExists(String taskId) {
                System.out.println(taskId + " 文件已存在，无需重复下载。");
            }
            @Override
            public void onChecksumFailed(String taskId) {
                System.out.println(taskId + " 文件MD5校验失败！");
            }
            @Override
            public void onIsSupportResume(Boolean isSupport, String taskId) {
                System.out.println(taskId + " 是否支持断点续传: " + isSupport);
            }
        };

        DownloadTaskData task = MultiDownloader.taskData(
            "https://example.com/file.zip",
            "D:/downloads/file.zip",
            4,
            "预期的MD5值",
            listener);

        try {
            String taskId = manager.addTask(task);
            manager.startTask(taskId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

## 项目结构

```
com.xiaoyou.downloader
├── MultiDownloader.java      # 门面类，简化接口调用
├── DownloadManager.java      # 下载管理器接口
├── DefaultDownloadManager.java # 下载管理器实现
├── DownloadTask.java         # 具体下载任务类，多线程分片下载逻辑
├── DownloadWorker.java       # 单个线程下载工作类
├── DownloadListener.java     # 任务状态监听接口
├── dto
│   └── DownloadTaskData.java # 任务数据模型
├── utils
│   ├── MD5Utils.java         # MD5计算工具
│   └── HttpUtils.java        # HTTP请求工具
├── exception
│   └── DownloadException.java # 自定义异常类
└── constant
    └── Errors.java           # 错误常量
```

---

## 设计亮点

* **门面设计**：`MultiDownloader` 作为统一入口，隐藏内部实现细节，简化用户调用
* **多线程分片**：将大文件切分为多个区块，线程池异步下载，支持断点续传
* **下载进度监听**：每个任务的实时进度以百分比形式回调给用户
* **完整性校验**：通过MD5确保文件下载完整，自动检测文件已存在及异常情况
* **异常处理**：合理捕获和通知异常，保证任务稳定执行

---

## 依赖环境

* Java 8 及以上版本
* 无需第三方库，完全纯Java实现

---

## 未来规划

* 支持下载任务暂停与恢复
* 支持更多文件校验算法（SHA-1、SHA-256）
* 支持断点续传自动重试策略
* 提供更友好的 GUI 示例与控制台演示

---

## 贡献指南

欢迎提交 Issue 和 Pull Request。请遵守代码规范，写明清晰的提交说明。

---

## 许可证

本项目采用 [MIT License](LICENSE) 开源协议，详情见 LICENSE 文件。

