# Build Instructions

## Prerequisites
- **Build Tool**: Gradle 8.x+ with Kotlin DSL (wrapper included)
- **JDK**: JDK 11+ (required by Android Gradle Plugin 9.1.1)
- **Android SDK**: compileSdk 36, minSdk 24
- **Kotlin**: 2.1.20
- **System Requirements**: macOS/Linux/Windows, 8GB+ RAM recommended

## Build Steps

### 1. Install Dependencies
```bash
./gradlew :cards:dependencies
```

### 2. Build Library Module
```bash
./gradlew :cards:assembleDebug
```

### 3. Build Release AAR
```bash
./gradlew :cards:assembleRelease
```

### 4. Build Demo App
```bash
./gradlew :app:assembleDebug
```

### 5. Verify Build Success
- **Expected Output**: `BUILD SUCCESSFUL` with no errors
- **Build Artifacts**:
  - `cards/build/outputs/aar/cards-debug.aar`
  - `cards/build/outputs/aar/cards-release.aar`
- **Common Warnings**: Compose compiler warnings are acceptable

## Troubleshooting

### Build Fails with Dependency Errors
- **Cause**: Missing or incompatible dependency versions
- **Solution**: Run `./gradlew --refresh-dependencies :cards:assembleDebug`

### Build Fails with Kotlin Version Mismatch
- **Cause**: Compose compiler requires matching Kotlin version
- **Solution**: Ensure `kotlin` version in `libs.versions.toml` matches the Compose compiler plugin version

### Build Fails with SDK Not Found
- **Cause**: Android SDK 36 not installed
- **Solution**: Open Android Studio SDK Manager and install API 36
