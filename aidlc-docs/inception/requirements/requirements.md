# Requirements Document — CometChatCardsRenderer Android

## Intent Analysis
- **User Request**: Build the Android platform package for CometChatCardsRenderer library using AI-DLC workflow
- **Request Type**: New Feature
- **Scope Estimate**: Single Component (Android library module)
- **Complexity Estimate**: Complex — 20 element types, 9 action types, theme system, registry pattern, dual API surface (View + Compose)
- **Linear Ticket**: ENG-34127

## Project Overview

CometChatCardsRenderer (Android) is a standalone rendering library that converts Card Schema JSON into native Android views. The library follows a strict input/output contract:

- **Input**: Card_Schema JSON, Theme_Mode (auto | light | dark), optional Action_Callback, optional Theme_Override
- **Output**: Rendered native Android view hierarchy + Container_Style object

The library is a pure renderer — it does not execute actions, manage message lifecycle, or integrate with any SDK/UIKit layer. All interactive element taps are emitted to the consumer via the Action_Callback.

## Technical Decisions (from Requirements Verification)

| Decision | Choice | Rationale |
|----------|--------|-----------|
| Platform Scope | Android only | ENG-34127 scoped to Android |
| API Surface | Both Traditional View + Jetpack Compose | Full design spec coverage |
| Image Loading | Coil | Design doc specification |
| Markdown Rendering | Html.fromHtml (View) + AnnotatedString (Compose) | Built-in approach, no extra dependencies |
| JSON Parsing | Kotlinx Serialization | Sealed class polymorphic serialization support |
| Module Structure | Single `:cards` library module alongside `:app` demo, one published artifact | Simpler build, single dependency for consumers |
| Package Name | `com.cometchat.cards` | Design doc specification |
| Default Theme Colors | Use placeholder values from design doc | Reasonable defaults until design team finalizes |
| Shimmer | Custom implementation (Compose animation / Canvas) | No external dependency |
| Snapshot Testing | Roborazzi | Robolectric-based, no device needed |
| Loading State API | Included | Full feature set |
| Security Extension | Enabled (Full) | Production-grade library |
| PBT Extension | Enabled (Full) | Serialization, data transformations, business logic |
| Distribution | Maven Central | Public, standard for open-source |
| Kotlin Version | Latest stable (2.1.x) | Modern features: sealed interfaces, value classes |

## Functional Requirements

### FR-1: Card Rendering Entry Point
The library SHALL provide two public entry points:
- `CometChatCardView` — a `FrameLayout` subclass for traditional View usage
- `CometChatCardComposable` — a `@Composable` function for Jetpack Compose usage

Both accept Card_Schema JSON string, Theme_Mode, optional Action_Callback, optional Theme_Override, and optional LogLevel. Both return/expose a rendered view and Container_Style output.

### FR-2: Card Schema Parsing
The library SHALL parse Card_Schema JSON into Kotlin typed models using Kotlinx Serialization with sealed class polymorphic serialization on the `type` discriminator field. The parser SHALL handle:
- All 20 element types (11 content, 5 layout, 3 interactive, 1 data display)
- All 9 action types
- ContainerStyle, ColorValue, Padding models
- Unknown fields (silently ignored)
- Round-trip serialization (parse → serialize → parse produces equivalent model)

### FR-3: Element Registry
The library SHALL use a registry pattern — a `Map<String, CometChatCardElementRenderer>` populated at initialization with all 20 built-in element renderers. Element type strings are looked up in the registry to find the corresponding renderer. Unknown types are skipped silently with a warning log.

### FR-4: Content Element Rendering (11 types)
The library SHALL render all 11 content element types:
- **text**: content, variant (10 variants), color, fontWeight, align, maxLines (ellipsis truncation), padding
- **image**: async loading via Coil, fit (cover/contain/fill), width/height (dp or percentage), borderRadius, altText accessibility, shimmer loading state, placeholder on failure
- **icon**: URL-based icon loading via Coil, size, color tinting, backgroundColor, borderRadius, padding
- **avatar**: circular image or fallback initials with backgroundColor, size, borderRadius, fontSize, fontWeight
- **badge**: compact label with text, colors, border, borderRadius, fontSize, padding
- **divider**: horizontal line with color, thickness, margin
- **spacer**: empty vertical space with height in dp
- **chip**: display-only chip with text, optional icon, colors, border, borderRadius, fontSize, padding (NOT interactive)
- **progressBar**: horizontal bar with value clamped 0-100, color, trackColor, height, label, borderRadius
- **codeBlock**: monospace text with language label, backgroundColor, textColor, padding, borderRadius, fontSize
- **markdown**: rich text conversion (bold, italic, links, unordered/ordered lists, inline code) using Html.fromHtml (View) / AnnotatedString (Compose). Links emit openUrl action.

### FR-5: Layout Element Rendering (5 types)
The library SHALL render all 5 layout element types with recursive child rendering:
- **row**: horizontal arrangement with gap, align (start/center/end/spaceBetween/spaceAround), wrap, scrollable (with peek and snap), padding, background, border
- **column**: vertical arrangement with gap, align (start/center/end/stretch), padding, background, border
- **grid**: grid layout with columns (2-4, default 2), gap, padding, background, border
- **accordion**: collapsible section with header (string or element[]), headerIcon (string header only), body, expandedByDefault, toggle animation, border, padding
- **tabs**: tabbed container with tab labels, defaultActiveTab, tabAlign, content switching without full re-render

