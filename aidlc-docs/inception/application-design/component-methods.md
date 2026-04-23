# Component Methods

## 1. CardSchemaParser

```kotlin
class CometChatCardSchemaParser {
    // Parse JSON string into typed CardSchema model
    // Returns Result to handle parse errors gracefully
    fun parse(json: String): Result<CometChatCardSchema>

    // Serialize typed model back to JSON (for round-trip property)
    fun serialize(schema: CometChatCardSchema): String
}
```

## 2. ElementRegistry

```kotlin
class CometChatCardElementRegistry {
    // Register a renderer for an element type
    fun register(type: String, renderer: CometChatCardElementRenderer)

    // Look up renderer by type string, returns null if unknown
    fun getRenderer(type: String): CometChatCardElementRenderer?

    // Register all 20 built-in renderers
    fun registerDefaults()
}
```

## 3. ThemeResolver

```kotlin
class CometChatCardThemeResolver {
    // Resolve a ColorValue or plain hex string to a single hex color
    fun resolveColor(value: Any?, effectiveMode: CometChatCardThemeMode, defaultValue: CometChatCardColorValue?): String?

    // Resolve a theme-aware URL (plain string or {light, dark} object)
    fun resolveUrl(value: Any?, effectiveMode: CometChatCardThemeMode): String?

    // Merge Default_Theme + Theme_Override into ResolvedTheme
    fun resolveTheme(override: CometChatCardThemeOverride?): CometChatCardResolvedTheme

    // Determine effective theme mode (resolve AUTO to light/dark based on system)
    fun resolveEffectiveMode(mode: CometChatCardThemeMode, isSystemDark: Boolean): CometChatCardThemeMode
}
```

## 4. DefaultTheme

```kotlin
object CometChatCardDefaultTheme {
    // All color tokens as CometChatCardColorValue (light + dark)
    val textColor: CometChatCardColorValue
    val secondaryTextColor: CometChatCardColorValue
    val backgroundColor: CometChatCardColorValue
    val borderColor: CometChatCardColorValue
    val dividerColor: CometChatCardColorValue
    val buttonFilledBg: CometChatCardColorValue
    val buttonFilledText: CometChatCardColorValue
    val buttonTonalBg: CometChatCardColorValue
    val progressBarColor: CometChatCardColorValue
    val progressTrackColor: CometChatCardColorValue
    val codeBlockBg: CometChatCardColorValue
    // ... all other tokens from design doc

    // Typography scale
    val typography: Map<String, CometChatCardTypography>  // variant -> {fontSize, fontWeight}

    // Default font family (null = platform default Roboto)
    val fontFamily: String?
}
```

## 5. ActionEmitter

```kotlin
class CometChatCardActionEmitter {
    // Validate action and emit to callback if valid
    // Returns true if emitted, false if validation failed or no callback
    fun emit(
        action: CometChatCardAction?,
        elementId: String,
        cardJson: String,
        callback: CometChatCardActionCallback?,
        isDisabled: Boolean = false
    ): Boolean

    // Validate required fields for a specific action type
    fun isValidAction(action: CometChatCardAction): Boolean
}
```

## 6. Logger

```kotlin
object CometChatCardLogger {
    // Current log level (default: WARNING)
    var logLevel: CometChatCardLogLevel

    fun error(message: String)
    fun warning(message: String)
    fun verbose(message: String)
}
```

## 7. RenderContext

```kotlin
data class CometChatCardRenderContext(
    val themeMode: CometChatCardThemeMode,
    val resolvedTheme: CometChatCardResolvedTheme,
    val onAction: CometChatCardActionCallback?,
    val cardJson: String,
    val depth: Int,
    val renderElement: (CometChatCardElement, Int) -> Any,  // returns View or @Composable
    val loadingStateManager: CometChatCardLoadingStateManager,
    val logger: CometChatCardLogger
) {
    // Create child context with incremented depth
    fun withDepth(newDepth: Int): CometChatCardRenderContext
}
```

## 8. CometChatCardElementRenderer (Base Interface)

```kotlin
interface CometChatCardElementRenderer {
    // Render element as traditional Android View
    fun renderView(
        context: Context,
        element: CometChatCardElement,
        renderContext: CometChatCardRenderContext
    ): View

    // Render element as Jetpack Compose composable
    @Composable
    fun RenderComposable(
        element: CometChatCardElement,
        renderContext: CometChatCardRenderContext
    )
}
```

## 9. CometChatCardView (View Entry Point)

```kotlin
class CometChatCardView : FrameLayout {
    fun setCardSchema(json: String)
    fun setThemeMode(mode: CometChatCardThemeMode)
    fun setActionCallback(callback: CometChatCardActionCallback?)
    fun setThemeOverride(override: CometChatCardThemeOverride?)
    fun setLogLevel(level: CometChatCardLogLevel)
    fun setElementLoading(elementId: String, loading: Boolean)
    fun getContainerStyle(): CometChatCardContainerStyle?

    // Internal
    private fun render()
    private fun parseAndRender(json: String)
    private fun renderBody(body: List<CometChatCardElement>, context: CometChatCardRenderContext)
    private fun showFallback(text: String)
    private fun showPlaceholder()
}
```

## 10. CometChatCardComposable (Compose Entry Point)

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

## 11. LoadingStateManager

```kotlin
class CometChatCardLoadingStateManager {
    fun setLoading(elementId: String, loading: Boolean)
    fun isLoading(elementId: String): Boolean
    fun clearAll()
}
```
