# CometChat Cards Android — API Documentation

## Public API

### CometChatCardComposable

Jetpack Compose entry point for rendering Card Schema JSON.

```kotlin
@Composable
fun CometChatCardComposable(
    cardJson: String,
    themeMode: CometChatCardThemeMode = CometChatCardThemeMode.AUTO,
    onAction: ((CometChatCardActionEvent) -> Unit)? = null,
    themeOverride: CometChatCardThemeOverride? = null,
    logLevel: CometChatCardLogLevel = CometChatCardLogLevel.WARNING,
    onContainerStyle: ((CometChatCardContainerStyle) -> Unit)? = null
)
```

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| cardJson | String | required | Card Schema JSON string |
| themeMode | CometChatCardThemeMode | AUTO | Light/dark mode resolution |
| onAction | ((CometChatCardActionEvent) -> Unit)? | null | Callback for action events |
| themeOverride | CometChatCardThemeOverride? | null | Override default theme colors |
| logLevel | CometChatCardLogLevel | WARNING | Log verbosity |
| onContainerStyle | ((CometChatCardContainerStyle) -> Unit)? | null | Receives container style from JSON |

---

### CometChatCardView

Traditional View (FrameLayout) entry point for rendering Card Schema JSON.

```kotlin
class CometChatCardView(context: Context, attrs: AttributeSet? = null) : FrameLayout
```

| Method | Description |
|--------|-------------|
| `setCardSchema(json: String)` | Set the Card Schema JSON and trigger render |
| `setThemeMode(mode: CometChatCardThemeMode)` | Set light/dark mode |
| `setActionCallback(callback: CometChatCardActionCallback?)` | Set action event listener |
| `setThemeOverride(override: CometChatCardThemeOverride?)` | Override default theme |
| `setLogLevel(level: CometChatCardLogLevel)` | Set log verbosity |
| `setElementLoading(elementId: String, loading: Boolean)` | Set loading state on interactive element |
| `getContainerStyle(): CometChatCardContainerStyle?` | Get container style from parsed JSON |

---

## Models

### CometChatCardThemeMode

```kotlin
enum class CometChatCardThemeMode {
    AUTO,   // Follow system theme
    LIGHT,  // Force light
    DARK    // Force dark
}
```

### CometChatCardLogLevel

```kotlin
enum class CometChatCardLogLevel {
    NONE,      // No logs
    ERROR,     // Parse failures, crashes
    WARNING,   // Skipped elements, missing props (default)
    VERBOSE    // Everything
}
```

### CometChatCardActionEvent

Emitted when user taps an interactive element.

```kotlin
data class CometChatCardActionEvent(
    val action: CometChatCardAction,  // Action details
    val elementId: String,             // ID of tapped element
    val cardJson: String               // Full card JSON
)
```

### CometChatCardAction (sealed interface)

| Subclass | Properties |
|----------|-----------|
| CometChatCardOpenUrlAction | url: String, openIn: String? |
| CometChatCardChatWithUserAction | uid: String |
| CometChatCardChatWithGroupAction | guid: String |
| CometChatCardSendMessageAction | text: String, receiverUid: String?, receiverGuid: String? |
| CometChatCardCopyToClipboardAction | value: String |
| CometChatCardDownloadFileAction | url: String, filename: String? |
| CometChatCardInitiateCallAction | callType: String, uid: String?, guid: String? |
| CometChatCardApiCallAction | url: String, method: String?, headers: Map?, body: JsonElement? |
| CometChatCardCustomCallbackAction | callbackId: String, payload: JsonElement? |

### CometChatCardContainerStyle

Extracted from the card JSON's `style` field.

```kotlin
data class CometChatCardContainerStyle(
    val background: CometChatCardColorOrHex?,
    val borderRadius: Int?,
    val borderColor: CometChatCardColorOrHex?,
    val borderWidth: Int?,
    val padding: CometChatCardPadding?,
    val maxWidth: Int?,
    val maxHeight: Int?,
    val width: CometChatCardDimension?,
    val height: CometChatCardDimension?
)
```

### CometChatCardThemeOverride

Override any default theme color token.

```kotlin
data class CometChatCardThemeOverride(
    val textColor: CometChatCardColorValue? = null,
    val secondaryTextColor: CometChatCardColorValue? = null,
    val backgroundColor: CometChatCardColorValue? = null,
    val borderColor: CometChatCardColorValue? = null,
    val dividerColor: CometChatCardColorValue? = null,
    val buttonFilledBg: CometChatCardColorValue? = null,
    val buttonFilledText: CometChatCardColorValue? = null,
    // ... all other tokens
    val fontFamily: String? = null
)
```

### CometChatCardColorValue

Theme-aware color with light and dark hex values.

