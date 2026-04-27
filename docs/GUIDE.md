# CometChat Cards Android — Developer Guide

## What is it?

CometChat Cards is an Android library that renders Card Schema JSON into native Android views. You give it a JSON string, it gives you a rendered card — either as a traditional `android.view.View` or a Jetpack Compose composable.

The library is a pure renderer. It does not execute actions, manage messages, or integrate with any SDK. When a user taps a button or link, the library emits the action to your callback — you decide what to do with it.

## Installation

Add the Cloudsmith repository and dependency:

```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://dl.cloudsmith.io/public/cometchat/call-team/maven/") }
    }
}

// app/build.gradle.kts
dependencies {
    implementation("com.cometchat:cards-android:1.0.0-beta.1")
}
```

Requirements: minSdk 24, Kotlin, Android Gradle Plugin 9.x

Your app's `AndroidManifest.xml` must include:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

This is required because the library loads images via network (Coil/OkHttp).

## Quick Start

### Jetpack Compose

```kotlin
import com.cometchat.cards.CometChatCardComposable
import com.cometchat.cards.models.CometChatCardThemeMode

@Composable
fun MyScreen() {
    CometChatCardComposable(
        cardJson = """{"version":"1.0","body":[{"type":"text","id":"t1","content":"Hello!"}],"fallbackText":"Hello"}""",
        themeMode = CometChatCardThemeMode.AUTO,
        onAction = { event ->
            // Handle action: event.action, event.elementId
        }
    )
}
```

### Traditional View (XML/Activity)

```kotlin
import com.cometchat.cards.CometChatCardView
import com.cometchat.cards.models.CometChatCardThemeMode

val cardView = CometChatCardView(context)
cardView.setCardSchema("""{"version":"1.0","body":[{"type":"text","id":"t1","content":"Hello!"}],"fallbackText":"Hello"}""")
cardView.setThemeMode(CometChatCardThemeMode.AUTO)
cardView.setActionCallback { event ->
    // Handle action: event.action, event.elementId
}

// Add to your layout
parentLayout.addView(cardView)
```

## API Reference

### CometChatCardComposable (Compose)

```kotlin
@Composable
fun CometChatCardComposable(
    cardJson: String,                                          // Required: Card Schema JSON
    themeMode: CometChatCardThemeMode = CometChatCardThemeMode.AUTO,  // AUTO, LIGHT, or DARK
    onAction: ((CometChatCardActionEvent) -> Unit)? = null,    // Action callback
    themeOverride: CometChatCardThemeOverride? = null,         // Override default theme colors
    logLevel: CometChatCardLogLevel = CometChatCardLogLevel.WARNING,  // Logging level
    onContainerStyle: ((CometChatCardContainerStyle) -> Unit)? = null  // Container style callback
)
```

### CometChatCardView (View)

```kotlin
class CometChatCardView : FrameLayout {
    fun setCardSchema(json: String)
    fun setThemeMode(mode: CometChatCardThemeMode)
    fun setActionCallback(callback: CometChatCardActionCallback?)
    fun setThemeOverride(override: CometChatCardThemeOverride?)
    fun setLogLevel(level: CometChatCardLogLevel)
    fun setElementLoading(elementId: String, loading: Boolean)
    fun getContainerStyle(): CometChatCardContainerStyle?
}
```

## Card Schema JSON Format

Every card has this structure:

```json
{
    "version": "1.0",
    "body": [ /* array of elements */ ],
    "style": { /* optional container styling */ },
    "fallbackText": "Plain text for older clients"
}
```

### Container Style

```json
"style": {
    "background": {"light": "#FFFFFF", "dark": "#1E1E1E"},
    "borderRadius": 12,
    "borderColor": {"light": "#E0E0E0", "dark": "#3A3A3A"},
    "borderWidth": 1,
    "padding": 16
}
```

The library extracts this and returns it via `getContainerStyle()` (View) or `onContainerStyle` (Compose) so you can apply it to your message bubble wrapper.

## Elements (20 types)

### Content Elements (11)

| Type | Required Fields | Description |
|------|----------------|-------------|
| text | id, content | Text with variant, color, fontWeight, align, maxLines |
| image | id, url | Async image with fit, dimensions, borderRadius, shimmer loading |
| icon | id, name | Icon from URL with size, color tinting, backgroundColor |
| avatar | id | Circular image or fallback initials |
| badge | id, text | Compact label with colors, border |
| divider | id | Horizontal line separator |
| spacer | id, height | Empty vertical space |
| chip | id, text | Compact chip with optional icon (display-only, not interactive) |
| progressBar | id, value | Horizontal bar (0-100), with optional label |
| codeBlock | id, content | Monospace text with language label |
| markdown | id, content | Rich text (bold, italic, links, lists, inline code) |

### Layout Elements (5)

| Type | Required Fields | Description |
|------|----------------|-------------|
| row | id, items | Horizontal layout with gap, align, scrollable, wrap |
| column | id, items | Vertical layout with gap, align |
| grid | id, items | Grid layout with 2-4 columns |
| accordion | id, header, body | Collapsible section with expand/collapse |
| tabs | id, tabs | Tabbed container with tab switching |

### Interactive Elements (3)

| Type | Required Fields | Description |
|------|----------------|-------------|
| button | id, label, action | Tappable button with colors, border, icon |
| iconButton | id, icon, action | Icon-only button |
| link | id, text, action | Inline tappable text |

### Data Display (1)

| Type | Required Fields | Description |
|------|----------------|-------------|
| table | id, columns, rows | Data table with headers, striped rows, borders |

## Color Values

Colors can be:

