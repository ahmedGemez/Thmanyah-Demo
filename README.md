# Thmanyah-Demo

## How to Build & Run

1. **Clone the repository:**
   ```sh
   git clone https://github.com/ahmedGemez/Thmanyah-Demo
   cd Thmanyah-Demo
   ```

2. **Open in Android Studio**.

3. **Sync Gradle** and let dependencies download.

4. **Run the app** on an emulator or device.

## Key Libraries

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Hilt](https://dagger.dev/hilt/)
- [Coil](https://coil-kt.github.io/coil/compose/)
- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
- [Kotlin Coroutines & Flow](https://kotlinlang.org/docs/flow.html)

## Paging

- Home sections are loaded with infinite scroll.
- When the user scrolls near the end, the next page is fetched and appended.

## Search

- Search input uses debounce (200ms) to avoid unnecessary API calls.
- Results are only shown after the user types.

## Architecture

- **MVVM** with Clean Architecture principles.
- **Hilt** for dependency injection.
- **StateFlow** for reactive UI state.
