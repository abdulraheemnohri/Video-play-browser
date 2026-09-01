# VIDEOPlay Browser 🌐🎥

**A Firefox/GeckoView-based Android browser with Video-First and Privacy-First features.**

---

## 📌 **About VIDEOPlay Browser**

VIDEOPlay Browser is a **modern, privacy-focused, and video-optimized** Android browser built on **Mozilla's GeckoView** engine. It combines the power of Firefox with the latest Android features to deliver a **fast, secure, and immersive** browsing experience.

---

## ✨ **Features**

### **🌐 Browser Features**
- ✅ **GeckoView Integration** – Firefox Engine for fast and secure browsing
- ✅ **Tab Management** – Unlimited tabs, private tabs, tab groups
- ✅ **Modern Navigation** – Back, Forward, Reload, Stop
- ✅ **Smart Address Bar** – URL + Search with suggestions
- ✅ **Home Screen** – Quick Access, Recently Visited, Continue Watching
- ✅ **History** – Search, Delete, Clear
- ✅ **Bookmarks** – Folders, Tags, Import/Export
- ✅ **Downloads** – Queue, Pause, Resume, Cancel
- ✅ **Site Permissions** – Camera, Microphone, Location, etc.
- ✅ **Privacy Controls** – Tracking Protection, HTTPS-Only

### **🎥 Video Features**
- ✅ **Video Detection** – Auto-detect HTML5 video/audio
- ✅ **Video Player** – Play, Pause, Seek, Volume, Speed Controls
- ✅ **Fullscreen Mode** – Immersive video experience
- ✅ **Picture-in-Picture (PiP)** – Android PiP API support
- ✅ **Mini Player** – Continue watching while browsing
- ✅ **MediaSession** – Lock Screen Controls, Bluetooth Support
- ✅ **Audio Focus** – Pause on Calls, Duck on Navigation
- ✅ **Video History** – Continue Watching, Resume Playback
- ✅ **Video Settings** – Playback, Controls, Quality, Subtitles, etc.

### **📱 Android Features**
- ✅ **Edge-to-Edge UI** – Status Bar & Navigation Bar Insets
- ✅ **Predictive Back** – Modern back gesture animation
- ✅ **Dynamic Colors** – Material 3 Theming
- ✅ **Adaptive Layouts** – Phones, Tablets, Foldables
- ✅ **Dark Mode** – Automatic & Manual
- ✅ **Biometric Lock** – Fingerprint/Face ID App Lock
- ✅ **App Shortcuts** – New Tab, Private Tab, Search
- ✅ **Widgets** – Search Widget, Quick Actions
- ✅ **Sharing** – Open URL, Share Page, Share Downloads
- ✅ **Default Browser** – Set as Default Browser

### **🔒 Privacy & Security**
- ✅ **Tracking Protection** – Block trackers across websites
- ✅ **HTTPS-Only Mode** – Force secure connections
- ✅ **Private Browsing** – No history, no cookies
- ✅ **Clear Data on Exit** – Auto-clear browsing data
- ✅ **Biometric Authentication** – Secure app access

---

## 📱 **Screenshots**

