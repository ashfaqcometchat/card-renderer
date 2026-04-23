# Units of Work

## Decomposition Strategy

The library is decomposed into 5 units of work based on logical package boundaries within the single `:cards` module. Each unit is independently implementable and testable, with clear dependencies.

---

## Unit 1: Models & Parser
**Package**: `com.cometchat.cards.models`, `com.cometchat.cards.parser`
**Estimated Effort**: ~4 hours
**Files**: ~8-10

Foundation layer. All typed models (20 element types, 9 action types, CardSchema, ContainerStyle, ColorValue, Padding, enums) and the JSON parser using Kotlinx Serialization.

**Components**:
- All 20 element data classes (sealed interface hierarchy)
- All 9 action data classes (sealed interface hierarchy)
- CometChatCardSchema, CometChatCardContainerStyle, CometChatCardColorValue, CometChatCardPadding
- CometChatCardThemeMode, CometChatCardLogLevel enums
- CometChatCardSchemaParser (parse + serialize)
- SerializersModule configuration

**Dependencies**: None (foundation unit)

---

## Unit 2: Theme & Core Infrastructure
**Package**: `com.cometchat.cards.theme`, `com.cometchat.cards.core`, `com.cometchat.cards.actions`
**Estimated Effort**: ~3 hours
**Files**: ~8-10

Theme resolution, default theme, action emission, logging, registry, render context, and loading state manager.

**Components**:
- CometChatCardDefaultTheme (all color tokens + typography scale)
- CometChatCardThemeOverride (data class with nullable fields)
- CometChatCardResolvedTheme
- CometChatCardThemeResolver (color resolution, URL resolution, theme merging)
- CometChatCardActionEmitter (validation + emission)
- CometChatCardActionCallback, CometChatCardActionEvent
- CometChatCardLogger
- CometChatCardElementRegistry
- CometChatCardRenderContext
- CometChatCardLoadingStateManager

**Dependencies**: Unit 1 (Models)

---

## Unit 3: Content & Data Renderers
**Package**: `com.cometchat.cards.renderers` (content + data subset)
**Estimated Effort**: ~5 hours
**Files**: ~12-14

All 11 content element renderers + 1 data display renderer. Each renderer implements `renderView()` and `RenderComposable()`.

**Components** (12 renderers):
- TextElementRenderer
- ImageElementRenderer (Coil View + Compose integration, shimmer)
- IconElementRenderer (Coil, color tinting)
- AvatarElementRenderer (image or fallback initials)
- BadgeElementRenderer
- DividerElementRenderer
- SpacerElementRenderer
- ChipElementRenderer
- ProgressBarElementRenderer
- CodeBlockElementRenderer (monospace, language label)
- MarkdownElementRenderer (Html.fromHtml + AnnotatedString, link tap → openUrl)
- TableElementRenderer (headers, rows, striped, borders)

**Dependencies**: Unit 1 (Models), Unit 2 (Theme, RenderContext, ActionEmitter)

---

## Unit 4: Layout & Interactive Renderers
**Package**: `com.cometchat.cards.renderers` (layout + interactive subset)
**Estimated Effort**: ~5 hours
**Files**: ~8-10

All 5 layout element renderers (with recursive rendering) + 3 interactive element renderers (with action wiring).

**Components** (8 renderers):
- RowElementRenderer (horizontal, scrollable, peek, snap)
- ColumnElementRenderer (vertical)
- GridElementRenderer (multi-column grid)
- AccordionElementRenderer (expand/collapse, header as string or elements)
- TabsElementRenderer (tab switching, content panels)
- ButtonElementRenderer (4 variants: filled, outlined, text, tonal)
- IconButtonElementRenderer
- LinkElementRenderer

**Dependencies**: Unit 1 (Models), Unit 2 (Theme, RenderContext, ActionEmitter, Registry — for recursive rendering), Unit 3 (content renderers may be children of layouts)

---

## Unit 5: Public API & Integration
**Package**: `com.cometchat.cards`
**Estimated Effort**: ~4 hours
**Files**: ~5-7

Entry points, registry initialization, system theme observation, error handling, and Gradle module setup.

**Components**:
- CometChatCardView (FrameLayout, View API)
- CometChatCardComposable (@Composable, Compose API)
- Registry initialization (register all 20 renderers)
- System theme observation (auto mode)
- Error handling (fallbackText, placeholder)
- Gradle module setup (build.gradle.kts for :cards, dependencies, Maven publish config)
- Demo app updates (:app using the library)

**Dependencies**: Unit 1, Unit 2, Unit 3, Unit 4 (all units)

---

## Implementation Order

```
Unit 1: Models & Parser          (no dependencies)
    ↓
Unit 2: Theme & Core Infra       (depends on Unit 1)
    ↓
Unit 3: Content & Data Renderers (depends on Unit 1, 2)
    ↓
Unit 4: Layout & Interactive     (depends on Unit 1, 2, 3)
    ↓
Unit 5: Public API & Integration (depends on all)
```

## Summary

| Unit | Name | Est. Hours | Est. Files | Dependencies |
|------|------|-----------|-----------|-------------|
| 1 | Models & Parser | ~4h | ~8-10 | None |
| 2 | Theme & Core Infrastructure | ~3h | ~8-10 | Unit 1 |
| 3 | Content & Data Renderers | ~5h | ~12-14 | Unit 1, 2 |
| 4 | Layout & Interactive Renderers | ~5h | ~8-10 | Unit 1, 2, 3 |
| 5 | Public API & Integration | ~4h | ~5-7 | All |
| **Total** | | **~21h** | **~41-51** | |
