# Application Design — CometChatCardsRenderer Android

## Architecture Overview

The library is a single Gradle module:
- **`:cards`** — Complete library (models, parser, theme, registry, actions, logging, renderers, public API)

## Key Architectural Decisions

| Decision | Choice |
|----------|--------|
| Module structure | Single `:cards` module, one published artifact `com.cometchat:cards` |
| Renderer pattern | Single renderer class per element with `renderView()` + `RenderComposable()` methods |
| Coil integration | Separate per API: View extensions for View renderers, Compose extensions for Compose renderers |
| Theme Override API | Data class with nullable fields |
| Serialization | Kotlinx Serialization with `@Serializable` + `@SerialName` + `SerializersModule` polymorphic block |

## Component Summary (11 components)

| # | Component | Module | Purpose |
|---|-----------|--------|---------|
| 1 | CardSchemaParser | :cards-core | JSON → typed models (Kotlinx Serialization) |
| 2 | ElementRegistry | :cards-core | Map type strings → renderer implementations |
| 3 | ThemeResolver | :cards-core | Resolve ColorValue/URLs, merge themes |
| 4 | DefaultTheme | :cards-core | Fallback color/typography values |
| 5 | ActionEmitter | :cards-core | Validate + emit action events |
| 6 | Logger | :cards-core | Configurable log level filtering |
| 7 | RenderContext | :cards-core | Carry rendering state through element tree |
| 8 | ElementRenderers (×20) | :cards | Render elements as View + Composable |
| 9 | CometChatCardView | :cards | Traditional View entry point |
| 10 | CometChatCardComposable | :cards | Compose entry point |
| 11 | LoadingStateManager | :cards-core | Track loading state per element ID |

## Data Flow

```
JSON → CardSchemaParser → CometChatCardSchema
                              ↓
                    ThemeResolver (merge DefaultTheme + Override)
                              ↓
                    RenderContext (mode, theme, callback, depth)
                              ↓
                    ElementRegistry → Renderer lookup per element
                              ↓
                    Native View tree + ContainerStyle
```

## Detailed Artifacts
- [components.md](components.md) — Full component descriptions
- [component-methods.md](component-methods.md) — Method signatures
- [services.md](services.md) — Orchestration flow and patterns
- [component-dependency.md](component-dependency.md) — Dependency matrix and package structure
