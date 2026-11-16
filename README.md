# Antigravity Mobile

Google Antigravity 网页版 Android 封装客户端（基于 WebView）。

[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Android](https://img.shields.io/badge/Android-7.0%2B%20(API%2024%2B)-3DDC84?logo=android&logoColor=white)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-7F52FF?logo=kotlin&logoColor=white)](https://kotlinlang.org)

---

## 📌 项目简介

日常在手机浏览器里访问 Google Antigravity 网页版时，普遍存在几个难受的地方：
1. **浏览器地址栏占空间**：顶部地址栏和底部操作栏占了近 1/3 的垂直屏幕，看代码和长对话很局促。
2. **切后台容易丢会话**：回个微信或切出去看资料，切回来经常被浏览器直接重新加载，前面的上下文容易中断。
3. **账号登录阻断**：部分套壳浏览器因 UA 或窗口机制问题，容易触发 Google 账号安全阻断。

**Antigravity Mobile** 就是为了解决上述痛点随手写的一个 Android 原生 WebView 封装工具：
- **去掉浏览器地址栏**：全屏加载 Antigravity 网页，把屏幕空间都留给工作区。
- **切后台防刷新**：拦截返回键为后台热驻留（`moveTaskToBack`），切换应用不强制重载。
- **适配 Google 账号登录**：使用标准 Chrome User-Agent，支持多窗口弹窗登录机制与第三方 Cookie 保持。
- **自定义连接端点**：长按顶部进度条可以随时修改连接地址（官方地址或自建远程端点）。

---

## 📱 安装包下载

可直接前往 GitHub 仓库的 [Releases 发行页](https://github.com/SucyMambavarang/antigravity_app/releases) 下载打包好的 `Antigravity-Mobile.apk` 安装。

---

## 🛠️ 本地构建

如果你需要自己编译：

### 环境要求
- Android SDK (API 24+)
- JDK 17
- Gradle 8.x / 9.x

### 构建步骤
```bash
# 克隆仓库
git clone https://github.com/SucyMambavarang/antigravity_app.git
cd antigravity_app

# 编译 Debug 安装包
./gradlew assembleDebug

# 或 Windows PowerShell:
.\gradlew.bat assembleDebug
```

编译生成的 APK 位于：
`app/build/outputs/apk/debug/app-debug.apk`

---

## 📄 开源许可

本项目基于 [MIT 许可证](LICENSE) 开源。
