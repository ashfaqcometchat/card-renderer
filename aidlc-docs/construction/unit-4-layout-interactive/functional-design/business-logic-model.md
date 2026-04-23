# Unit 4: Layout & Interactive Renderers — Business Logic Model

## Layout Rendering — Recursive Pattern

All 5 layout renderers follow this recursive pattern:
```
renderLayout(element, context):
    if context.depth > 5:
        logger.warning("Max nesting depth exceeded, id=${element.id}")
        return empty view
    
    container = createLayoutContainer(element)  // LinearLayout, GridLayout, etc.
    applyContainerStyle(container, element)      // padding, bg, border, borderRadius
    
    for child in element.items (or body/tabs):
        renderer = registry.getRenderer(child.type)
        if renderer != null:
            childView = renderer.render(child, context.withDepth(context.depth + 1))
            container.addView(childView)
        else:
            logger.warning("Unknown element type: ${child.type}, id: ${child.id}")
    
    return container
```

## Row Renderer
- Container: `LinearLayout(HORIZONTAL)` (View) / `Row` (Compose)
- `gap`: set as margin between children
- `align`: map to gravity/arrangement:
  - start → Gravity.START / Arrangement.Start
  - center → Gravity.CENTER / Arrangement.Center
  - end → Gravity.END / Arrangement.End
  - spaceBetween → Arrangement.SpaceBetween
  - spaceAround → Arrangement.SpaceAround
- `wrap`: if true, use `FlexboxLayout` (View) / `FlowRow` (Compose)
- `scrollable`: if true, wrap in `HorizontalScrollView` (View) / `LazyRow` (Compose)
  - `peek`: set padding end = -peek dp to show next item edge
  - `snap`: "item" → `SnapHelper` (View) / `SnapFlingBehavior` (Compose); "free" → no snap
- Apply `padding`, `backgroundColor`, `borderRadius`, `borderColor`, `borderWidth`

## Column Renderer
- Container: `LinearLayout(VERTICAL)` (View) / `Column` (Compose)
- `gap`: set as margin between children
- `align`: map to gravity/crossAxisAlignment:
  - start → Gravity.START / CrossAxisAlignment.Start
  - center → Gravity.CENTER_HORIZONTAL / CrossAxisAlignment.CenterHorizontally
  - end → Gravity.END / CrossAxisAlignment.End
  - stretch → MATCH_PARENT width / fillMaxWidth()
- Apply `padding`, `backgroundColor`, `borderRadius`, `borderColor`, `borderWidth`

## Grid Renderer
- Container: `GridLayout` (View) / `LazyVerticalGrid` (Compose)
- `columns`: 2-4 (default 2), set as column count
- `gap`: set as spacing between cells
- Apply `padding`, `backgroundColor`, `borderRadius`, `borderColor`, `borderWidth`

## Accordion Renderer
- **Header rendering**:
  - If `header` is Text: render as `TextView`/`Text` with `fontSize`, `fontWeight`, plus arrow indicator icon
    - If `headerIcon` provided: load icon URL, display before text
  - If `header` is Elements: render elements in horizontal row, `headerIcon` ignored
- **Body**: render child elements vertically (recursive)
- **State**: local `isExpanded` boolean, initialized from `expandedByDefault` (default false)
- **Toggle**: tap header → toggle `isExpanded`
  - View: `AnimatorSet` or `TransitionManager` for smooth expand/collapse
  - Compose: `AnimatedVisibility` with `expandVertically`/`shrinkVertically`
- Apply `border`, `padding`, `borderRadius`
- Accessibility: announce "expanded"/"collapsed" state

## Tabs Renderer
- **Tab bar**: render tab labels horizontally
  - `tabAlign`: start → left-aligned, center → centered, stretch → fill width equally
  - Apply `tabPadding`, `fontSize`
  - Active tab: highlighted with `tabActiveColor` from theme
  - Inactive tabs: `tabInactiveColor` from theme
- **Content panel**: render only active tab's content array
- **State**: local `activeTabIndex`, initialized from `defaultActiveTab` (default 0)
- **Switching**: tap tab label → set `activeTabIndex`, re-render content panel only
  - View: `TabLayout` + `ViewPager2` or manual tab switching
  - Compose: `TabRow` + content `Box` keyed by index
- Apply `contentPadding`
- Accessibility: expose tabs as selectable, announce selected tab

## Button Renderer
- Render tappable button with `label` text
- **Variant styling** (from design doc):
  - `filled`: solid `backgroundColor`, text in `textColor`, no border
  - `outlined`: transparent bg, border = `borderColor` (or `backgroundColor`), text = `textColor` (or `backgroundColor`)
  - `text`: transparent bg, no border, text = `backgroundColor`
  - `tonal`: bg = `backgroundColor` at 15% opacity, text = `backgroundColor`
- `icon`: if present, load URL via Coil, position per `iconPosition` (left/right)
- `size`: small (height 32dp), medium (height 44dp, default), large (height 52dp)
- `fullWidth`: if true, width = MATCH_PARENT / fillMaxWidth()
- `disabled`: if true, apply disabled colors from theme, ignore taps
- **Tap handler**: emit action via ActionEmitter
- **Loading state**: if `loadingStateManager.isLoading(element.id)`, show spinner instead of label/icon, disable taps

## IconButton Renderer
- Render tappable icon-only button
- Load `icon` URL via Coil
- Apply `size` (default 24dp), `backgroundColor`, `color` (tint), `borderRadius`
- **Tap handler**: emit action via ActionEmitter
- **Loading state**: spinner replaces icon when loading

## Link Renderer
- Render inline tappable text
- Apply `color` (default from theme linkColor), `underline` (default true), `fontSize`
- **Tap handler**: emit action via ActionEmitter
- View: `ClickableSpan` on `TextView`
- Compose: `ClickableText` with `TextDecoration.Underline`

## Testable Properties (PBT-01)

| Property | Category | Description |
|----------|----------|-------------|
| P6: Unknown types skipped | Invariant | Mix of known/unknown → only known rendered, in order |
| P10: Button variant styling | Invariant | Each variant produces correct bg/text/border combination |
| P11: Action emission | Round-trip | Interactive tap → correct ActionEvent emitted |
| P12: Non-interactive handling | Invariant | Null/invalid action or disabled → no emission |
| P13: Layout child arrangement | Invariant | Row=horizontal, Column=vertical, Grid=N columns |
| P14: Recursive rendering | Invariant | Nested layouts render all levels correctly |
| P15: Max nesting depth | Invariant | Depth >5 → element skipped, siblings continue |
| P16: Accordion toggle | Idempotence | Toggle twice → back to original state |
| P17: Tab selection | Invariant | Select tab I → only tab I content visible |
| P23: Loading state | Round-trip | Set loading → spinner shown; clear → restored |
| P26: Scrollable row | Invariant | scrollable=true → scroll container wrapping |
