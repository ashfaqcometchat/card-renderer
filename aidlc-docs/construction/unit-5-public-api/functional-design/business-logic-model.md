# Unit 5: Public API & Integration — Business Logic Model

## CometChatCardView Orchestration (View API)

```
setCardSchema(json):
    1. Store json
    2. Call render()

render():
    1. Remove all child views
    2. Parse json via CardSchemaParser
       - If failure: showFallback(schema.fallbackText) or showPlaceholder()
       - If success: continue
    3. Extract ContainerStyle from schema.style, store for getContainerStyle()
    4. Determine effective theme mode:
       - If AUTO: read system Configuration.uiMode → LIGHT or DARK
       - If LIGHT/DARK: use as-is
    5. Resolve theme: ThemeResolver.resolveTheme(themeOverride) → ResolvedTheme
    6. Create RenderContext(effectiveMode, resolvedTheme, actionCallback, json, depth=0, ...)
    7. Create vertical LinearLayout container
    8. For each element in schema.body:
       a. renderer = registry.getRenderer(element.type)
       b. If renderer != null:
          try { childView = renderer.renderView(context, element, renderContext) }
          catch { logger.error("Renderer exception: ${element.type}"); continue }
          container.addView(childView)
       c. If renderer == null:
          logger.warning("Unknown element type: ${element.type}")
    9. Add container to this FrameLayout

showFallback(text):
    - If text is non-null and non-blank: render as plain TextView
    - Else: showPlaceholder()

showPlaceholder():
    - Render TextView with "Unable to display this message"
```

## CometChatCardComposable Orchestration (Compose API)

```
@Composable
CometChatCardComposable(cardJson, themeMode, onAction, themeOverride, logLevel, onContainerStyle):
    1. Configure logger: CometChatCardLogger.logLevel = logLevel
    2. Parse cardJson via remember { parser.parse(cardJson) }
       - If failure: render FallbackText or PlaceholderText
    3. Extract ContainerStyle, emit via onContainerStyle callback
    4. Determine effective mode:
       - If AUTO: val isDark = isSystemInDarkTheme() → DARK or LIGHT
       - If LIGHT/DARK: use as-is
    5. Resolve theme via remember(themeOverride) { resolver.resolveTheme(themeOverride) }
    6. Create RenderContext
    7. Column(modifier = Modifier.fillMaxWidth()) {
         schema.body.forEach { element ->
           val renderer = registry.getRenderer(element.type)
           if (renderer != null) {
             try { renderer.RenderComposable(element, renderContext) }
             catch { logger.error(...) }
           } else {
             logger.warning(...)
           }
         }
       }
```

## System Theme Observation

### View API
```
onConfigurationChanged(newConfig):
    if themeMode == AUTO:
        val newIsDark = (newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        if newIsDark != currentIsDark:
            currentIsDark = newIsDark
            render()  // re-render with new theme
```

### Compose API
- `isSystemInDarkTheme()` is a composable that automatically triggers recomposition when system theme changes
- No manual observation needed — Compose handles it

## Registry Initialization

```
fun createDefaultRegistry(): CometChatCardElementRegistry {
    val registry = CometChatCardElementRegistry()
    // Content (11)
    registry.register("text", TextElementRenderer())
    registry.register("image", ImageElementRenderer())
    registry.register("icon", IconElementRenderer())
    registry.register("avatar", AvatarElementRenderer())
    registry.register("badge", BadgeElementRenderer())
    registry.register("divider", DividerElementRenderer())
    registry.register("spacer", SpacerElementRenderer())
    registry.register("chip", ChipElementRenderer())
    registry.register("progressBar", ProgressBarElementRenderer())
    registry.register("codeBlock", CodeBlockElementRenderer())
    registry.register("markdown", MarkdownElementRenderer())
    // Layout (5)
    registry.register("row", RowElementRenderer())
    registry.register("column", ColumnElementRenderer())
    registry.register("grid", GridElementRenderer())
    registry.register("accordion", AccordionElementRenderer())
    registry.register("tabs", TabsElementRenderer())
    // Interactive (3)
    registry.register("button", ButtonElementRenderer())
    registry.register("iconButton", IconButtonElementRenderer())
    registry.register("link", LinkElementRenderer())
    // Data Display (1)
    registry.register("table", TableElementRenderer())
    return registry
}
```

## Error Handling Chain

```
Card_Schema JSON
  ├── Parse succeeds → Render body elements
  │     ├── Element renders → Add to view
  │     └── Element fails → Skip, log error, continue siblings
  ├── Parse fails → Display fallbackText
  │     ├── fallbackText exists → Plain text view
  │     └── fallbackText missing → "Unable to display this message"
  └── JSON is null/empty → "Unable to display this message"
```

## Gradle Module Setup

```
:cards module (build.gradle.kts):
  - android-library plugin
  - namespace = "com.cometchat.cards"
  - minSdk = 24, compileSdk = 36
  - Kotlin 2.1.x
  - Dependencies:
    - kotlinx-serialization-json
    - coil (View + Compose)
    - androidx-core-ktx
    - compose BOM + compose-ui + compose-foundation + compose-material3
    - kotest-property (testImplementation)
    - roborazzi (testImplementation)
  - Maven publish configuration for com.cometchat:cards

:app module:
  - implementation(project(":cards"))
  - Demo activity showing card rendering
```

## Testable Properties (PBT-01)

| Property | Category | Description |
|----------|----------|-------------|
| P5: Body array child count | Invariant | N recognized elements → N child views in root container |
| P21: Accessibility labeling | Invariant | Content elements have labels, interactive elements are action-accessible |
| P27: Font family override | Invariant | ThemeOverride fontFamily → all text uses overridden font |