```json
// Theme-aware (light/dark)
"color": {"light": "#141414", "dark": "#E8E8E8"}

// Plain hex (same in both modes)
"color": "#3A3AF4"

// Transparent
"backgroundColor": "transparent"
```

## Padding

```json
// Uniform (all sides)
"padding": 16

// Per-side
"padding": {"top": 8, "right": 16, "bottom": 8, "left": 16}
```

## Actions (9 types)

When a user taps an interactive element, the library emits an action event:

```kotlin
data class CometChatCardActionEvent(
    val action: CometChatCardAction,  // The action object
    val elementId: String,             // Which element was tapped
    val cardJson: String               // Full card JSON for context
)
```

| Action Type | Parameters | What to do |
|------------|-----------|-----------|
| openUrl | url, openIn | Open browser or webview |
| chatWithUser | uid | Navigate to 1:1 chat |
| chatWithGroup | guid | Navigate to group chat |
| sendMessage | text, receiverUid, receiverGuid | Send a text message |
| copyToClipboard | value | Copy to system clipboard |
| downloadFile | url, filename | Download a file |
| initiateCall | callType (audio/video), uid, guid | Start a call |
| apiCall | url, method, headers, body | Make HTTP request |
| customCallback | (type only) | App-specific logic |

Example handling:

```kotlin
cardView.setActionCallback { event ->
    when (event.action) {
        is CometChatCardOpenUrlAction -> {
            val url = (event.action as CometChatCardOpenUrlAction).url
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
        is CometChatCardChatWithUserAction -> {
            val uid = (event.action as CometChatCardChatWithUserAction).uid
            // Navigate to chat with user
        }
        is CometChatCardCustomCallbackAction -> {
            Log.d("Card", "Custom action on element: ${event.elementId}")
        }
        // ... handle other types
    }
}
```

## Theme Mode

```kotlin
CometChatCardThemeMode.AUTO   // Follow system light/dark (default)
CometChatCardThemeMode.LIGHT  // Always light
CometChatCardThemeMode.DARK   // Always dark
```

In AUTO mode, the library observes system theme changes and re-renders automatically.

## Theme Override

Override default theme colors:

```kotlin
val override = CometChatCardThemeOverride(
    textColor = CometChatCardColorValue(light = "#000000", dark = "#FFFFFF"),
    buttonFilledBg = CometChatCardColorValue(light = "#FF6B6B", dark = "#FF6B6B"),
    fontFamily = "sans-serif-medium"
)

// Compose
CometChatCardComposable(cardJson = json, themeOverride = override)

// View
cardView.setThemeOverride(override)
```

Color precedence: JSON value > ThemeOverride > DefaultTheme

## Loading State

Set interactive elements to a loading state:

```kotlin
// View API
cardView.setElementLoading("btn_123", true)   // Show spinner
cardView.setElementLoading("btn_123", false)  // Restore

// Compose: managed via LoadingStateManager internally
```

## Log Levels

```kotlin
CometChatCardLogLevel.NONE     // No output
CometChatCardLogLevel.ERROR    // Parse failures, render crashes
CometChatCardLogLevel.WARNING  // Skipped elements, missing properties (default)
CometChatCardLogLevel.VERBOSE  // Everything including render details
```

Logs use tag `CometChatCards` in Logcat.

## Error Handling

| Scenario | Behavior |
|----------|----------|
| Malformed JSON | Shows fallbackText as plain text |
| Missing body/version | Shows "Unable to display this message" |
| Unknown element type | Skipped, siblings continue rendering |
| Missing required property | Element skipped, warning logged |
| Image load failure | Shows altText or placeholder icon |
| Nesting depth > 5 | Over-nested element skipped |
| Invalid action (empty url, etc.) | Tap is a no-op |
| No action callback provided | All taps are no-ops |

## Architecture

```
JSON String
    → CometChatCardSchemaParser (Kotlinx Serialization)
    → CometChatCardSchema (typed models)
    → CometChatCardThemeResolver (resolve colors for light/dark)
    → CometChatCardElementRegistry (map type → renderer)
    → 20 Element Renderers (each has renderView + RenderComposable)
    → Native View tree or Compose UI
```

The library uses a registry pattern: a `Map<String, Renderer>` with all 20 element types registered. Layout elements (row, column, grid, accordion, tabs) render children recursively through the registry.

## Building from Source

```bash
# Build library
./gradlew :cards:assembleRelease

# Run tests
./gradlew :cards:testDebugUnitTest

# Generate distribution (AAR + POM)
./gradlew :cards:assembleRelease :cards:publish
# Output: cards/distribution/com/cometchat/cards-android/1.0.0-beta.1/

# Build demo apps
./gradlew :app-compose:assembleDebug    # Compose demo
./gradlew :app-xml:assembleDebug        # XML/View demo
```

## Project Structure

```
card-renderer-android/
├── cards/                          ← Library module
│   └── src/main/java/com/cometchat/cards/
│       ├── models/                 ← 20 element types, 9 action types, CardSchema
│       ├── parser/                 ← JSON parser (Kotlinx Serialization)
│       ├── theme/                  ← DefaultTheme, ThemeResolver, ThemeOverride
│       ├── actions/                ← ActionEmitter, ActionCallback, ActionEvent
│       ├── core/                   ← Registry, RenderContext, Logger, LoadingState
│       ├── renderers/              ← 20 element renderers (View + Compose each)
│       ├── CometChatCardView.kt    ← View entry point
│       └── CometChatCardComposable.kt ← Compose entry point
├── app-compose/                    ← Compose demo app
├── app-xml/                        ← XML/View demo app
├── design.md                       ← Architecture design document
├── requirements.md                 ← Requirements specification
└── schema.md                       ← Card Schema JSON specification
```
