# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [Unreleased]

### Added
- Initial project structure with GeckoView integration
- Browser tabs management system
- Video player with fullscreen and PiP support
- Privacy features (Tracking Protection, HTTPS-Only Mode)
- Downloads manager with pause/resume/cancel
- History, Bookmarks, and Video History management
- Settings for General, Video, Privacy, and Downloads
- App shortcuts and widgets support
- Default browser and app links support
- Sharing functionality
- Urdu localization support
- Material 3 theming with dynamic colors
- Edge-to-Edge UI support
- GitHub Actions workflows for CI/CD

### Changed
- Improved project structure and organization
- Enhanced UI/UX with modern Android features
- Better error handling and crash prevention

### Fixed
- GeckoView session leaks
- Database initialization issues
- Progress bar accuracy
- Navigation state loss

---

## [1.0.0] - 2026-09-02

### Added
- First stable release of VIDEOPlay Browser
- Basic browser functionality with GeckoView
- Tab management (new, close, switch)
- Address bar with URL and search
- Home screen with quick access
- Settings screen
- Video player with basic controls
- Privacy settings
- Downloads management

---

## Template

```
## [X.Y.Z] - YYYY-MM-DD

### Added
- Feature 1
- Feature 2

### Changed
- Change 1
- Change 2

### Fixed
- Bug fix 1
- Bug fix 2

### Removed
- Deprecated feature 1
- Deprecated feature 2
```

---

## Types of Changes

- **Added** for new features
- **Changed** for changes in existing functionality
- **Fixed** for any bug fixes
- **Removed** for now removed features
- **Security** in case of vulnerabilities

---

## Notes

- This changelog follows the [Keep a Changelog](https://keepachangelog.com/) format
- Version numbers follow [Semantic Versioning](https://semver.org/)
- Each release should have its own section with the date in ISO 8601 format (YYYY-MM-DD)
- Add new entries at the top under [Unreleased]
- When releasing, move [Unreleased] entries to a new version section with the release date
