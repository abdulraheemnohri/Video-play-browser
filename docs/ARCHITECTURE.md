# VIDEOPlay Browser - Architecture Documentation

## 🏗 Overview

VIDEOPlay Browser follows **Clean Architecture** principles with a modular approach. The architecture is designed to be:

- **Modular**: Separation of concerns with clear boundaries
- **Testable**: Easy to test each component in isolation
- **Maintainable**: Clear structure and organization
- **Scalable**: Easy to add new features
- **Flexible**: Can adapt to changing requirements

---

## 📐 Architecture Layers

```
┌─────────────────────────────────────────────────────────────┐
│                        Presentation Layer                        │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │      UI         │  │   ViewModel     │  │    Theme        │  │
│  │  (Compose)      │  │   (State)        │  │   (Styling)     │  │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                       Domain Layer (Use Cases)                   │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │   Tab Use Cases │  │ Video Use Cases │  │ Browser Use     │  │
│  │                 │  │                 │  │ Cases           │  │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                        Data Layer                                │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │   Repository    │  │     DAO         │  │   DataStore     │  │
│  │   (Business)     │  │   (Database)    │  │   (Preferences)  │  │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  │
└─────────────────────────────────────────────────────────────┘
                              │
                              ▼
┌─────────────────────────────────────────────────────────────┐
│                     External Dependencies                        │
│  ┌─────────────────┐  ┌─────────────────┐  ┌─────────────────┐  │
│  │   GeckoView     │  │   Room DB       │  │   Android SDK   │  │
│  │   (Browser)      │  │   (Persistence)  │  │   (Platform)    │  │
│  └─────────────────┘  └─────────────────┘  └─────────────────┘  │
└─────────────────────────────────────────────────────────────┘
```

---

## 🗂 Module Structure

```
VideoPlayBrowser/
├── app/                          # Main application module
│   ├── src/main/java/com/videoplay/browser/
│   │   ├── ui/                  # Presentation Layer
│   │   │   ├── screens/        # Composable Screens
│   │   │   ├── theme/          # Theming (Color, Typography)
│   │   │   └── components/     # Reusable UI Components
│   │   │
│   │   ├── viewmodel/          # ViewModels (State Holders)
│   │   │   ├── BrowserViewModel.kt
│   │   │   ├── VideoPlayerViewModel.kt
│   │   │   └── SettingsViewModel.kt
│   │   │
│   │   ├── gecko/              # GeckoView Integration
│   │   │   ├── runtime/        # GeckoRuntime Management
│   │   │   └── session/        # GeckoSession Management
│   │   │
│   │   ├── video/              # Video Features
│   │   │   ├── detector/       # Video Detection
│   │   │   ├── playback/       # Video Playback
│   │   │   ├── fullscreen/     # Fullscreen Management
│   │   │   ├── pip/            # Picture-in-Picture
│   │   │   ├── media/          # MediaSession Integration
│   │   │   ├── settings/       # Video Settings
│   │   │   ├── gestures/       # Gesture Controls
│   │   │   └── subtitles/      # Subtitle Support
│   │   │
│   │   ├── tabs/               # Tab Management
│   │   │   ├── Tab.kt
│   │   │   ├── TabManager.kt
│   │   │   └── SessionManager.kt
│   │   │
│   │   ├── browser/            # Browser Functionality
│   │   │   └── navigation/     # Navigation Controls
│   │   │
│   │   ├── history/            # Browsing History
│   │   │   └── HistoryRepository.kt
│   │   │
│   │   ├── bookmarks/          # Bookmarks Management
│   │   │   └── BookmarkRepository.kt
│   │   │
│   │   ├── downloads/          # Downloads Management
│   │   │   ├── DownloadManager.kt
│   │   │   ├── DownloadService.kt
│   │   │   └── DownloadRepository.kt
│   │   │
│   │   ├── video/              # Video History
│   │   │   └── VideoRepository.kt
│   │   │
│   │   ├── privacy/            # Privacy Features
│   │   │   ├── PrivacyManager.kt
│   │   │   ├── TrackingProtectionManager.kt
│   │   │   ├── HttpsOnlyManager.kt
│   │   │   ├── SitePermissionsManager.kt
│   │   │   └── ClearBrowsingDataManager.kt
│   │   │
│   │   ├── security/           # Security Features
│   │   │   └── BiometricLock.kt
│   │   │
│   │   ├── settings/           # App Settings
│   │   │   ├── SearchEngineSettings.kt
│   │   │   ├── AppearanceSettings.kt
│   │   │   ├── BrowserSettings.kt
│   │   │   ├── AccessibilitySettings.kt
│   │   │   ├── DownloadSettings.kt
│   │   │   └── SettingsManager.kt
│   │   │
│   │   ├── android/            # Android-Specific Features
│   │   │   ├── AppShortcutsManager.kt
│   │   │   ├── WidgetProvider.kt
│   │   │   ├── DefaultBrowserManager.kt
│   │   │   ├── SharingManager.kt
│   │   │   └── AppLinksManager.kt
│   │   │
│   │   ├── database/           # Database Layer
│   │   │   ├── AppDatabase.kt
│   │   │   ├── DatabaseModule.kt
│   │   │   ├── entities/       # Data Entities
│   │   │   └── dao/            # Data Access Objects
│   │   │
│   │   ├── core/               # Core Utilities
│   │   │   └── preferences/    # DataStore Preferences
│   │   │
│   │   └── BrowserApplication.kt  # Application Class
│   │
│   └── src/main/res/            # Resources
│       ├── values/             # Strings, Colors, Styles, etc.
│       ├── values-ur/          # Urdu Localization
│       ├── drawable/           # Images and Icons
│       └── layout/             # XML Layouts (for Widgets)
│
└── .github/                      # GitHub Configuration
    └── workflows/               # CI/CD Workflows
```

