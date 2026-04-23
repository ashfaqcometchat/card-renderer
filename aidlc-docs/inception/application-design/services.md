# Services

## Rendering Orchestration Service

The rendering pipeline is orchestrated by the entry points (`CometChatCardView` and `CometChatCardComposable`). There is no separate "service" layer — the entry points coordinate the components directly.

### Orchestration Flow

```
1. Consumer provides: cardJson, themeMode, actionCallback, themeOverride, logLevel
2. Entry Point:
   a. Configure Logger with logLevel
   b. Parse cardJson via CardSchemaParser → CometChatCardSchema (or error)
   c. Extract ContainerStyle from schema.style
   d. Determine effective theme mode (resolve AUTO via system observation)
   e. Resolve theme: merge DefaultTheme + ThemeOverride via ThemeResolver → ResolvedTheme
   f. Create RenderContext with: effectiveMode, resolvedTheme, actionCallback, cardJson, depth=0
   g. Iterate schema.body array:
      - For each element: lookup renderer in ElementRegistry by element.type
      - If renderer found: call renderView() or RenderComposable()
      - If renderer null: log warning, skip element
   h. Return rendered view tree + ContainerStyle
3. Error paths:
   a. Parse failure → show fallbackText as plain text
   b. Missing body/version → show "Unable to display this message"
   c. Element render failure → skip element, continue siblings
```

### System Theme Observation

#### View API (CometChatCardView)
- Observe `Configuration.uiMode` via `onConfigurationChanged` callback
- When system theme changes in AUTO mode: re-resolve all colors, re-render

#### Compose API (CometChatCardComposable)
- Use `isSystemInDarkTheme()` composable
- Compose recomposition handles re-rendering automatically when theme changes

### Recursive Rendering (Layout Elements)

Layout renderers (row, column, grid, accordion, tabs) call back into the registry to render their children:

```
LayoutRenderer.render(element, context):
    if context.depth > MAX_DEPTH (5):
        logger.warning("Max nesting depth exceeded")
        return empty view
    for child in element.items:
        renderer = registry.getRenderer(child.type)
        if renderer != null:
            childView = renderer.render(child, context.withDepth(context.depth + 1))
            add childView to layout container
        else:
            logger.warning("Unknown element type: ${child.type}")
```

### Action Emission Flow

Interactive element renderers wire tap handlers that delegate to ActionEmitter:

```
InteractiveRenderer.render(element, context):
    nativeView = createNativeView(element)
    nativeView.onTap = {
        actionEmitter.emit(
            action = element.action,
            elementId = element.id,
            cardJson = context.cardJson,
            callback = context.onAction,
            isDisabled = element.disabled ?: false
        )
    }
```
