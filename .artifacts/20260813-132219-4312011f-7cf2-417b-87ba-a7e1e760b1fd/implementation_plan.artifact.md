# Implementation Plan: Book Explorer App

We will transition the project from the RAWG game domain to a functional Book Explorer app using the Google Books API. This will involve restoring UI capabilities to the `shared` module for a true Multiplatform UI experience (Compose Multiplatform) and implementing the full data flow.

## User Review Required

> [!IMPORTANT]
> **Compose Multiplatform Restoration**: To have a functional UI across platforms, I will re-enable Compose Multiplatform in the `shared` module. This allows writing the UI once and running it on both Android and iOS.

## Proposed Changes

### 1. Build Configuration
- **shared/build.gradle.kts**: Re-add Compose Multiplatform plugins and dependencies.
- **libs.versions.toml**: Ensure all necessary libraries for Compose and Ktor are present.

### 2. Data Layer (`shared`)
- **[NEW] BookDto.kt**: Data classes for Google Books API response.
- **[NEW] BookApi.kt**: Ktor-based client to fetch books.
- **[NEW] BookRepository.kt**: Interface and implementation to map DTOs to Domain models.

### 3. Domain Layer (`shared`)
- **[UPDATE] Item.kt**: Adapt to represent a Book (id, title, authors, cover, etc.).
- **[NEW] GetBooksUseCase.kt**: Logic to retrieve book lists.

### 4. UI Layer (`shared`)
- **[NEW] BookListScreen.kt**: A Compose Multiplatform screen to display books.
- **[NEW] BookListViewModel.kt**: Logic to handle UI state.
- **[NEW] App.kt**: Main entry point for the shared UI.

### 5. Dependency Injection (`shared`)
- **[UPDATE] Koin.kt**: Register new repositories and viewmodels.

## Verification Plan

### Automated Tests
- `./gradlew :shared:assemble` to ensure the module builds with new UI dependencies.
- (Optional) Unit tests for Repository mapping logic.

### Manual Verification
- Deploy to `androidApp` and verify the list of books is displayed.
- Use `take_screenshot` to confirm the UI looks correct.
