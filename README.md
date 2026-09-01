# VIDEOPlay Browser

**VIDEOPlay Browser** is a **Firefox/GeckoView-based** Android browser with a **Video-First** and **Privacy-First** approach. It combines the power of **GeckoView** with modern Android features to deliver a **fast, secure, and video-optimized** browsing experience.

---

## 📌 **Project Overview**

VIDEOPlay Browser is designed to:
- Provide a **full-featured web browser** experience.
- Optimize **video playback** with advanced controls.
- Ensure **privacy and security** for users.
- Support **modern Android features** like Picture-in-Picture (PiP), Fullscreen, and MediaSession.
- Offer **extensive customization** for video settings.

---

## 🛠 **Tech Stack**

- **Browser Engine**: [GeckoView](https://mozilla.github.io/geckoview/) (Mozilla Firefox Engine)
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: Clean Architecture (MVVM + UseCases + Repositories)
- **Database**: Room (for History, Bookmarks, Downloads, etc.)
- **Preferences**: DataStore (for Settings)
- **Dependencies**:
  - `org.mozilla.geckoview:geckoview` (Latest Stable)
  - `androidx.compose.ui:ui` (Jetpack Compose)
  - `androidx.lifecycle:lifecycle-viewmodel-compose` (ViewModel)
  - `androidx.navigation:navigation-compose` (Navigation)
  - `androidx.room:room-runtime` (Database)
  - `androidx.datastore:datastore-preferences` (Preferences)

---

## 📂 **Project Structure**

```
VideoPlayBrowser/
│
├── app/                          # Main Android App Module
│   ├── src/main/
│   │   ├── AndroidManifest.xml
│   │   ├── java/com/videoplay/browser/
│   │   │   ├── ui/               # Compose UI Screens
│   │   │   ├── viewmodel/       # ViewModels
│   │   │   ├── model/           # Data Models
│   │   │   ├── navigation/      # Navigation Logic
│   │   │   └── ...
│   │   └── res/                 # Resources (Strings, Drawables, etc.)
│
├── core/                         # Core Modules
│   ├── common/                  # Shared Utilities
│   ├── model/                   # Shared Data Models
│   ├── database/                # Room Database
│   ├── preferences/             # DataStore Preferences
│   ├── security/                # Security Utilities
│   └── ui/                      # Shared UI Components
│
├── browser/                      # Browser Module
│   ├── api/                     # Browser APIs
│   ├── engine/                  # Browser Engine (GeckoView)
│   ├── navigation/              # Navigation Logic
│   ├── permissions/             # Site Permissions
│   └── session/                 # Browser Sessions
│
├── gecko/                        # GeckoView Module
│   ├── runtime/                 # GeckoRuntime Management
│   ├── session/                 # GeckoSession Management
│   └── extensions/              # GeckoView Extensions
│
├── video/                        # Video Module
│   ├── detector/                # Video Detection Logic
│   ├── playback/                # Video Playback Logic
│   ├── controls/                # Video Controls
│   ├── fullscreen/              # Fullscreen Management
│   ├── pip/                     # Picture-in-Picture (PiP)
│   ├── media/                   # MediaSession Integration
│   ├── history/                 # Video History
│   └── settings/                # Video Settings
│
├── tabs/                         # Tab Management
├── history/                      # Browsing History
├── bookmarks/                    # Bookmarks Management
├── downloads/                    # Downloads Management
├── privacy/                      # Privacy Features
├── settings/                     # App Settings
├── search/                       # Search Engine
├── sharing/                      # Sharing Features
├── widgets/                      # Android Widgets
├── shortcuts/                    # App Shortcuts
└── testing/                      # Tests (Unit, UI, Integration)
```

---

## 🚀 **Getting Started**

### **Prerequisites**
- Android Studio (Latest Stable)
- JDK 17+ (Required for Android Gradle Plugin)
- Android SDK (API 24+)
- Git

### **Setup**
1. Clone the repository:
   ```bash
   git clone https://github.com/abdulraheemnohri/Video-play-browser.git
   cd Video-play-browser
   ```

2. Open the project in **Android Studio**.

3. Sync Gradle and build the project.

4. Run on an **Android device** or **emulator**.

---

## 📱 **Features**

### **Browser Features**
- ✅ **GeckoView Integration** (Firefox Engine)
- ✅ **Tab Management** (Unlimited Tabs, Private Tabs)
- ✅ **Navigation** (Back, Forward, Reload, Stop)
- ✅ **Address Bar** (URL + Search)
- ✅ **Home Screen** (Quick Access, Recently Visited)
- ✅ **History** (Search, Delete, Clear)
- ✅ **Bookmarks** (Folders, Tags, Import/Export)
- ✅ **Downloads** (Queue, Pause, Resume, Cancel)
- ✅ **Site Permissions** (Camera, Microphone, Location, etc.)
- ✅ **Privacy Controls** (Tracking Protection, HTTPS-Only)

### **Video Features**
- ✅ **Video Detection** (HTML5 Video/Audio)
- ✅ **Video Player** (Play, Pause, Seek, Volume, Speed)
- ✅ **Fullscreen Mode** (Immersive Experience)
- ✅ **Picture-in-Picture (PiP)** (Android PiP API)
- ✅ **Mini Player** (Continue Watching While Browsing)
- ✅ **MediaSession** (Lock Screen Controls, Bluetooth)
- ✅ **Audio Focus** (Pause on Calls, Duck on Navigation)
- ✅ **Video History** (Continue Watching, Resume Playback)
- ✅ **Video Settings** (Playback, Controls, Quality, Subtitles, etc.)

### **Android Features**
- ✅ **Edge-to-Edge UI** (Status Bar, Navigation Bar)
- ✅ **Adaptive Layouts** (Phone, Tablet, Foldable)
- ✅ **Dark Mode & Dynamic Colors** (Material 3)
- ✅ **App Shortcuts** (New Tab, Private Tab, Search)
- ✅ **Widgets** (Search Widget, Quick Actions)
- ✅ **Sharing** (Open URL, Share Page, Share Downloads)
- ✅ **App Links** (Default Browser Support)
- ✅ **Biometric Lock** (App Lock with Fingerprint/Face ID)

---

## 🔧 **Configuration**

### **GeckoView Setup**
The project uses **GeckoView** as the browser engine. Ensure you have the latest version in `app/build.gradle.kts`:
```kotlin
implementation("org.mozilla.geckoview:geckoview:120.0.20240311145107")
```

### **Gradle Plugins**
- **Android Gradle Plugin**: Latest Stable
- **Kotlin Gradle Plugin**: Latest Stable
- **Jetpack Compose**: Latest Stable

---

## 📜 **License**

This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for details.

---

## 🤝 **Contributing**

Contributions are welcome! Please open an **Issue** or **Pull Request** for any improvements.

---

## 📬 **Contact**

For questions or feedback, contact:
- **GitHub**: [@abdulraheemnohri](https://github.com/abdulraheemnohri)
- **Email**: abdulraheemnohri@gmail.com