---

## 🔌 Key Components

### **1. Presentation Layer (UI)**

| Component | Purpose | Location |
|-----------|---------|----------|
| `MainActivity.kt` | Entry point of the app | `ui/` |
| `BrowserApp.kt` | Root composable with navigation | `ui/` |
| `HomeScreen.kt` | Home screen with quick access | `ui/screens/` |
| `BrowserScreen.kt` | Browser with address bar and controls | `ui/screens/` |
| `TabsScreen.kt` | Tab management screen | `ui/screens/` |
| `SettingsScreen.kt` | App settings screen | `ui/screens/` |
| `VideoPlayerScreen.kt` | Video player UI | `video/playback/` |
| `Theme.kt` | App theming | `ui/theme/` |
| `Color.kt` | Color palette | `ui/theme/` |
| `Type.kt` | Typography | `ui/theme/` |

### **2. Domain Layer (Use Cases)**

*Note: Explicit UseCases are not yet implemented but would follow this pattern:*

| Use Case | Purpose | Location |
|----------|---------|----------|
| `GetTabsUseCase` | Get list of tabs | `domain/usecases/` |
| `AddTabUseCase` | Add a new tab | `domain/usecases/` |
| `CloseTabUseCase` | Close a tab | `domain/usecases/` |
| `LoadUrlUseCase` | Load a URL in a tab | `domain/usecases/` |
| `PlayVideoUseCase` | Play a video | `domain/usecases/` |

### **3. Data Layer**

| Component | Purpose | Location |
|-----------|---------|----------|
| `AppDatabase.kt` | Room database | `database/` |
| `HistoryDao.kt` | History data access | `database/dao/` |
| `BookmarkDao.kt` | Bookmark data access | `database/dao/` |
| `VideoHistoryDao.kt` | Video history data access | `database/dao/` |
| `DownloadDao.kt` | Download data access | `database/dao/` |
| `HistoryRepository.kt` | History business logic | `history/` |
| `BookmarkRepository.kt` | Bookmark business logic | `bookmarks/` |
| `VideoRepository.kt` | Video history business logic | `video/` |
| `DownloadRepository.kt` | Download business logic | `downloads/` |
| `SettingsRepository.kt` | Preferences storage | `core/preferences/` |

### **4. External Dependencies**

