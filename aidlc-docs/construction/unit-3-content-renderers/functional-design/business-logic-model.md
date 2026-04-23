# Unit 3: Content & Data Renderers — Business Logic Model

## Rendering Pattern (All 12 Renderers)

Each renderer follows the same pattern:
```
1. Extract element properties
2. Resolve theme-aware values (colors, URLs) via RenderContext.resolvedTheme
3. Apply default values for missing optional properties
4. Create native view (View) or composable (Compose)
5. Apply all visual properties
6. Set accessibility labels
7. Return native view
```

## Text Renderer
- Map `variant` to fontSize + fontWeight from typography scale
- Apply `color` (resolve ColorOrHex), `fontWeight`, `align` (Gravity/textAlign)
- If `maxLines` set: `maxLines = value`, `ellipsize = TextUtils.TruncateAt.END`
- Apply `padding`
- Accessibility: label = content text

## Image Renderer
- Resolve URL (theme-aware: plain string or {light, dark})
- Load via Coil: `ImageView.load(url)` (View) / `AsyncImage(url)` (Compose)
- Apply `fit`: cover → CenterCrop, contain → CenterInside, fill → FitXY
- Resolve `width`/`height` (Dp, Percent, or default 200dp height)
- Apply `borderRadius` via `ShapeableImageView` (View) or `clip(RoundedCornerShape)` (Compose)
- Loading state: custom shimmer animation
- Failure state: show `altText` as text or generic placeholder icon
- Accessibility: label = altText, or decorative if no altText

## Icon Renderer
- Resolve `name` URL (theme-aware)
- Load via Coil with size constraint
- Apply `color` tint via `setColorFilter` (View) or `ColorFilter.tint` (Compose)
- Apply `backgroundColor`, `borderRadius`, `padding`
- Failure: generic placeholder icon

## Avatar Renderer
- If `imageUrl` present: load circular image via Coil with `CircleCropTransformation`
- If no `imageUrl`: render circle with `backgroundColor`, center `fallbackInitials` text
- Apply `size` (width=height=size), `borderRadius` (default = size/2 for circle)
- Apply `fontSize`, `fontWeight` for initials

## Badge Renderer
- Render compact label: `text` centered
- Apply `backgroundColor`, `color` (text), `borderColor`, `borderWidth`, `borderRadius`, `fontSize`, `padding`
- Default: borderRadius=10, fontSize=12

## Divider Renderer
- Render horizontal `View` with height = `thickness` (default 1dp)
- Apply `color` (resolve, default from theme dividerColor)
- Apply `margin` as vertical margin

## Spacer Renderer
- Render empty `View`/`Spacer` with height = `height` dp
- No visual properties, no accessibility

## Chip Renderer
- Render compact chip: optional `icon` (URL, loaded via Coil) + `text`
- Apply `backgroundColor`, `color`, `borderColor`, `borderWidth`, `borderRadius` (default 14), `fontSize` (default 13), `padding`
- NOT interactive (no action property)

## ProgressBar Renderer
- Clamp `value` to 0-100: `val clamped = value.coerceIn(0, 100)`
- Render horizontal bar: filled portion = clamped%, track = remaining
- Apply `color` (fill), `trackColor` (background), `height` (default 4dp), `borderRadius`
- If `label` present: render label text with `labelFontSize`, `labelColor`

## CodeBlock Renderer
- Render `content` in monospace font (`Typeface.MONOSPACE`)
- If `language` present: render language label above code
- Apply `backgroundColor` (default from theme codeBlockBg), `textColor`, `padding`, `borderRadius`, `fontSize` (default 13)
- Apply `languageLabelFontSize`, `languageLabelColor` to language label

## Markdown Renderer
- **View**: Convert markdown subset to HTML, render via `Html.fromHtml(html, FROM_HTML_MODE_COMPACT)`
  - `**text**` → `<b>text</b>`
  - `*text*` → `<i>text</i>`
  - `[text](url)` → `<a href="url">text</a>`
  - `- item` → `<ul><li>item</li></ul>`
  - `1. item` → `<ol><li>item</li></ol>`
  - `` `code` `` → `<code>code</code>`
- **Compose**: Build `AnnotatedString` with `SpanStyle` for bold/italic/code, `LinkAnnotation` for links
- Link taps → emit `openUrl` action via ActionEmitter
- Apply `baseFontSize`, `linkColor`, `color`, `lineHeight`

## Table Renderer
- Render header row from `columns` string array with `headerBackgroundColor`
- Render data rows from `rows` (string[][])
- If `stripedRows`: alternate row backgrounds using `stripedRowColor` or theme default
- If `border`: render cell borders using `borderColor`
- Apply `cellPadding` (default 8) and `fontSize` (default 14) to all cells

## Testable Properties (PBT-01)

| Property | Category | Description |
|----------|----------|-------------|
| P5: Body array child count | Invariant | N recognized elements → N child views |
| P7: Element properties applied | Invariant | All specified properties reflected in native view |
| P8: Text maxLines | Invariant | maxLines set → native maxLines matches |
| P9: ProgressBar clamping | Invariant | Any value → clamped 0-100 |
| P18: Markdown conversion | Round-trip | Supported subset → correct rich text elements |
| P22: Table rendering | Invariant | N columns, M rows → correct header + data cells |
| P24: Avatar fallback | Invariant | No imageUrl → initials displayed |
| P25: Image altText | Invariant | altText → accessibility label set |
