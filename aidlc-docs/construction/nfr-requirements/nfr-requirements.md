# NFR Requirements

## Performance

### NFR-PERF-1: Initial Render Time
- Card with up to 50 elements must render within 100ms on mid-range devices (excluding image load time)
- Measured from `setCardSchema()` call to view tree attached

### NFR-PERF-2: Image Loading
- Async image loading with in-memory and disk caching via Coil
- Shimmer placeholder during loading (custom implementation, no external shimmer library)
- Graceful fallback on failure (altText or placeholder icon)

### NFR-PERF-3: Theme Re-resolution
- System theme change in auto mode must re-resolve colors and update view within one frame (16ms)
- Compose: handled by recomposition automatically
- View: re-render triggered by `onConfigurationChanged`

## Testing

### NFR-TEST-1: Unit Test Coverage
- Minimum 80% code coverage across all units
- Every public method must have at least one unit test

### NFR-TEST-2: Property-Based Testing (PBT)
- Framework: Kotest Property Testing (`io.kotest:kotest-property`)
- All 28 correctness properties from design doc implemented as PBT tests
- Minimum 100 iterations per property
- Each test tagged: `Feature: cometchat-card-message, Property {N}: {description}`
- Custom generators for domain types (element models, action models, ColorValue, Padding)
- Shrinking enabled, seed logged on failure

### NFR-TEST-3: Snapshot Testing
- Framework: Roborazzi (Robolectric-based, no device needed)
- Snapshot tests for each of the 20 element types
- Snapshot tests for button variants (filled, outlined, text, tonal)
- Snapshot tests for light and dark theme modes

### NFR-TEST-4: Shared Test Fixtures
- 13 Card_Schema JSON fixtures from design doc maintained as test resources
- Used across unit tests, PBT tests, and snapshot tests

## Compatibility

### NFR-COMPAT-1: Android SDK
- minSdk: 24 (Android 7.0)
- targetSdk: 36
- compileSdk: 36

### NFR-COMPAT-2: Kotlin
- Kotlin 2.1.x (latest stable)
- Modern features: sealed interfaces, value classes

### NFR-COMPAT-3: Jetpack Compose
- Compose BOM (latest stable)
- Material 3 components where applicable

## Distribution

### NFR-DIST-1: Maven Central
- Published as `com.cometchat:cards`
- Single AAR artifact
- POM with all transitive dependencies declared

### NFR-DIST-2: Versioning
- Semantic versioning (starting at 1.0.0)
- Schema version "1.0" supported

## Security (Extension Enabled)

### NFR-SEC-1: Input Validation
- All JSON input validated via Kotlinx Serialization schema
- No raw string concatenation for any processing
- Unknown fields silently ignored (no injection vector)

### NFR-SEC-2: No Hardcoded Credentials
- Library contains no API keys, tokens, or secrets
- All URLs come from Card Schema JSON (consumer-provided)

### NFR-SEC-3: Dependency Security
- All dependencies pinned to exact versions via `libs.versions.toml`
- No `latest` or unpinned versions

## Accessibility

### NFR-A11Y-1: Screen Reader Support
- All content elements have accessibility labels
- Interactive elements marked as action-accessible
- Accordion announces expanded/collapsed state
- Tabs expose as selectable with labels
- Images without altText marked as decorative

### NFR-A11Y-2: Focus Traversal
- Top-to-bottom, left-to-right reading order following card body sequence
