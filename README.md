# BiliBridge

[![Android](https://img.shields.io/badge/Android-API%2023%2B-3DDC84?logo=android&logoColor=white)](https://developer.android.com/)
[![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)](https://openjdk.org/)
[![CI](https://github.com/inertialobs/bilibili-intent-bridge/actions/workflows/android.yml/badge.svg?branch=main)](https://github.com/inertialobs/bilibili-intent-bridge/actions/workflows/android.yml)
[![许可证：MIT](https://img.shields.io/badge/许可证-MIT-yellow.svg)](LICENSE)

BiliBridge 是一个轻量级 Android 跳转桥：当网页或应用把 B 站链接定向发送给官方包名 `tv.danmaku.bili` 时，由桥接 Activity 接收链接并转交给第三方客户端。

本项目当前按 [PiliPlus](https://github.com/bggRGjQaUbCoE/PiliPlus) 配置，目标包名为 `com.example.piliplus`, 不包含 B 站客户端，也不修改第三方客户端的 APK、签名或数据。

## 工作方式

- 桥接 APK 使用应用 ID `tv.danmaku.bili`，因此不能与官方 B 站客户端同时安装。
- 接收 `bilibili://`、`tv.danmaku.bili://`、`bilibili-hd://`，以及部分 B 站 HTTP(S) 链接，并转发给 PiliPlus。
- 收到没有 URI 的包启动请求时，打开 PiliPlus 首页。
- `bilibili://` 的 Intent Filter 设置了较低优先级。在本项目验证过的 Android 16 设备上，普通隐式深链仍解析到 PiliPlus；其他 ROM 的解析行为可能不同。

## 构建

### GitHub Actions

推送到 `main` 分支，或在 Actions 页面手动运行 **Build Android APK**。成功后下载 `BiliBridge-debug` artifact，其中的 APK 路径为：

```text
app/build/outputs/apk/debug/app-debug.apk
```

### 本地构建

需要 JDK 17、Gradle 8.9、Android SDK Platform 35 和 Build Tools 35.0.0。在项目根目录运行：

```bash
gradle assembleDebug
```

APK 输出位置同上。

## 安装与配置

安装构建出的 APK 后，桥会占用 `tv.danmaku.bili` 包名。若需转发到其他客户端，请同时修改：

1. `app/src/main/java/com/example/bilbridge/BridgeActivity.java` 中的 `PILIPLUS_PACKAGE`。
2. `app/src/main/AndroidManifest.xml` 中 `<queries>` 声明的包名。

目标客户端还需要声明并支持相应的 Intent Filter / 深链格式。桥只能转发链接，不能补齐客户端本身不支持的页面路由。

## 已知限制

- 官方 B 站与桥使用相同应用 ID，二者不能并存；要安装官方客户端，需先卸载桥。
- 部分宿主会检查包可见性、签名或额外接口。桥只提供包名和 Intent 接收入口，不模拟官方签名、Provider 或完整 PackageManager 行为。
- Android 11 及以上的包可见性规则可能影响宿主的安装状态查询；宿主是否声明了相应查询权限/范围会影响结果。
- GitHub Actions 产物为 debug 签名。不同 CI 运行生成的签名可能不同，覆盖更新失败时需先卸载旧版桥再安装新版。

## 许可证

本项目采用 MIT License，详见 [`LICENSE`](LICENSE)。