```kotlin
data class CometChatCardColorValue(
    val light: String,  // e.g. "#FFFFFF"
    val dark: String    // e.g. "#1A1A1A"
)
```

### CometChatCardColorOrHex

Color value in JSON — can be themed object, plain hex, or transparent.

```kotlin
sealed interface CometChatCardColorOrHex {
    data class Hex(val value: String) : CometChatCardColorOrHex
    data class Themed(val colorValue: CometChatCardColorValue) : CometChatCardColorOrHex
    data object Transparent : CometChatCardColorOrHex
}
```

### CometChatCardPadding

```kotlin
sealed interface CometChatCardPadding {
    data class Uniform(val value: Int) : CometChatCardPadding
    data class PerSide(val top: Int, val right: Int, val bottom: Int, val left: Int) : CometChatCardPadding
}
```

---

## Element Types (20)

### Content Elements

| Type | Class | Required | Optional |
|------|-------|----------|----------|
| text | CometChatCardTextElement | id, content | variant, color, fontWeight, align, maxLines, padding |
| image | CometChatCardImageElement | id, url | altText, fit, width, height, borderRadius, padding |
| icon | CometChatCardIconElement | id, name | size, color, backgroundColor, borderRadius, padding |
| avatar | CometChatCardAvatarElement | id | imageUrl, fallbackInitials, size, borderRadius, backgroundColor, fontSize, fontWeight |
| badge | CometChatCardBadgeElement | id, text | color, backgroundColor, borderColor, borderWidth, borderRadius, fontSize, padding |
| divider | CometChatCardDividerElement | id | color, thickness, margin |
| spacer | CometChatCardSpacerElement | id, height | — |
| chip | CometChatCardChipElement | id, text | color, icon, backgroundColor, borderColor, borderWidth, borderRadius, fontSize, padding |
| progressBar | CometChatCardProgressBarElement | id, value | color, trackColor, height, label, borderRadius, labelFontSize, labelColor |
| codeBlock | CometChatCardCodeBlockElement | id, content | language, backgroundColor, textColor, padding, borderRadius, fontSize, languageLabelFontSize, languageLabelColor |
| markdown | CometChatCardMarkdownElement | id, content | baseFontSize, linkColor, color, lineHeight |

### Layout Elements

| Type | Class | Required | Optional |
|------|-------|----------|----------|
| row | CometChatCardRowElement | id, items | gap, align, wrap, scrollable, peek, snap, padding, backgroundColor, borderRadius, borderColor, borderWidth |
| column | CometChatCardColumnElement | id, items | gap, align, padding, backgroundColor, borderRadius, borderColor, borderWidth |
| grid | CometChatCardGridElement | id, items | columns (2-4), gap, padding, backgroundColor, borderRadius, borderColor, borderWidth |
| accordion | CometChatCardAccordionElement | id, header, body | headerIcon, expandedByDefault, border, padding, fontSize, fontWeight, borderRadius |
| tabs | CometChatCardTabsElement | id, tabs | defaultActiveTab, tabAlign, tabPadding, contentPadding, fontSize |

### Interactive Elements

| Type | Class | Required | Optional |
|------|-------|----------|----------|
| button | CometChatCardButtonElement | id, label, action | backgroundColor, textColor, borderColor, borderWidth, borderRadius, padding, fontSize, icon, iconPosition, size, fullWidth, disabled |
| iconButton | CometChatCardIconButtonElement | id, icon, action | size, backgroundColor, color, borderRadius |
| link | CometChatCardLinkElement | id, text, action | color, underline, fontSize |

### Data Display

| Type | Class | Required | Optional |
|------|-------|----------|----------|
| table | CometChatCardTableElement | id, columns, rows | stripedRows, headerBackgroundColor, border, cellPadding, fontSize, stripedRowColor, borderColor |

---

## Error Behavior

| Scenario | Result |
|----------|--------|
| Invalid JSON | fallbackText shown as plain text |
| Missing body/version | "Unable to display this message" |
| Unknown element type | Skipped silently |
| Missing required field | Element skipped, warning logged |
| Image load failure | altText or placeholder shown |
| Nesting > 5 levels | Over-nested element skipped |
| Invalid action fields | Tap is no-op |
| No callback set | All taps are no-ops |

---

## Dependencies

| Library | Version | Purpose |
|---------|---------|---------|
| Kotlinx Serialization JSON | 1.8.1 | JSON parsing |
| Coil 3 | 3.1.0 | Image loading (View + Compose) |
| Compose BOM | 2025.04.01 | Compose UI |
| AndroidX Core KTX | 1.18.0 | Android extensions |
| AndroidX AppCompat | 1.7.1 | Backward compatibility |