| Dependency | Purpose | Version |
|------------|---------|---------|
| GeckoView | Browser engine | 120.0 |
| Jetpack Compose | UI framework | 1.6.0 |
| Room | Database | 2.6.0 |
| DataStore | Preferences | 1.0.0 |
| Navigation Compose | Navigation | 2.7.5 |
| Coroutines | Async programming | 1.7.3 |

---

## 🔄 Data Flow

### **1. Browser Navigation Flow**

```
User Action (e.g., click back button)
       ↓
BrowserScreen.kt (UI)
       ↓
BrowserViewModel.kt (State)
       ↓
NavigationController.kt (Business Logic)
       ↓
TabManager.kt (Tab Management)
       ↓
GeckoSession (GeckoView)
       ↓
Web Page Loads
```

### **2. Video Playback Flow**

```
User Action (e.g., click video)
       ↓
BrowserScreen.kt (UI)
       ↓
VideoDetector.kt (Detection)
       ↓
VideoPlayerViewModel.kt (State)
       ↓
VideoPlayerScreen.kt (UI)
       ↓
GeckoSession (GeckoView Video)
       ↓
Video Plays
```

### **3. Settings Flow**

```
User Changes Setting
       ↓
SettingsScreen.kt (UI)
       ↓
SettingsViewModel.kt (State)
       ↓
SettingsRepository.kt (Persistence)
       ↓
DataStore (Storage)
       ↓
Setting Saved
```

---

## 🏗 Design Patterns

### **1. MVVM (Model-View-ViewModel)**

- **View**: Composable functions (UI)
- **ViewModel**: State holders (Business Logic)
- **Model**: Data classes and repositories

### **2. Repository Pattern**

- Separates business logic from data access
- Provides a clean API for data operations
- Handles data from multiple sources (Database, Network, etc.)

### **3. Singleton Pattern**

- Used for managers that should have only one instance (e.g., `GeckoRuntimeManager`)
- Lazy initialization for performance

### **4. Observer Pattern**

- Used with Jetpack Compose's `StateFlow` and `Flow`
- UI automatically updates when state changes

---

## 📊 Module Dependencies

```
┌─────────────────────────┐
│      Presentation        │
│   (UI, ViewModels)       │
└──────────────┬──────────┘
               │
               ▼
┌─────────────────────────┐
│       Domain            │
│   (Use Cases, Models)    │
└──────────────┬──────────┘
               │
               ▼
┌─────────────────────────┐
│       Data              │
│   (Repositories, DAOs)   │
└──────────────┬──────────┘
               │
               ▼
┌─────────────────────────┐
│     External            │
│   (GeckoView, Room, etc.)│
└─────────────────────────┘
```

---

## 🔧 Configuration

### **Gradle**

- **Root `build.gradle.kts`**: Project-wide configuration
- **App `build.gradle.kts`**: App module dependencies
- **`settings.gradle.kts`**: Project settings and dependency resolution

### **AndroidManifest.xml**

- Declares activities, services, and receivers
- Sets permissions
- Configures app metadata

---

## 📈 Performance Considerations

1. **GeckoView**: Use a single `GeckoRuntime` for all sessions
2. **Database**: Use Room with coroutines for background operations
3. **UI**: Use `LazyColumn`/`LazyRow` for large lists
4. **State**: Use `StateFlow` for UI state to minimize recompositions
5. **Images**: Use Coil for image loading

---

## 🔒 Security Considerations

1. **GeckoView**: Keep GeckoView updated to the latest version
2. **HTTPS**: Use HTTPS-only mode for secure connections
3. **Permissions**: Request only necessary permissions
4. **Data**: Clear sensitive data when needed
5. **Biometric**: Use Android's Biometric API for authentication

---

## 🚀 Future Improvements

1. **Dependency Injection**: Implement Hilt or Koin
2. **UseCases**: Explicit UseCase classes for business logic
3. **Testing**: Add more comprehensive tests
4. **Modularization**: Split into multiple modules
5. **Feature Flags**: Add feature flags for experimental features

---

## 📚 Resources

- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Android Architecture Guide](https://developer.android.com/jetpack/guide)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [GeckoView Documentation](https://mozilla.github.io/geckoview/)

---

*Last updated: September 2, 2026*
