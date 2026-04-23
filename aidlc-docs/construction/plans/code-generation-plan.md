# Code Generation Plan

## Linear Sub-Issues

| Unit | Linear ID | Title |
|------|-----------|-------|
| Unit 1 | ENG-34364 | Models & Parser |
| Unit 2 | ENG-34365 | Theme & Core Infrastructure |
| Unit 3 | ENG-34366 | Content & Data Renderers |
| Unit 4 | ENG-34367 | Layout & Interactive Renderers |
| Unit 5 | ENG-34368 | Public API & Integration |

Parent: ENG-34127

## Code Location

All code goes in the `:cards` library module:
- **Source**: `cards/src/main/java/com/cometchat/cards/`
- **Tests**: `cards/src/test/java/com/cometchat/cards/`
- **Test fixtures**: `cards/src/test/resources/fixtures/`

## Execution Steps

### Unit 1: Models & Parser (ENG-34364)

- [x] Step 1.1: Create `:cards` Gradle module with build.gradle.kts, dependencies, Kotlin serialization plugin
- [x] Step 1.2: Create enum classes — CometChatCardThemeMode, CometChatCardLogLevel
- [x] Step 1.3: Create supporting types — CometChatCardColorValue, CometChatCardColorOrHex (with custom serializer), CometChatCardPadding (with custom serializer), CometChatCardDimension (with custom serializer)
- [x] Step 1.4: Create CometChatCardAction sealed interface + 9 action data classes with @Serializable/@SerialName
- [x] Step 1.5: Create CometChatCardElement sealed interface + 20 element data classes with @Serializable/@SerialName (including CometChatCardAccordionHeader custom serializer, CometChatCardTabItem)
- [x] Step 1.6: Create CometChatCardContainerStyle, CometChatCardSchema
- [x] Step 1.7: Create CometChatCardSchemaParser with SerializersModule, parse() and serialize() methods
- [x] Step 1.8: Create CometChatCardActionEvent, CometChatCardActionCallback
- [x] Step 1.9: Unit tests for parser — all 20 element types, all 9 action types, round-trip, unknown fields, malformed JSON
- [x] Step 1.10: Unit 1 summary documentation

### Unit 2: Theme & Core Infrastructure (ENG-34365)

- [x] Step 2.1: Create CometChatCardDefaultTheme — all color tokens + typography scale
- [x] Step 2.2: Create CometChatCardThemeOverride data class with nullable fields
- [x] Step 2.3: Create CometChatCardResolvedTheme and CometChatCardThemeResolver — resolveColor, resolveUrl, resolveTheme, resolveEffectiveMode
- [x] Step 2.4: Create CometChatCardActionEmitter — emit, isValidAction
- [x] Step 2.5: Create CometChatCardLogger — log level filtering, android.util.Log backend
- [x] Step 2.6: Create CometChatCardElementRenderer interface (renderView + RenderComposable)
- [x] Step 2.7: Create CometChatCardElementRegistry — register, getRenderer, registerDefaults stub
- [x] Step 2.8: Create CometChatCardRenderContext — all fields, withDepth()
- [x] Step 2.9: Create CometChatCardLoadingStateManager — setLoading, isLoading, clearAll
- [x] Step 2.10: Unit tests for theme resolver, action emitter, logger, loading state manager
- [x] Step 2.11: Unit 2 summary documentation

### Unit 3: Content & Data Renderers (ENG-34366)

- [x] Step 3.1: Create TextElementRenderer (renderView + RenderComposable)
- [x] Step 3.2: Create ImageElementRenderer (Coil View + Compose, custom shimmer, fallback)
- [x] Step 3.3: Create IconElementRenderer (Coil, color tinting)
- [x] Step 3.4: Create AvatarElementRenderer (image or fallback initials)
- [x] Step 3.5: Create BadgeElementRenderer
- [x] Step 3.6: Create DividerElementRenderer
- [x] Step 3.7: Create SpacerElementRenderer
- [x] Step 3.8: Create ChipElementRenderer
- [x] Step 3.9: Create ProgressBarElementRenderer (value clamping 0-100)
- [x] Step 3.10: Create CodeBlockElementRenderer (monospace, language label)
- [x] Step 3.11: Create MarkdownElementRenderer (Html.fromHtml + AnnotatedString, link tap → openUrl)
- [x] Step 3.12: Create TableElementRenderer (headers, rows, striped, borders)
- [x] Step 3.13: Unit tests for all 12 renderers
- [x] Step 3.14: Unit 3 summary documentation

### Unit 4: Layout & Interactive Renderers (ENG-34367)

- [x] Step 4.1: Create RowElementRenderer (horizontal, scrollable/peek/snap, wrap)
- [x] Step 4.2: Create ColumnElementRenderer (vertical, align)
- [x] Step 4.3: Create GridElementRenderer (multi-column)
- [x] Step 4.4: Create AccordionElementRenderer (expand/collapse, dual header, animation)
- [x] Step 4.5: Create TabsElementRenderer (tab switching, content panels)
- [x] Step 4.6: Create ButtonElementRenderer (4 variants, icon, size, fullWidth, disabled, loading)
- [x] Step 4.7: Create IconButtonElementRenderer (action wiring, loading)
- [x] Step 4.8: Create LinkElementRenderer (action wiring)
- [x] Step 4.9: Unit tests for all 8 renderers + recursive rendering + depth enforcement
- [x] Step 4.10: Unit 4 summary documentation

### Unit 5: Public API & Integration (ENG-34368)

- [x] Step 5.1: Update settings.gradle.kts to include `:cards` module
- [x] Step 5.2: Create CometChatCardView (FrameLayout) — setCardSchema, setThemeMode, setActionCallback, setThemeOverride, setLogLevel, setElementLoading, getContainerStyle, render pipeline, system theme observation
- [x] Step 5.3: Create CometChatCardComposable — @Composable entry point, isSystemInDarkTheme, onContainerStyle
- [x] Step 5.4: Create registry initialization function (registerDefaults with all 20 renderers)
- [x] Step 5.5: Error handling — fallbackText display, placeholder view, element-level try-catch
- [x] Step 5.6: Update :app module — add dependency on :cards, create demo activity
- [x] Step 5.7: Create shared test fixtures (13 JSON files)
- [x] Step 5.8: Integration tests — full card rendering pipeline
- [x] Step 5.9: Maven publish configuration in build.gradle.kts
- [x] Step 5.10: Unit 5 summary documentation
