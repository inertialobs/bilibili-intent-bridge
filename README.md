# BiliBridge

A small Android bridge for this device's PiliPlus installation (`com.example.piliplus`).
The bridge uses the application ID `tv.danmaku.bili` so apps that target that package
can launch it. It forwards Bilibili deep links to PiliPlus and opens PiliPlus's home
screen when launched without a URI.

The `bilibili://` intent filter has lower priority than the native client, so ordinary
implicit Bilibili links should continue to prefer PiliPlus where Android can choose by
priority. The bridge also handles package-targeted links addressed to `tv.danmaku.bili`.

## Build

Push to `main` or run **Build Android APK** from GitHub Actions. Download the
`BiliBridge-debug` artifact. The APK is at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

The debug APK is signed by the CI runner. Rebuilding may produce a different debug
signing key, so updates may require uninstalling the previous bridge first.
