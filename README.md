# QuizNotion 📚

<div align="center">
  <img src="app/src/main/res/drawable/quiz_app_logo.xml" alt="QuizNotion Logo" width="150"/>
  
  ### A Modern Android Quiz Application
  
  [![Kotlin](https://img.shields.io/badge/Kotlin-2.1.10-purple.svg)](https://kotlinlang.org)
  [![Compose](https://img.shields.io/badge/Jetpack%20Compose-Latest-green.svg)](https://developer.android.com/jetpack/compose)
  [![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)
  [![Min SDK](https://img.shields.io/badge/Min%20SDK-26-orange.svg)](https://developer.android.com/about/versions/oreo)
</div>

---

## 📖 About

QuizNotion is a feature-rich Android quiz application built with modern Android development practices. It provides an interactive learning experience with multiple quiz topics, real-time score tracking, and comprehensive result analysis.

## ✨ Features

- 🎯 **Multiple Quiz Topics** - Various categories to test your knowledge
- 📊 **Real-time Progress Tracking** - Monitor your performance across all quizzes
- 🎨 **Modern UI/UX** - Beautiful Material Design 3 interface
- 💾 **Offline Support** - Access previously loaded quizzes without internet
- 📝 **Detailed Explanations** - Learn from correct answers with explanations
- 🐛 **Issue Reporting** - Report problems with questions directly in the app
- 🌙 **Dark Mode** - Comfortable viewing in any lighting condition
- ⚡ **Smooth Animations** - Engaging user experience with fluid transitions

## 🏗️ Architecture

This project follows **Clean Architecture** principles with **MVVM** pattern:

```
┌─────────────────────────────────────────┐
│          Presentation Layer             │
│  (UI, ViewModels, Compose Screens)      │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│           Domain Layer                  │
│   (Use Cases, Models, Repositories)     │
└─────────────────────────────────────────┘
                  ↓
┌─────────────────────────────────────────┐
│            Data Layer                   │
│  (Remote API, Local DB, DataStore)      │
└─────────────────────────────────────────┘
```

### Key Components

- **Presentation**: Jetpack Compose UI with ViewModels
- **Domain**: Business logic and repository interfaces
- **Data**: Room database, Ktor networking, DataStore preferences

## 🛠️ Tech Stack

### Core
- **Kotlin** - Programming language
- **Jetpack Compose** - Modern UI toolkit
- **Material Design 3** - Design system
- **Coroutines & Flow** - Asynchronous programming

### Architecture Components
- **ViewModel** - UI state management
- **Navigation Component** - Screen navigation
- **Room** - Local database
- **DataStore** - Key-value storage

### Networking & Serialization
- **Ktor Client** - HTTP networking
- **Kotlinx Serialization** - JSON parsing

### Dependency Injection
- **Koin** - Dependency injection framework

### Image Loading
- **Coil 3** - Image loading library

### Other Libraries
- **Kotlinx DateTime** - Date and time handling
- **Google Fonts** - Custom typography

## 📱 Screenshots

<details>
<summary>Click to expand screenshots</summary>

| Dashboard | Quiz Screen | Results |
|-----------|------------|---------|
| ![Dashboard](screenshots/dashboard.png) | ![Quiz](screenshots/quiz.png) | ![Results](screenshots/results.png) |

</details>

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog | 2023.1.1 or later
- JDK 11 or higher
- Android SDK with minimum API level 26
- Gradle 8.13

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/QuizNotion.git
   cd QuizNotion
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned repository

3. **Sync Gradle**
   - Wait for Gradle sync to complete
   - Resolve any dependency issues if prompted

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click the "Run" button or press `Shift + F10`

## 🌐 API Configuration

The app connects to a backend API. The base URL is configured in:

```kotlin
// app/src/main/java/com/example/quiznotion/data/util/Constant.kt
const val BASE_URL = "http://quizznotion.us-east-1.elasticbeanstalk.com"
```

To use your own backend, replace this URL with your API endpoint.

## 📦 Project Structure

```
app/
├── data/
│   ├── local/          # Room database, DAOs, entities
│   ├── remote/         # Ktor API client, DTOs
│   ├── repository/     # Repository implementations
│   └── mapper/         # Data mappers
├── domain/
│   ├── model/          # Domain models
│   ├── repository/     # Repository interfaces
│   └── util/           # Domain utilities
└── presentation/
    ├── dashboard/      # Dashboard screen
    ├── quiz/           # Quiz screen
    ├── result/         # Results screen
    ├── issue_report/   # Issue reporting screen
    ├── navigation/     # Navigation setup
    ├── component/      # Reusable UI components
    └── theme/          # App theming
```

## 🎨 Design System

The app uses a custom Material Design 3 color scheme:

- **Primary**: Golden Yellow (#6D5E0F)
- **Secondary**: Warm Gray (#665E40)
- **Tertiary**: Forest Green (#43664E)

Fonts: Ubuntu (via Google Fonts)

## 🧪 Testing

```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

## 📝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Coding Standards

- Follow [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Write meaningful commit messages
- Add comments for complex logic
- Update documentation when needed

## 📄 License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

```
Copyright 2025 QuizNotion

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## 👨‍💻 Author

**Your Name**
- GitHub: [@yourusername](https://github.com/yourusername)
- LinkedIn: [Your Name](https://linkedin.com/in/yourprofile)
- Email: your.email@example.com

## 🙏 Acknowledgments

- [Jetpack Compose](https://developer.android.com/jetpack/compose) - UI toolkit
- [Material Design 3](https://m3.material.io/) - Design guidelines
- [Ktor](https://ktor.io/) - Networking library
- [Koin](https://insert-koin.io/) - Dependency injection
- [Coil](https://coil-kt.github.io/coil/) - Image loading

## 📧 Support

For support, email support@quiznotion.com or open an issue in the repository.

---

<div align="center">
  Made with ❤️ using Jetpack Compose
  
  ⭐ Star this repo if you found it helpful!
</div>
