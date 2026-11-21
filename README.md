# Antigravity Mobile

**A lightweight Android WebView wrapper for the Google Antigravity web app.**  
**一个轻量级的 Google Antigravity 网页版 Android WebView 封装小工具。**

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Android](https://img.shields.io/badge/Android-7.0%2B%20(API%2024%2B)-3DDC84?logo=android&logoColor=white)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Latest Release](https://img.shields.io/github/v/release/SucyMambavarang/antigravity_app?color=brightgreen)](https://github.com/SucyMambavarang/antigravity_app/releases)

[English](#-english) • [简体中文](#-简体中文)

---

## 🌐 English

### 📌 Overview
**Antigravity Mobile** is a simple, open-source Android WebView wrapper for accessing the **Google Antigravity** web interface on mobile devices.

Using Antigravity directly in a mobile browser comes with a few practical annoyances:
1. **Browser Chrome Takes Up Space**: Top address bars and bottom toolbars eat up a large share of vertical screen space when reading code or long conversations.
2. **Background Reloads**: Switching out to another app and coming back often triggers a browser tab reload and interrupts the active view.
3. **Multi-Account & File Upload Friction**: Switching Google accounts (`/u/0/`, `/u/1/`) or clearing session cookies is inconvenient, and basic WebView wrappers break `<input type="file">` uploads by default.

### ✨ Features
- **Full-Screen Without Browser Address Bar**: Removes browser address and navigation bars so the full viewport is dedicated to the web workspace.
- **Background Resume Without Forced Reload**: Intercepts the root back press via `moveTaskToBack(true)` while persisting WebView state and cookies, avoiding unnecessary reloads when switching apps.
- **Three-Finger Tap Quick Menu**: Tap anywhere on screen with 3 fingers (or long-press Back / press Menu) to open the session dialog—switch Google accounts (`AccountChooser`), clear cookies/cache and re-login, or set a custom URL. Automatically remembers the active account base path (`/u/0/`, `/u/1/`).
- **Gallery & Multi-File Upload Support**: Implements Android WebView `onShowFileChooser` with the system file/gallery picker (`ACTION_GET_CONTENT` + `EXTRA_ALLOW_MULTIPLE`), supporting single and multi-file uploads.
- **Adaptive Light/Dark Launch Splash**: Matches the system light (`#FFFFFF`) or dark (`#0F172A`) theme on startup (including Android 12+ `windowSplashScreenBackground`) and fades out smoothly once the WebView commits its first visible frame (`onPageCommitVisible`).

### 📱 Quick Download
Download the latest pre-built `Antigravity-Mobile.apk` from the [Releases Page](https://github.com/SucyMambavarang/antigravity_app/releases).

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
**Antigravity Mobile** 是一个开源自用的 Google Antigravity 网页版 Android WebView 封装小工具，主要为了解决直接用手机浏览器访问时的几个实际痛点：
1. **浏览器地址栏占空间**：顶部地址栏和底部操作栏占掉不少纵向屏幕，手机上看代码和长对话比较局促。
2. **切后台容易刷新**：切出去回个消息再切回来，浏览器经常重新加载页面，打断正在看的会话。
3. **多账号切换与上传不便**：多谷歌账号路径（`/u/0/`、`/u/1/`）容易跳回默认账号，普通 WebView 封装也默认无法拉起系统相册/文件选择器。

### ✨ 主要功能
- **全屏无地址栏**：去掉手机浏览器顶部地址栏与底部操作栏，把完整屏幕空间留给网页工作区。
- **切后台防刷新**：根页面按返回键时通过 `moveTaskToBack(true)` 退到后台，并持久化 Cookie 与 WebView 状态，日常切换应用不易触发强制刷新。
- **三指轻点切号与清缓存**：任意页面**三指轻点屏幕**（或长按返回键 / 菜单键）可随时呼出快捷菜单，支持一键切换谷歌账号、清除登录凭证与缓存重登、修改访问地址；自动记忆当前账号基础路径（`/u/0/`、`/u/1/`）。
- **支持相册与多文件上传**：完整接入 Android WebView `onShowFileChooser` 与系统文件/相册选择器（支持 `EXTRA_ALLOW_MULTIPLE`），可在网页内正常选择图片或一次上传多个文件。
- **自适应深浅色启动过渡页**：启动背景与过渡层跟随系统深浅色自动切换（浅色 `#FFFFFF` / 深色 `#0F172A`，适配 Android 12+ 原生 Splash 背景），待网页首帧渲染完成（`onPageCommitVisible`）后平滑淡出，减少冷启动闪屏割裂感。

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
编译产物位于：`app/build/outputs/apk/debug/app-debug.apk`

---

## 📄 License
This project is open-source under the [MIT License](LICENSE).
