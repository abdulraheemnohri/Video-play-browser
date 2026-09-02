# Contributing to VIDEOPlay Browser

We welcome contributions from the community! Here's how you can help make VIDEOPlay Browser better.

---

## 📌 Code of Conduct

By participating in this project, you agree to abide by our [Code of Conduct](CODE_OF_CONDUCT.md).

---

## 🤝 How Can I Contribute?

### **Reporting Bugs**

If you find a bug, please open an issue on GitHub with the following information:

- **Android version**
- **Device model**
- **Steps to reproduce**
- **Expected behavior**
- **Actual behavior**
- **Screenshots or videos** (if applicable)
- **Logcat output** (if available)

### **Suggesting Features**

Have an idea for a new feature? Open an issue on GitHub with:

- A clear description of the feature
- The problem it solves
- Any relevant mockups or examples
- Potential implementation details

### **Submitting Pull Requests**

1. **Fork** the repository
2. **Clone** your fork
3. **Create a new branch** (`git checkout -b feature/your-feature`)
4. **Make your changes**
5. **Commit** your changes (`git commit -m 'feat: Add your feature'`)
6. **Push** to the branch (`git push origin feature/your-feature`)
7. **Open a Pull Request**

---

## 🛠 Development Setup

### **Prerequisites**

- Android Studio (Latest Stable)
- JDK 17+
- Android SDK (API 24+)
- Git

### **Setup**

1. Clone the repository:
   ```bash
   git clone https://github.com/abdulraheemnohri/Video-play-browser.git
   cd Video-play-browser
   ```

2. Open in Android Studio:
   - File → Open → Select the project

3. Sync Gradle:
   - Click **Sync Now** in Android Studio

4. Build & Run:
   - Select a device/emulator
   - Click **Run (▶)**

---

## 📂 Project Structure

```
VideoPlayBrowser/
├── app/                          # Main Android app module
│   ├── src/main/
│   │   ├── java/com/videoplay/browser/
│   │   │   ├── ui/               # UI Components
│   │   │   ├── viewmodel/       # ViewModels
│   │   │   ├── gecko/           # GeckoView integration
│   │   │   ├── video/           # Video features
│   │   │   ├── tabs/            # Tab management
│   │   │   ├── browser/         # Browser functionality
│   │   │   ├── database/        # Room database
│   │   │   ├── privacy/         # Privacy features
│   │   │   ├── downloads/       # Download management
│   │   │   ├── settings/        # App settings
│   │   │   └── android/         # Android-specific features
│   │   └── res/                 # Resources
│   └── build.gradle.kts
├── .github/                      # GitHub configuration
│   └── workflows/               # GitHub Actions workflows
├── build.gradle.kts              # Root build file
├── settings.gradle.kts           # Project settings
├── README.md                    # Project documentation
├── CONTRIBUTING.md              # This file
├── CODE_OF_CONDUCT.md          # Code of Conduct
└── LICENSE                      # License
```

---

## 🎯 Coding Guidelines

### **Kotlin**

- Follow [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful names for variables, functions, and classes
- Prefer immutability (`val` over `var`)
- Use extension functions where appropriate
- Keep functions small and focused

### **Jetpack Compose**

- Follow [Compose Guidelines](https://developer.android.com/jetpack/compose/documentation)
- Use `remember` for state that survives recomposition
- Use `derivedStateOf` for derived state
- Avoid unnecessary recompositions
- Use `LazyColumn`/`LazyRow` for lists

### **Architecture**

- Follow Clean Architecture principles
- Use MVVM pattern
- Separate concerns (UI, Business Logic, Data)
- Use dependency injection (manual for now)
- Keep ViewModels thin
- Use UseCases for business logic

---

## 🧪 Testing

### **Unit Tests**

- Test ViewModels, UseCases, and Repositories
- Use JUnit and Mockito
- Keep tests fast and isolated

### **UI Tests**

- Test Compose UI components
- Use Compose Testing library
- Test user interactions

### **Integration Tests**

- Test interactions between components
- Test database operations
- Test network operations

---

## 📝 Commit Message Guidelines

Use [Conventional Commits](https://www.conventionalcommits.org/) format:

- `feat: Add new feature`
- `fix: Fix a bug`
- `docs: Update documentation`
- `style: Format code`
- `refactor: Refactor code`
- `test: Add tests`
- `chore: Maintenance tasks`

---

## 🎨 Design Guidelines

- Follow Material Design 3
- Use dynamic colors
- Support dark mode
- Ensure accessibility
- Support RTL (Right-to-Left) languages

---

## 🚀 Pull Request Guidelines

1. **Title**: Clear and descriptive
2. **Description**: Explain what the PR does
3. **Linked Issues**: Reference any related issues
4. **Screenshots**: Include if UI changes
5. **Testing**: Describe how it was tested
6. **Breaking Changes**: Note any breaking changes

---

## 🙏 Recognition

All contributors will be recognized in the project's CONTRIBUTORS.md file (if created).

---

## 📬 Contact

For questions about contributing, please open an issue or contact:

- **GitHub**: [@abdulraheemnohri](https://github.com/abdulraheemnohri)
- **Email**: abdulraheemnohri@gmail.com

---

Thank you for contributing to VIDEOPlay Browser! 🎉