### FR-6: Interactive Element Rendering (3 types)
The library SHALL render all 3 interactive element types:
- **button**: 4 variants (filled, outlined, text, tonal) with label, action, backgroundColor, textColor, borderColor, borderWidth, borderRadius, padding, fontSize, icon (URL), iconPosition, size, fullWidth, disabled
- **iconButton**: icon-only button with action, size, backgroundColor, color, borderRadius
- **link**: inline tappable text with action, color, underline, fontSize

### FR-7: Data Display Element Rendering (1 type)
- **table**: column headers (string[]), data rows (string[][]), stripedRows, headerBackgroundColor, border, cellPadding, fontSize, stripedRowColor, borderColor

### FR-8: Action Emission
The library SHALL emit all 9 action types to the Action_Callback without executing them:
- openUrl, copyToClipboard, downloadFile, apiCall, chatWithUser, chatWithGroup, sendMessage, initiateCall, customCallback
- Each emission includes: action object, elementId, full cardJson
- No-callback = silent no-op
- Invalid action (missing required fields) = no-op
- Disabled element = no-op

### FR-9: Theme Resolution
- ColorValue objects resolved based on effective theme mode (light → .light, dark → .dark)
- Plain hex strings used as-is regardless of theme mode
- Precedence: JSON value > Theme_Override > Default_Theme
- Auto mode: observe system theme via `Configuration.uiMode` (View) / `isSystemInDarkTheme()` (Compose), re-render on change
- Manual mode (light/dark): ignore system theme changes
- Theme-aware URLs: same pattern as colors for image/icon URLs

### FR-10: Default Theme
The library SHALL maintain a Default_Theme with fallback values for both light and dark modes:
- Text colors, secondary text colors
- Typography scale: title=32, heading1=24, heading2=20, heading3=18, heading4=16, body=14, body1=14, body2=12, caption1=12, caption2=10
- Button colors (filled, outlined, text, tonal, disabled)
- Border, background, divider, progress bar colors
- Code block, chip, badge, avatar, link, tab, accordion, table colors
- Shimmer colors
- Platform default font (Roboto), overridable via Theme_Override fontFamily

### FR-11: Error Handling
- Malformed JSON → display fallbackText as plain text
- Missing body/version → "Unable to display this message" placeholder
- Unknown element type → skip, log warning, continue siblings
- Missing required property → skip element, log warning
- Renderer exception → skip element, log error, continue siblings
- Image load failure → show altText or placeholder icon
- Invalid hex color → fall back to Default_Theme value
- Nesting depth > 5 → skip over-nested element, log warning
- Unsupported schema version → best-effort render; if fails, show fallbackText

### FR-12: Loading State API
The library SHALL provide `setElementLoading(elementId, loading)` to:
- Replace interactive element's label/icon with a loading spinner
- Disable tap events while loading
- Restore original state when loading cleared

### FR-13: Logging
Configurable log levels: none, error, warning (default), verbose
- Backend: `android.util.Log` with tag `"CometChatCards"`
- Format: `[CometChatCards] [{LEVEL}] {message}`

### FR-14: Accessibility
- Content elements: accessibility labels from visible text/altText
- Interactive elements: marked as accessible action elements
- Accordion: announce expanded/collapsed state
- Tabs: expose as selectable tabs with labels
- Images without altText: marked as decorative
- Focus traversal: top-to-bottom, left-to-right reading order

### FR-15: Maximum Nesting Depth
Layout elements enforce max depth of 5 levels. Elements at depth > 5 are skipped. Siblings at same level continue rendering.

## Non-Functional Requirements

### NFR-1: Performance
- Initial render of card with up to 50 elements within 100ms on mid-range devices (excluding image load time)
- Async image loading with in-memory and disk caching via Coil

### NFR-2: Module Structure
- Single `:cards` library module with package `com.cometchat.cards`
- Existing `:app` module serves as demo/test app
- Library published to Maven Central as single artifact `com.cometchat:cards`
- Internal package structure provides logical separation (models, parser, theme, actions, core, renderers)

### NFR-3: Testing
- Unit tests for parser, theme resolution, all 20 renderers, action emission, error handling, logging
- Property-based tests (Kotest Property Testing) for all 28 correctness properties from design doc
- Snapshot tests via Roborazzi
- Shared test fixtures (13 Card_Schema JSON fixtures from design doc)
- Target: minimum 80% code coverage

### NFR-4: Dependencies
- Kotlinx Serialization (JSON parsing)
- Coil (image loading)
- AndroidX Core KTX, AppCompat
- Jetpack Compose (BOM)
- Kotest Property Testing (test only)
- Roborazzi (test only)

### NFR-5: Compatibility
- Min SDK: 24
- Target SDK: 36
- Kotlin: Latest stable (2.1.x)
- Both View and Compose APIs

### NFR-6: Cross-Platform Consistency
- Element type names, action type names, theme mode names, log level names identical across all platforms
- Default property values identical across all platforms (as specified in requirements.md)
- `CometChatCard` prefix on all public-facing classes
