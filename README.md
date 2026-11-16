# Antigravity Mobile

**Android client for Google Antigravity Remote & cloud AI agent workflows.**  
**Google Antigravity 网页版与远程智能体 (Agent) 专属 Android 客户端。**

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Android](https://img.shields.io/badge/Android-7.0%2B%20(API%2024%2B)-3DDC84?logo=android&logoColor=white)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Latest Release](https://img.shields.io/github/v/release/SucyMambavarang/antigravity_app?color=brightgreen)](https://github.com/SucyMambavarang/antigravity_app/releases)

[English](#-english) • [简体中文](#-简体中文)

---

## 🌐 English

### 📌 Overview
**Antigravity Mobile** is an open-source Android native client built for developers monitoring and reviewing **Google Antigravity** remote sessions and autonomous AI coding agents on mobile devices.

When reviewing agent code diffs or approving tool execution away from desktop workstations, mobile web browsers present severe friction:
1. **Intrusive Browser Chrome**: Heavy address and navigation bars waste ~30% of vertical screen estate.
2. **Aggressive Background Eviction**: Switching to messaging apps causes the browser to reload, severing active agent streaming logs and losing conversation context.
3. **Session Loss**: Losing account and workspace routing upon process suspension.

**Antigravity Mobile provides an essential mobile bridge for agentic workflows:**
- **Zero Browser Chrome**: Edge-to-edge full-screen display dedicated to code reviews, log streams, and approval modals.
- **Persistent Sessions & Hot Resume**: Background keep-alive via `moveTaskToBack`, ensuring instant resume with zero reload loops.
- **Multi-Account State Preservation**: Automatically persists user routing (`/u/0/`, `/u/1/`) and cookies across lifecycle events.
- **Real-Time Notification Bridge**: Native Android notification channel alerting developers when agents complete tasks or require tool execution authorization.

### 📱 Quick Download
Download the latest pre-built APK from the [Releases Page](https://github.com/SucyMambavarang/antigravity_app/releases).

### 🛠️ Building from Source
```bash
git clone https://github.com/SucyMambavarang/antigravity_app.git
cd antigravity_app
./gradlew assembleDebug
```
Output APK: `app/build/outputs/apk/debug/app-debug.apk`

---

## 🇨🇳 简体中文

### 📌 项目简介
日常在手机浏览器里访问 Google Antigravity 网页版时，普遍存在几个难受的地方：
1. **浏览器地址栏占空间**：顶部地址栏和底部操作栏占了近 1/3 的垂直屏幕，看代码和长对话很局促。
2. **切后台容易丢会话**：回个微信或切出去看资料，切回来经常被浏览器直接重新加载，前面的上下文容易中断。
3. **多账号切换易失忆**：重新打开容易丢失用户路由被踢回主账号。

**Antigravity Mobile** 就是为了解决上述痛点随手写的一个 Android 原生客户端：
- **去掉浏览器地址栏**：全屏加载 Antigravity 网页，把屏幕空间都留给代码审查与工作区。
- **切后台防刷新**：拦截返回键为后台热驻留（`moveTaskToBack`），切换应用不强制重载。
- **账号状态持久化**：自动捕获并持久化当前账号路由，多账号切换后永久记住。
- **智能体通知桥接**：原生系统通知通道，任务完成或等待审批时及时提醒。

### 📱 安装包下载
前往 GitHub 仓库的 [Releases 发行页](https://github.com/SucyMambavarang/antigravity_app/releases) 直接下载 `Antigravity-Mobile.apk`。

### 🛠️ 本地编译
```bash
# 克隆仓库
git clone https://github.com/SucyMambavarang/antigravity_app.git
cd antigravity_app

# 编译 Debug 安装包
./gradlew assembleDebug
# 或 Windows PowerShell:
.\gradlew.bat assembleDebug
```

---

## 📄 License
This project is open-source under the [MIT License](LICENSE).
