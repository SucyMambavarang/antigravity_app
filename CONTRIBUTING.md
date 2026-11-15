# Contributing to Antigravity Mobile

Thank you for your interest in contributing to **Antigravity Mobile**! We welcome contributions from developers, designers, and documentation writers of all skill levels.

---

## Table of Contents

- [Code of Conduct](#code-of-conduct)
- [Getting Started](#getting-started)
- [Development Setup](#development-setup)
- [Git Workflow & Branching](#git-workflow--branching)
- [Commit Convention](#commit-convention)
- [Code Style & Standards](#code-style--standards)
- [Security & Privacy Guidelines](#security--privacy-guidelines)
- [Verification & Testing](#verification--testing)
- [Submitting a Pull Request](#submitting-a-pull-request)

---

## Code of Conduct

We are committed to providing a friendly, safe, and welcoming environment for everyone, regardless of background, identity, or experience level.
- Treat fellow contributors with respect, empathy, and professional courtesy.
- Focus on constructive feedback and problem solving.
- Harassment or exclusionary behavior will not be tolerated.

---

## Getting Started

1. **Fork the Repository**: Click the "Fork" button at the top right of the GitHub repository.
2. **Clone your Fork**:
   ```bash
   git clone https://github.com/<your-username>/antigravity-mobile.git
   cd antigravity-mobile
   ```
3. **Configure Upstream Remote**:
   ```bash
   git remote add upstream https://github.com/google-antigravity/antigravity-mobile.git
   git fetch upstream
   ```

---

## Development Setup

### Prerequisites
- **JDK**: Java Development Kit 17 (e.g., OpenJDK 17 or Eclipse Temurin 17).
- **Android SDK**:
  - `compileSdk`: 36
  - `minSdk`: 24 (Android 7.0+)
  - `targetSdk`: 34
  - Android Build Tools 34.0.0+
- **IDE**: Android Studio (Ladybug 2024.2+ recommended) or IntelliJ IDEA with Android plugin.

### Gradle Verification
Verify that your local environment is configured properly:
- **Linux / macOS**:
  ```bash
  ./gradlew --version
  ./gradlew assembleDebug
  ```
- **Windows (PowerShell)**:
  ```powershell
  .\gradlew.bat --version
  .\gradlew.bat assembleDebug
  ```

---

## Git Workflow & Branching

Always create a dedicated feature branch from `main`:
```bash
git checkout main
git pull upstream main
git checkout -b <branch-type>/<short-description>
```

### Branch Naming Conventions
- `feature/<feature-name>`: New features or major enhancements (e.g., `feature/url-history`).
- `fix/<issue-name>`: Bug fixes (e.g., `fix/fullscreen-insets-api34`).
- `perf/<description>`: Performance improvements (e.g., `perf/webview-hardware-accel`).
- `docs/<description>`: Documentation additions or updates (e.g., `docs/architecture-diagram`).
- `refactor/<description>`: Code cleanup or architectural refactoring.

---

## Commit Convention

We follow the [Conventional Commits](https://www.conventionalcommits.org/) specification:

```
<type>(<optional scope>): <description>

[optional body]

[optional footer(s)]
```

### Allowed Types
| Type | Description |
| :--- | :--- |
| `feat` | A new feature or user-facing functionality |
| `fix` | A bug fix |
| `docs` | Documentation changes only |
| `style` | Formatting, whitespace, or lint fixes (no functional changes) |
| `refactor` | Code changes that neither fix a bug nor add a feature |
| `perf` | Changes that improve performance or memory usage |
| `test` | Adding or updating unit tests |
| `chore` | Build scripts, dependencies, or tool configuration |

### Examples
- `feat(ui): add sleek dark session URL input dialog`
- `fix(webview): handle onRenderProcessGone crash gracefully`
- `docs(readme): add bilingual quick start guide`
- `perf(render): enable hardware acceleration for edge-to-edge view`

---

## Code Style & Standards

- **Kotlin Conventions**: Follow the official [Android Kotlin Style Guide](https://developer.android.com/kotlin/style-guide).
- **Architecture**: Keep UI components decoupled from session logic and SharedPreferences management.
- **WebView Security**:
  - Do NOT enable `allowFileAccess` unless strictly necessary.
  - Always enforce HTTPS navigation checks in `shouldOverrideUrlLoading`.
  - Maintain crash resilience via `onRenderProcessGone`.
- **Resource Management**: Define all user-facing strings in `res/values/strings.xml` to support localization.

---

## Security & Privacy Guidelines

> [!CAUTION]
> **Zero Sensitive Data Commitment**
> - **Never** commit hardcoded session tokens, account IDs, cookies, or private remote URLs.
> - **Never** hardcode private IP addresses (e.g., local LAN addresses), internal server addresses, or personal names/emails.
> - Use dynamic user input (`SharedPreferences`) or environment variables for session endpoints.
> - Pull requests containing hardcoded credentials or private data will be rejected immediately.

---

## Verification & Testing

Before submitting your pull request, run the test and build verification suite:

```bash
# 1. Run unit tests
./gradlew test

# 2. Build debug APK
./gradlew assembleDebug

# 3. Check code formatting and linting
./gradlew lint
```

Ensure all tasks complete successfully with zero errors.

---

## Submitting a Pull Request

1. **Commit and Push**:
   ```bash
   git add -A
   git commit -m "feat(module): concise description of changes"
   git push origin <your-branch-name>
   ```
2. **Open PR**: Navigate to the upstream repository on GitHub and open a Pull Request against `main`.
3. **Fill PR Template**:
   - Provide a clear summary of what was changed and why.
   - Reference any related issues (e.g., `Fixes #12`).
   - Describe verification steps performed.
   - Attach screenshots or screen recordings for UI modifications.
4. **Code Review**: Address feedback promptly. Maintainers will review and merge once CI checks pass.

Thank you for helping make **Antigravity Mobile** better for everyone!