| **Home Screen** | **Browser Screen** | **Video Player** |
|----------------|------------------|----------------|
| ![Home](https://via.placeholder.com/300x600/6200EE/FFFFFF?text=Home+Screen) | ![Browser](https://via.placeholder.com/300x600/6200EE/FFFFFF?text=Browser+Screen) | ![Video](https://via.placeholder.com/300x600/6200EE/FFFFFF?text=Video+Player) |

| **Tabs Screen** | **Settings Screen** | **History Screen** |
|----------------|------------------|----------------|
| ![Tabs](https://via.placeholder.com/300x600/6200EE/FFFFFF?text=Tabs+Screen) | ![Settings](https://via.placeholder.com/300x600/6200EE/FFFFFF?text=Settings+Screen) | ![History](https://via.placeholder.com/300x600/6200EE/FFFFFF?text=History+Screen) |

*(Screenshots will be updated with real app screens soon!)*

---

## 🛠 **Tech Stack**

| **Category** | **Technology** |
|-------------|--------------|
| **Browser Engine** | [GeckoView](https://mozilla.github.io/geckoview/) (Mozilla Firefox) |
| **Language** | Kotlin |
| **UI Framework** | Jetpack Compose |
| **Architecture** | Clean Architecture (MVVM + UseCases + Repositories) |
| **Database** | Room (SQLite) |
| **Preferences** | DataStore |
| **Navigation** | Android Navigation Component |
| **Dependencies** | AndroidX, Material 3, Coil, etc. |

---

## 📂 **Project Structure**

```
VideoPlayBrowser/
│
├── .github/
│   └── workflows/
│       ├── debug_apk.yml
│       └── release_apk.yml
│
├── app/
│   ├── src/main/
│   │   ├── AndroidManifest.xml
│   │   ├── java/com/videoplay/browser/
│   │   │   ├── ui/
│   │   │   │   ├── screens/
│   │   │   │   │   ├── HomeScreen.kt
│   │   │   │   │   ├── BrowserScreen.kt
│   │   │   │   │   ├── TabsScreen.kt
│   │   │   │   │   ├── HistoryScreen.kt
│   │   │   │   │   ├── BookmarksScreen.kt
│   │   │   │   │   ├── DownloadsScreen.kt
│   │   │   │   │   └── SettingsScreen.kt
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
│   │   │   │   └── settings/
│   │   │   │       ├── VideoSettings.kt
│   │   │   │       └── VideoSettingsViewModel.kt
│   │   │   ├── database/
│   │   │   │   ├── AppDatabase.kt
│   │   │   │   ├── entities/
│   │   │   │   │   ├── HistoryEntity.kt
│   │   │   │   │   ├── BookmarkEntity.kt
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
│   │   │   └── BrowserApplication.kt
│   │   └── res/
│   │       ├── values/
│   │       │   ├── strings.xml
│   │       │   ├── colors.xml
│   │       │   ├── themes.xml
│   │       │   └── styles.xml
│   │       ├── values-ur/
│   │       │   └── strings.xml (Urdu Localization)
│   │       └── drawable/
│   │           ├── ic_launcher_background.xml
│   │           ├── ic_launcher_foreground.xml
│   │           └── app_logo.svg
│   └── build.gradle.kts
│
├── .gitignore
├── README.md
├── LICENSE
├── settings.gradle.kts
├── build.gradle.kts
└── proguard-rules.pro
```

---

## 🚀 **Getting Started**

### **Prerequisites**
- Android Studio (Latest Stable)
- JDK 17+ (Required for Android Gradle Plugin)
- Android SDK (API 24+)
- Git

### **Setup**
1. **Clone the repository:**
   ```bash
   git clone https://github.com/abdulraheemnohri/Video-play-browser.git
   cd Video-play-browser
   ```

2. **Open in Android Studio:**
   - Open Android Studio → **File → Open** → Select the `Video-play-browser` folder.

3. **Sync Gradle:**
   - Click **Sync Now** in Android Studio to download dependencies.

4. **Build & Run:**
   - Connect an **Android device** or start an **emulator**.
   - Click **Run (▶)** to build and install the app.

---

## 📦 **Build Variants**

| **Variant** | **Description** | **Usage** |
|------------|----------------|----------|
| **Debug** | For development & testing | `./gradlew assembleDebug` |
| **Release** | For production | `./gradlew assembleRelease` |

---

## 🤝 **Contributing**

Contributions are welcome! Please follow these steps:

1. **Fork** the repository.
2. Create a **new branch** (`git checkout -b feature/your-feature`).
3. Commit your changes (`git commit -m 'feat: Add new feature'`).
4. Push to the branch (`git push origin feature/your-feature`).
5. Open a **Pull Request**.

---

## 📜 **License**

This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for details.

---

## 📬 **Contact**

For questions or feedback, contact:
- **GitHub**: [@abdulraheemnohri](https://github.com/abdulraheemnohri)
- **Email**: abdulraheemnohri@gmail.com

---

## 🌟 **Acknowledgments**

- **Mozilla** – For [GeckoView](https://mozilla.github.io/geckoview/)
- **Android Team** – For Jetpack Compose & Material 3
- **Open Source Community** – For inspiration and support

---

**Made with ❤️ in Pakistan** 🇵🇰
