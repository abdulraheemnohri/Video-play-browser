# VIDEOPlay Browser

**A Modern, Privacy-First, Video-Optimized Android Browser Based on GeckoView**

[![GitHub Release](https://img.shields.io/github/v/release/abdulraheemnohri/Video-play-browser?style=for-the-badge)](https://github.com/abdulraheemnohri/Video-play-browser/releases)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)
[![Android CI](https://github.com/abdulraheemnohri/Video-play-browser/actions/workflows/debug_apk.yml/badge.svg?style=for-the-badge)](https://github.com/abdulraheemnohri/Video-play-browser/actions)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.20-blue.svg?style=for-the-badge&logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-1.6.0-4285F4.svg?style=for-the-badge&logo=jetpack-compose)](https://developer.android.com/jetpack/compose)
[![GeckoView](https://img.shields.io/badge/GeckoView-120.0-FF9500.svg?style=for-the-badge&logo=firefox)](https://mozilla.github.io/geckoview/)

---

## 📌 **About VIDEOPlay Browser**

**VIDEOPlay Browser** is a **modern Android browser** built on **Mozilla's GeckoView** engine, designed with a **video-first** and **privacy-first** approach. It combines the power of **Firefox's rendering engine** with **cutting-edge Android features** to deliver a **fast, secure, and video-optimized** browsing experience.

---

## 🌟 **Key Features**

### **🚀 Browser Features**
- ✅ **GeckoView Engine** – Powered by Mozilla Firefox for **fast, secure, and standards-compliant** web rendering.
- ✅ **Tab Management** – **Unlimited tabs**, **private tabs**, **tab groups**, and **session restoration**.
- ✅ **Modern Navigation** – **Back, Forward, Reload, Stop** with **gesture support**.
- ✅ **Smart Address Bar** – **URL + Search** with **suggestions**, **clipboard paste**, and **voice input**.
- ✅ **Home Dashboard** – **Quick Access** (YouTube, Vimeo, etc.), **Recently Visited**, **Continue Watching**.
- ✅ **History & Bookmarks** – **Search, Filter, Delete, Import/Export** with **folders & tags**.
- ✅ **Downloads Manager** – **Queue, Pause, Resume, Cancel, Retry** with **notifications & progress tracking**.
- ✅ **Site Permissions** – **Camera, Microphone, Location, Notifications** per-site controls.
- ✅ **Privacy Controls** – **Tracking Protection (Standard/Strict/Custom)**, **HTTPS-Only Mode**, **Clear Data on Exit**.

### **🎥 Video Features**
- ✅ **Video Detection** – Automatically detects **HTML5 video/audio** elements.
- ✅ **Enhanced Video Player** – **Play, Pause, Seek, Volume, Speed Controls** with **gestures** (swipe for brightness/volume).
- ✅ **Fullscreen Mode** – **Immersive experience** with **auto-rotation** and **gesture controls**.
- ✅ **Picture-in-Picture (PiP)** – **Android PiP API** integration for **background playback**.
- ✅ **Mini Player** – **Continue watching while browsing** with **drag-to-move** and **resize**.
- ✅ **MediaSession** – **Lock Screen Controls**, **Bluetooth Controls**, **Headphone Controls**.
- ✅ **Audio Focus** – **Pause on Calls**, **Duck on Navigation**, **Continue on Resume**.
- ✅ **Video History** – **Continue Watching**, **Resume Playback**, **Delete, Clear All**.
- ✅ **Video Settings** – **Autoplay (Always/Wi-Fi Only/Never)**, **Playback Speed**, **Subtitles**, **Quality**, **Audio Track**.

### **📱 Android Features**
- ✅ **Edge-to-Edge UI** – **Status Bar & Navigation Bar** insets handling.
- ✅ **Predictive Back** – **Smooth back gesture animations** (Android 13+).
- ✅ **Dynamic Colors** – **Material You** theming with **system color extraction**.
- ✅ **Adaptive Layouts** – **Phones, Tablets, Foldables** support.
- ✅ **Dark Mode & AMOLED** – **System/Light/Dark/AMOLED** themes.
- ✅ **App Shortcuts** – **New Tab, Private Tab, Search, Downloads** (Launcher long-press).
- ✅ **Widgets** – **Search Widget**, **Quick Actions Widget**.
- ✅ **Sharing** – **Open URL, Search Text, Share Page, Share Downloads**.
- ✅ **Default Browser** – **Set as default browser** (Android 12+).
- ✅ **Biometric Lock** – **Fingerprint/Face ID** app lock.
- ✅ **Multi-Language** – **English & Urdu** (RTL support).

---

## 📱 **Screenshots**

| **Home Screen** | **Browser Screen** | **Video Player** |
|----------------|------------------|------------------|
| ![Home](https://via.placeholder.com/300x600/6200EE/FFFFFF?text=Home+Screen) | ![Browser](https://via.placeholder.com/300x600/6200EE/FFFFFF?text=Browser+Screen) | ![Video](https://via.placeholder.com/300x600/6200EE/FFFFFF?text=Video+Player) |

| **Tabs Screen** | **Settings Screen** | **Downloads Screen** |
|----------------|-------------------|---------------------|
| ![Tabs](https://via.placeholder.com/300x600/6200EE/FFFFFF?text=Tabs+Screen) | ![Settings](https://via.placeholder.com/300x600/6200EE/FFFFFF?text=Settings+Screen) | ![Downloads](https://via.placeholder.com/300x600/6200EE/FFFFFF?text=Downloads+Screen) |

---

## 🛠 **Tech Stack**

| **Category** | **Technology** |
|-------------|---------------|
| **Browser Engine** | [GeckoView](https://mozilla.github.io/geckoview/) (Mozilla Firefox) |
| **Language** | [Kotlin](https://kotlinlang.org) |
| **UI Framework** | [Jetpack Compose](https://developer.android.com/jetpack/compose) |
| **Architecture** | Clean Architecture (MVVM + UseCases + Repositories) |
| **Database** | [Room](https://developer.android.com/jetpack/androidx/releases/room) |
| **Preferences** | [DataStore](https://developer.android.com/jetpack/androidx/releases/datastore) |
| **Navigation** | [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) |
| **Dependency Injection** | Manual (No DI framework) |
| **Build System** | [Gradle](https://gradle.org) (KTS) |
| **Minimum SDK** | API 24 (Android 7.0) |
| **Target SDK** | API 34 (Android 14) |

---

## 🚀 **Getting Started**

### **Prerequisites**
- **Android Studio** (Latest Stable)
- **JDK 17+** (Required for Android Gradle Plugin)
- **Android SDK** (API 24+)
- **Git**

### **Setup**
1. **Clone the repository:**
   ```bash
   git clone https://github.com/abdulraheemnohri/Video-play-browser.git
   cd Video-play-browser
   ```

2. **Open in Android Studio:**
   - Open **Android Studio** → **File** → **Open** → Select the project.

3. **Sync Gradle:**
   - Click **Sync Now** in Android Studio.

4. **Build & Run:**
   - Select a **device/emulator** → Click **Run (▶)**.

---

## 📂 **Project Structure**

```
VideoPlayBrowser/
│
├── .github/
│   └── workflows/
│       ├── debug_apk.yml          # Debug APK Build Workflow
│       └── release_apk.yml        # Release APK Build Workflow
│
├── app/
│   ├── src/main/
│   │   ├── AndroidManifest.xml
│   │   ├── java/com/videoplay/browser/
│   │   │   ├── BrowserApplication.kt
│   │   │   ├── ui/
│   │   │   │   ├── MainActivity.kt
│   │   │   │   ├── BrowserApp.kt
│   │   │   │   ├── screens/
│   │   │   │   │   ├── HomeScreen.kt
│   │   │   │   │   ├── BrowserScreen.kt
│   │   │   │   │   ├── TabsScreen.kt
│   │   │   │   │   ├── SettingsScreen.kt
│   │   │   │   │   ├── HistoryScreen.kt
│   │   │   │   │   ├── BookmarksScreen.kt
│   │   │   │   │   ├── DownloadsScreen.kt
│   │   │   │   │   └── PrivacySettingsScreen.kt
│   │   │   │   └── theme/
│   │   │   │       ├── Theme.kt
│   │   │   │       ├── Color.kt
│   │   │   │       └── Type.kt
│   │   │   ├── viewmodel/
│   │   │   │   ├── BrowserViewModel.kt
│   │   │   │   └── VideoPlayerViewModel.kt
│   │   │   ├── gecko/
│   │   │   │   ├── runtime/
│   │   │   │   │   └── GeckoRuntimeManager.kt
│   │   │   │   └── session/
│   │   │   │       └── GeckoSessionManager.kt
│   │   │   ├── tabs/
│   │   │   │   ├── Tab.kt
│   │   │   │   └── TabManager.kt
│   │   │   ├── browser/
│   │   │   │   └── navigation/
│   │   │   │       └── NavigationController.kt
│   │   │   ├── video/
│   │   │   │   ├── detector/
│   │   │   │   │   └── VideoDetector.kt
│   │   │   │   ├── playback/
│   │   │   │   │   ├── VideoPlayerScreen.kt
│   │   │   │   │   └── VideoPlayerViewModel.kt
│   │   │   │   ├── fullscreen/
│   │   │   │   │   └── FullscreenVideoActivity.kt
│   │   │   │   ├── pip/
│   │   │   │   │   └── VideoPipManager.kt
│   │   │   │   ├── media/
│   │   │   │   │   └── MediaSessionManager.kt
│   │   │   │   └── settings/
│   │   │   │       ├── VideoSettings.kt
│   │   │   │       └── VideoSettingsViewModel.kt
│   │   │   ├── database/
│   │   │   │   ├── AppDatabase.kt
│   │   │   │   ├── entities/
│   │   │   │   │   ├── HistoryEntity.kt
│   │   │   │   │   ├── BookmarkEntity.kt
│   │   │   │   │   ├── BookmarkFolderEntity.kt
│   │   │   │   │   ├── VideoHistoryEntity.kt
│   │   │   │   │   └── DownloadEntity.kt
│   │   │   │   └── dao/
│   │   │   │       ├── HistoryDao.kt
│   │   │   │       ├── BookmarkDao.kt
│   │   │   │       ├── VideoHistoryDao.kt
│   │   │   │       └── DownloadDao.kt
│   │   │   ├── core/
│   │   │   │   └── preferences/
│   │   │   │       └── SettingsRepository.kt
│   │   │   ├── privacy/
│   │   │   │   └── PrivacySettings.kt
│   │   │   ├── security/
│   │   │   │   └── BiometricLock.kt
│   │   │   ├── downloads/
│   │   │   │   ├── DownloadManager.kt
│   │   │   │   └── DownloadService.kt
│   │   │   └── testing/
│   │   │       └── ExampleUnitTest.kt
│   │   └── res/
│   │       ├── values/
│   │       │   ├── strings.xml
│   │       │   ├── colors.xml
│   │       │   ├── themes.xml
│   │       │   └── styles.xml
│   │       ├── values-ur/
│   │       │   └── strings.xml
│   │       ├── drawable/
│   │       │   ├── ic_launcher_background.xml
│   │       │   ├── ic_launcher_foreground.xml
│   │       │   └── app_logo.svg
│   │       └── mipmap-*/
│   └── build.gradle.kts
│
├── build.gradle.kts
├── settings.gradle.kts
├── proguard-rules.pro
├── README.md
└── LICENSE
```

---

## 🎨 **UI/UX Design**

### **Design System**
- **Material 3** – Modern, clean, and adaptive.
- **Dynamic Colors** – System color extraction for theming.
- **Edge-to-Edge** – Full-screen experience with proper insets.
- **Adaptive Layouts** – Optimized for **phones, tablets, and foldables**.
- **Dark Mode** – Full support for **light/dark/AMOLED** themes.

### **Color Palette**
| **Color** | **Light Theme** | **Dark Theme** |
|-----------|----------------|----------------|
| Primary | `#6200EE` (Deep Purple) | `#D0BCFF` |
| Secondary | `#03DAC6` (Teal) | `#86E8DE` |
| Tertiary | `#3700B3` (Dark Purple) | `#CBCAFF` |
| Background | `#FFFFFBFE` | `#1C1B1F` |
| Surface | `#FFFFFBFE` | `#1C1B1F` |

---

## 🔧 **Configuration**

### **GeckoView Setup**
The project uses **GeckoView** as the browser engine. Ensure you have the latest version in `app/build.gradle.kts`:
```kotlin
implementation("org.mozilla.geckoview:geckoview:120.0.20240311145107")
```

### **Gradle Plugins**
- **Android Gradle Plugin**: `8.1.2`
- **Kotlin Gradle Plugin**: `1.9.20`
- **Jetpack Compose**: `1.6.0`

---

## 📜 **License**

This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for details.

---

## 🤝 **Contributing**

Contributions are welcome! Please open an **Issue** or **Pull Request** for any improvements.

### **How to Contribute**
1. **Fork** the repository.
2. **Clone** your fork.
3. **Create a new branch** (`git checkout -b feature/your-feature`).
4. **Commit** your changes (`git commit -m 'feat: Add your feature'`).
5. **Push** to the branch (`git push origin feature/your-feature`).
6. **Open a Pull Request**.

---

## 📬 **Contact**

For questions or feedback, contact:
- **GitHub**: [@abdulraheemnohri](https://github.com/abdulraheemnohri)
- **Email**: [abdulraheemnohri@gmail.com](mailto:abdulraheemnohri@gmail.com)

---

## 🙏 **Acknowledgments**

- **Mozilla** – For [GeckoView](https://mozilla.github.io/geckoview/).
- **Android Team** – For [Jetpack Compose](https://developer.android.com/jetpack/compose).
- **Open Source Community** – For inspiration and support.

---

**Made with ❤️ in Pakistan** 🇵🇰
