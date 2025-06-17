# Thmanyah Demo

A modern Android application showcasing the latest Android development practices and libraries.

## Features

- Modern Android Architecture Components
- Jetpack Compose UI
- MVVM Architecture Pattern
- Dependency Injection with Hilt
- Kotlin Coroutines for asynchronous operations
- Retrofit for network calls
- Material 3 Design
- Clean Architecture
- Unit Testing

## Tech Stack

- **Language**: Kotlin
- **Architecture**: MVVM + Clean Architecture
- **UI**: Jetpack Compose
- **DI**: Hilt
- **Async**: Coroutines + Flow
- **Network**: Retrofit + OkHttp
- **Image Loading**: Coil
- **Testing**: JUnit, Mockk, and Turbine

## Project Structure

```
app/
├── data/           # Data layer
│   ├── api/       # API interfaces and models
│   └── repositoryImpl/ # Repository implementations
├── domain/        # Domain layer
│   ├── model/     # Domain models
│   ├── repository/# Repository interfaces
│   └── usecase/   # Use cases
└── ui/            # Presentation layer
    ├── common/    # Common UI components
    ├── home/      # Home screen
    ├── search/    # Search screen
    └── theme/     # App theme
```

## Testing

The project includes comprehensive testing at different levels:

### Unit Tests
- Located in `src/test/java`
- Tests for ViewModels, UseCases
- Uses JUnit, Mockk, and Turbine

### Running Tests
```bash
# Run unit tests only
./gradlew testDebugUnitTest

```

## Getting Started

1. Clone the repository
2. Open the project in Android Studio
3. Sync the project with Gradle files
4. Run the app on an emulator or physical device

## Requirements

- Android Studio 
- JDK 17
- Android SDK 34
- Kotlin 1.8.0

## Dependencies

- Jetpack Compose BOM: 2024.02.00
- Hilt: 2.50
- Retrofit: 2.9.0
- Room: 2.6.1
- Coil: 2.5.0
- Navigation Compose: 2.7.7
- Material3: 1.2.0
- Testing:
- JUnit: 4.13.2
- Mockk: 1.13.9

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Key Libraries

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Hilt](https://dagger.dev/hilt/)
- [Coil](https://coil-kt.github.io/coil/compose/)
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- [Kotlin Coroutines & Flow](https://kotlinlang.org/docs/flow.html)

## Paging Home

- Home sections are loaded with infinite scroll.
- When the user scrolls near the end, the next page is fetched and appended.

## Search

- Search input uses debounce (200ms) to avoid unnecessary API calls.
- Results are only shown after the user types.

## Architecture

- **MVVM** with Clean Architecture principles.
- **Hilt** for dependency injection.
- **StateFlow** for reactive UI state.
