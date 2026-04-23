# Tech Stack Decisions

## Core Dependencies

| Dependency | Version | Purpose | Decided At |
|-----------|---------|---------|-----------|
| Kotlin | 2.1.x (latest stable) | Primary language | Requirements Q15 |
| Kotlinx Serialization JSON | Latest stable | Card Schema JSON parsing, polymorphic sealed class deserialization | Requirements Q5 |
| Coil | Latest stable (Coil 3.x) | Async image loading with View + Compose support, caching | Requirements Q3 |
| Coil Compose | Latest stable | Compose-specific image loading (AsyncImage) | Requirements Q3 |
| AndroidX Core KTX | 1.18.0 | Kotlin extensions for Android | Existing project |
| AndroidX AppCompat | 1.7.1 | Backward compatibility | Existing project |
| Jetpack Compose BOM | Latest stable | Compose UI framework | Requirements Q2 |
| Compose UI | Via BOM | Core Compose UI | Requirements Q2 |
| Compose Foundation | Via BOM | Layout primitives (LazyRow, FlowRow, etc.) | Requirements Q2 |
| Compose Material 3 | Via BOM | Material components (TabRow, etc.) | Requirements Q2 |

## Test Dependencies

| Dependency | Version | Purpose | Decided At |
|-----------|---------|---------|-----------|
| Kotest Property Testing | Latest stable | Property-based testing for 28 correctness properties | Requirements Q13 |
| Roborazzi | Latest stable | Snapshot/visual regression testing without device | Requirements Q10 |
| JUnit 4 | 4.13.2 | Unit test runner | Existing project |
| Robolectric | Latest stable | Android unit testing without device (required by Roborazzi) | Requirements Q10 |
| Kotlinx Serialization JSON (test) | Same as main | Test fixture JSON parsing | Implicit |

## Build Tools

| Tool | Version | Purpose |
|------|---------|---------|
| Android Gradle Plugin | 9.1.1 | Android build system | 
| Gradle | Current wrapper | Build automation |
| Kotlin Gradle Plugin | 2.1.x | Kotlin compilation |
| Kotlinx Serialization Plugin | Matches Kotlin | Serialization compiler plugin |
| Maven Publish Plugin | Built-in | Maven Central publishing |

## Decisions NOT Taken (Rationale)

| Alternative | Rejected Because |
|------------|-----------------|
| Glide/Picasso (image loading) | Coil is Kotlin-first, has native Compose support, lighter weight |
| Gson/Moshi (JSON parsing) | Kotlinx Serialization has first-class sealed class support, compile-time safety |
| Markwon (markdown) | Extra dependency; Html.fromHtml + AnnotatedString sufficient for supported subset |
| Facebook Shimmer | Extra dependency; custom Compose animation is lightweight and consistent across View/Compose |
| Paparazzi (snapshots) | Roborazzi chosen for Robolectric integration, better Compose support |
| Two-module split | Single module simpler for publishing, no standalone use case for core-only |

## Gradle Module Configuration

```
:cards (android-library)
├── namespace: com.cometchat.cards
├── minSdk: 24
├── compileSdk: 36
├── kotlin: 2.1.x
├── plugins:
│   ├── com.android.library
│   ├── org.jetbrains.kotlin.android
│   ├── org.jetbrains.kotlin.plugin.serialization
│   └── maven-publish
├── dependencies:
│   ├── implementation: kotlinx-serialization-json
│   ├── implementation: coil + coil-compose
│   ├── implementation: androidx-core-ktx
│   ├── implementation: compose-bom + compose-ui + compose-foundation + compose-material3
│   ├── testImplementation: kotest-property
│   ├── testImplementation: roborazzi
│   ├── testImplementation: robolectric
│   └── testImplementation: junit
└── publishing:
    └── maven: com.cometchat:cards:1.0.0

:app (android-application)
└── implementation(project(":cards"))
```
