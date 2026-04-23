# Requirements Document

## Introduction

CometChatCardsRenderer is a standalone cross-platform rendering library that converts Card Schema JSON into native platform views. The library's responsibility is narrow and focused: it accepts a `card` JSON object (containing version, body, style, and fallbackText) along with a Theme_Mode parameter (light, dark, or auto) and an optional Action_Callback, and produces two outputs — a rendered native view hierarchy and a set of container style settings (background, borderRadius, borderColor, borderWidth, padding, maxWidth, maxHeight, width, height) that the consumer (e.g., UIKit) can apply as it sees fit. The library does not handle message receiving, message lifecycle, category/type sorting, or any SDK/UIKit integration concerns. Colors are primarily defined by the Card Schema JSON; the library's Default_Theme serves only as a fallback when the JSON does not specify a value. The library does not execute any actions itself — when a user taps an interactive element, the library emits the action event to the consumer via the Action_Callback. The library supports automatic system theme observation (auto mode, the default) or explicit consumer-provided theme mode (manual mode). It ships as a separate package for Android, iOS, Flutter, React, and React Native.

## Glossary

- **Card_Renderer**: The top-level entry point of the CometChatCardsRenderer library. Accepts a Card_Schema JSON object, a Theme_Mode parameter, an optional Action_Callback, and an optional Theme_Override, and returns a rendered native view plus a Container_Style output. The library does not execute any actions — it emits all action events to the consumer via the Action_Callback. The public API is platform-idiomatic (see Requirement 2).
- **Element_Renderer**: A platform-specific renderer for a single element type (e.g., "text", "image", "row"). Each Element_Renderer receives the element JSON and the resolved theme context, and returns a native view.
- **Action_Callback**: A callback/listener provided by the consumer that receives all action events when a user taps an interactive element (button, iconButton, link). The callback receives the action type, action parameters, the elementId of the tapped element, and the full card JSON. If no Action_Callback is provided, taps on interactive elements do nothing.
- **Container_Style**: The container-level styling output extracted from the Card_Schema's `style` field, containing background (ColorValue or hex string), borderRadius, borderColor, borderWidth, padding, maxWidth, maxHeight, width, and height. Returned alongside the rendered view so the consumer can apply it to a wrapper (e.g., a message bubble).
- **ColorValue**: An object with `light` and `dark` hex string fields used for theme-aware color resolution. Resolved based on the effective theme mode (either from system observation in auto mode, or from the consumer-provided value in manual mode).
- **Theme_Mode**: A parameter that can be "auto" (default), "light", or "dark". In auto mode, the library observes the system theme and automatically resolves ColorValue objects, re-resolving colors and updating the rendered view when the system theme changes. In manual mode (light or dark), the library uses the consumer-provided value and ignores system theme changes.
- **Default_Theme**: The library's built-in fallback theme containing default text colors, typography scale, button colors, border colors, background colors, divider color, progress bar colors, and other visual defaults for both light and dark modes. The Default_Theme is only used as a fallback when the Card_Schema JSON does not specify an explicit value for a given property. The library does not impose its own color scheme — it renders what the JSON says.
- **Theme_Override**: An optional configuration object the consumer can pass at initialization or render time to override any values in the Default_Theme.
- **Card_Schema**: The JSON structure containing version, body (array of elements), style (Container_Style), and fallbackText.
- **Padding**: A value that can be either a single number (applied uniformly to all four sides) or an object with `top`, `right`, `bottom`, and `left` fields (each optional, defaulting to 0). All values are in density-independent pixels.
- **Layout_Element**: An element (row, column, grid, accordion, tabs) whose `items` or `body`/`tabs` property contains child elements rendered recursively.
- **Content_Element**: A leaf element (text, image, icon, avatar, badge, divider, spacer, chip, progressBar, codeBlock, markdown) that renders content without children.
- **Interactive_Element**: An element (button, iconButton, link) that carries an `action` property and responds to user taps.

## Design Decisions

1. Icons are always provided as image URLs in the `name` field. The library does not ship with or reference any platform icon library (SF Symbols, Material Icons, etc.).
2. RTL (right-to-left) layout and localization are not supported in this version. All layouts render left-to-right.
3. Markdown rendering supports a defined subset: bold, italic, links, unordered lists, ordered lists, and inline code. Full CommonMark is not supported.
4. Image loading state uses a shimmer/skeleton animation. Image failure state shows a generic placeholder icon or altText if available.
5. Row and column elements support only the `align` property for alignment. The `crossAlign` property is not supported in this version.
6. The library does not execute any actions. All action events are emitted to the consumer's Action_Callback.
7. The library's Default_Theme is a fallback mechanism only. All colors and styling are primarily defined by the Card Schema JSON.
8. Custom element renderer registration is not supported in this version. The library renders the 20 built-in element types only.
9. The library uses best-effort rendering for unrecognized schema versions. Unknown element types are skipped, and if rendering fails entirely, fallbackText is displayed.
10. Chips are display-only elements and do not support an `action` property. They are not interactive.
11. Image width and height support both numeric values (dp) and percentage strings (e.g., "50%"). Percentage values are relative to the parent container.
12. Table columns use a simple string array format (header labels only). Table rows use a string[][] format (array of string arrays). Object-based column definitions and record-based rows are not supported in this version.
13. The library does not validate Card Schema JSON payload size. The 65KB payload limit is enforced server-side by the chat-api.
14. Links within markdown elements emit an `openUrl` action to the Action_Callback when tapped, consistent with the library's pure-emission action model.
15. On Android, the library supports both traditional View (android.view.View) and Jetpack Compose (@Composable) APIs. On iOS, the library supports both UIKit (UIView) and SwiftUI (View) APIs. The core rendering logic is shared; the framework-specific layers are thin wrappers.
16. On Web, the core library is a framework-agnostic vanilla TypeScript package (`@cometchat/cards`) that returns HTMLElement. Framework-specific wrappers are provided as separate packages for React (`@cometchat/cards-react`), Angular (`@cometchat/cards-angular`), and Vue (`@cometchat/cards-vue`). The wrappers are thin layers over the vanilla core.
17. The library uses the platform's default system font (San Francisco on iOS, Roboto on Android, system font on Web, default on Flutter). The JSON schema does not specify fontFamily. Developers can override the default font family globally via Theme_Override.
18. Form/input elements (text inputs, checkboxes, dropdowns, radio buttons, toggles) are intentionally out of scope for v1. The library renders display-only and interactive (action-emitting) elements only.
19. Image and icon URLs support theme-aware values using the same ColorValue pattern as colors: either a plain URL string or an object `{ light: "url1", dark: "url2" }` resolved based on the effective theme mode.
20. The library supports a mechanism for the consumer to programmatically set a specific interactive element to a "loading" state by element ID. The consumer controls when to show and clear the loading state.

## Requirements

### Requirement 1: Card Rendering Entry Point

**User Story:** As a developer consuming the CometChatCardsRenderer library, I want to pass a Card Schema JSON object, a theme mode, and an optional action callback, and receive a rendered native view plus container style settings, so that I can embed rendered cards in my app's UI.

#### Acceptance Criteria

1. WHEN a valid Card_Schema JSON and a Theme_Mode parameter are provided, THE Card_Renderer SHALL parse the `body` array and render it as a vertical stack of elements, returning the rendered native view.
2. WHEN the Card_Schema contains a `style` property, THE Card_Renderer SHALL extract the Container_Style (background, borderRadius, borderColor, borderWidth, padding, maxWidth, maxHeight, width, height) and return it as a separate output alongside the rendered view.
3. WHEN the Container_Style `background` field is a ColorValue object, THE Card_Renderer SHALL resolve the hex color using the `light` value when the effective theme mode is light, and the `dark` value when the effective theme mode is dark.
4. WHEN the Container_Style `background` field is a plain hex string, THE Card_Renderer SHALL use that value directly regardless of Theme_Mode.
5. IF the Card_Schema is missing the `body` field or the `version` field, THEN THE Card_Renderer SHALL display a placeholder view with the text "Unable to display this message".
6. IF the Card_Schema `body` array is empty, THEN THE Card_Renderer SHALL render an empty container view and return the Container_Style as normal.
7. WHEN the Container_Style contains `maxWidth` or `maxHeight` fields, THE Card_Renderer SHALL include those values in the returned Container_Style output for the consumer to apply sizing constraints.
8. WHEN the Container_Style contains `width` or `height` fields (number or "auto"), THE Card_Renderer SHALL include those values in the returned Container_Style output for the consumer to apply explicit sizing.

### Requirement 2: Platform-Idiomatic Public API

**User Story:** As a developer on each supported platform, I want the library's public API to follow my platform's conventions and idioms, so that integrating the card renderer feels natural and consistent with other libraries I use.

#### Acceptance Criteria

1. WHERE the platform is Android (Kotlin), THE Card_Renderer SHALL expose both a traditional View API (returning android.view.View) and a Jetpack Compose API (returning a @Composable function) that accept Card_Schema JSON, Theme_Mode, optional Action_Callback, and optional Theme_Override.
2. WHERE the platform is iOS (Swift), THE Card_Renderer SHALL expose both a UIKit API (returning UIView) and a SwiftUI API (returning a SwiftUI View) that accept Card_Schema JSON, Theme_Mode, optional Action_Callback, and optional Theme_Override.
3. WHERE the platform is Flutter (Dart), THE Card_Renderer SHALL expose a Widget with named parameters that accepts Card_Schema JSON, Theme_Mode, optional Action_Callback, and optional Theme_Override.
4. WHERE the platform is Web, THE Card_Renderer SHALL expose a framework-agnostic vanilla TypeScript core package (`@cometchat/cards`) that accepts Card_Schema JSON, Theme_Mode, optional Action_Callback, and optional Theme_Override, and returns an HTMLElement plus Container_Style.
5. WHERE the platform is Web with React, THE library SHALL provide a React wrapper package (`@cometchat/cards-react`) that exposes a React component with a props interface wrapping the vanilla core.
6. WHERE the platform is Web with Angular, THE library SHALL provide an Angular wrapper package (`@cometchat/cards-angular`) that exposes an Angular component/directive wrapping the vanilla core.
7. WHERE the platform is Web with Vue, THE library SHALL provide a Vue wrapper package (`@cometchat/cards-vue`) that exposes a Vue component wrapping the vanilla core.
8. WHERE the platform is React Native (TypeScript), THE Card_Renderer SHALL expose a React Native component with a props interface that accepts Card_Schema JSON, Theme_Mode, optional Action_Callback, and optional Theme_Override.


### Requirement 3: Content Element Rendering

**User Story:** As an end user, I want rendered cards to display rich content elements (text, images, icons, avatars, badges, dividers, spacers, chips, progress bars, code blocks, and markdown), so that I can view structured information within a card.

#### Element Reference — Content Elements (11 types)

| Element | Properties |
|---------|-----------|
| text | id, content, variant, color, fontWeight, align, maxLines, padding |
| image | id, url (string or {light, dark}), altText, fit, width (number or percentage string), height (number or percentage string), borderRadius, padding |
| icon | id, name (URL string or {light, dark}), size, color, backgroundColor, borderRadius, padding |
| avatar | id, imageUrl, fallbackInitials, size, borderRadius, backgroundColor, fontSize, fontWeight |
| badge | id, text, color, backgroundColor, borderColor, borderWidth, borderRadius, fontSize, padding |
| divider | id, color, thickness, margin |
| spacer | id, height |
| chip | id, text, color, icon, backgroundColor, borderColor, borderWidth, borderRadius, fontSize, padding |
| progressBar | id, value, color, trackColor, height, label, borderRadius, labelFontSize, labelColor |
| codeBlock | id, content, language, backgroundColor, textColor, padding, borderRadius, fontSize, languageLabelFontSize, languageLabelColor |
| markdown | id, content, baseFontSize, linkColor, color, lineHeight |

#### Acceptance Criteria

1. WHEN a `text` element is present in the body, THE Element_Renderer SHALL render the content string with the specified variant (title, heading1, heading2, heading3, heading4, body, body1, body2, caption1, caption2), color, fontWeight, align, maxLines, and padding.
2. WHEN a `text` element specifies maxLines, THE Element_Renderer SHALL truncate the text with an ellipsis after the specified number of lines.
3. WHEN an `image` element is present, THE Element_Renderer SHALL load the image from the url asynchronously and apply the specified fit (cover, contain, fill), width (number in dp or percentage string relative to parent container), height (number in dp or percentage string relative to parent container), borderRadius, and padding.
4. WHEN an `image` element specifies altText, THE Element_Renderer SHALL set the altText as the accessibility label for the image view.
5. WHEN an `icon` element is present, THE Element_Renderer SHALL load the icon image from the `name` field URL and apply size, color, backgroundColor, borderRadius, and padding. IF the icon fails to load, THE Element_Renderer SHALL display a generic placeholder icon.
6. WHEN an `avatar` element is present with an imageUrl, THE Element_Renderer SHALL render a circular (or borderRadius-specified) image of the given size.
7. WHEN an `avatar` element has no imageUrl, THE Element_Renderer SHALL render a circle with the backgroundColor and display the fallbackInitials text centered inside, using fontSize and fontWeight if specified.
8. WHEN a `badge` element is present, THE Element_Renderer SHALL render a compact label with the text, backgroundColor, borderColor, borderWidth, borderRadius, fontSize, color, and padding properties.
9. WHEN a `divider` element is present, THE Element_Renderer SHALL render a horizontal line with the specified color, thickness, and margin.
10. WHEN a `spacer` element is present, THE Element_Renderer SHALL render empty vertical space of the specified height in density-independent pixels.
11. WHEN a `chip` element is present, THE Element_Renderer SHALL render a compact chip with text, optional icon, backgroundColor, color, borderColor, borderWidth, borderRadius, fontSize, and padding.
12. WHEN a `progressBar` element is present, THE Element_Renderer SHALL render a horizontal progress bar with value clamped to 0-100, applying color, trackColor, height, label, borderRadius, labelFontSize, and labelColor.
13. WHEN a `codeBlock` element is present, THE Element_Renderer SHALL render the content in a monospace font with the specified language label, backgroundColor, textColor, padding, borderRadius, fontSize, languageLabelFontSize, and languageLabelColor.
14. WHEN a `markdown` element is present, THE Element_Renderer SHALL convert the markdown content to native rich text supporting the following subset: bold, italic, links, unordered lists, ordered lists, and inline code. Apply baseFontSize, linkColor, color, and lineHeight.
15. WHEN a `markdown` element contains links in `[text](url)` format, THE Element_Renderer SHALL render the links as tappable text.
16. WHEN a user taps a link within a `markdown` element, THE Card_Renderer SHALL emit an `openUrl` action to the Action_Callback with the link's URL, the markdown element's id as the elementId, and the full card JSON.

### Requirement 4: Layout Element Rendering

**User Story:** As a developer constructing card schemas, I want layout elements (row, column, grid, accordion, tabs) to arrange child elements in structured layouts, so that I can build complex card UIs with nested compositions.

#### Element Reference — Layout Elements (5 types)

| Element | Properties |
|---------|-----------|
| row | id, items, gap, align, wrap, scrollable, peek, snap, padding, backgroundColor, borderRadius, borderColor, borderWidth |
| column | id, items, gap, align, padding, backgroundColor, borderRadius, borderColor, borderWidth |
| grid | id, items, columns, gap, padding, backgroundColor, borderRadius, borderColor, borderWidth |
| accordion | id, header (string or element[]), headerIcon, body, expandedByDefault, border, padding, fontSize, fontWeight, borderRadius |
| tabs | id, tabs[{label, content}], defaultActiveTab, tabAlign, tabPadding, contentPadding, fontSize |

#### Acceptance Criteria

1. WHEN a `row` element is present, THE Element_Renderer SHALL arrange its items horizontally with the specified gap, align, wrap, padding, backgroundColor, borderRadius, borderColor, and borderWidth.
2. WHEN a `row` element has scrollable set to true, THE Element_Renderer SHALL wrap the row in a horizontal scroll container with optional peek (visible portion of next item in pixels) and snap behavior.
3. WHEN a `column` element is present, THE Element_Renderer SHALL arrange its items vertically with the specified gap, align, padding, backgroundColor, borderRadius, borderColor, and borderWidth.
4. WHEN a `grid` element is present, THE Element_Renderer SHALL arrange its items in a grid with the specified number of columns (default 2, range 2-4), gap, padding, backgroundColor, borderRadius, borderColor, and borderWidth.
5. WHEN an `accordion` element is present, THE Element_Renderer SHALL render a collapsible section with the header (either a plain text string or an array of elements rendered as a horizontal row), optional headerIcon (only applicable when header is a string), and the body elements hidden by default unless expandedByDefault is true, applying border, padding, fontSize, fontWeight, and borderRadius.
6. WHEN a user taps the accordion header, THE Element_Renderer SHALL toggle the visibility of the accordion body elements with a smooth animation.
7. WHEN an accordion's header is an array of elements, THE Element_Renderer SHALL render the header elements in a horizontal arrangement and the headerIcon property SHALL be ignored.
8. WHEN a `tabs` element is present, THE Element_Renderer SHALL render a tabbed container with tab labels, displaying the content of the defaultActiveTab (default 0) initially, applying tabAlign, tabPadding, contentPadding, and fontSize.
9. WHEN a user taps a tab label, THE Element_Renderer SHALL switch the visible content to the selected tab's content array without re-rendering the entire card.
10. THE Card_Renderer SHALL render Layout_Element children recursively, supporting nesting of layout elements within other layout elements.
11. THE Card_Renderer SHALL enforce a maximum nesting depth of 5 levels for Layout_Elements, and WHEN the depth is exceeded, THE Card_Renderer SHALL skip the over-nested element and continue rendering siblings.


### Requirement 5: Interactive Element Rendering

**User Story:** As an end user, I want to interact with buttons, icon buttons, and links inside rendered cards, so that I can perform actions like opening URLs, copying text, or triggering callbacks directly from the card.

#### Element Reference — Interactive Elements (3 types)

| Element | Properties |
|---------|-----------|
| button | id, label, action, variant, backgroundColor, textColor, borderColor, borderWidth, borderRadius, padding, fontSize, icon, iconPosition, size, fullWidth, disabled |
| iconButton | id, icon, action, size, backgroundColor, color, borderRadius |
| link | id, text, action, color, underline, fontSize |

#### Acceptance Criteria

1. WHEN a `button` element is present, THE Element_Renderer SHALL render a tappable button with the label, variant (filled, outlined, text, tonal), backgroundColor, textColor, borderColor, borderWidth, borderRadius, padding, fontSize, icon, iconPosition (left or right), size, fullWidth, and disabled properties.
2. WHEN a `button` element has variant "filled", THE Element_Renderer SHALL render a solid background using backgroundColor with text in textColor.
3. WHEN a `button` element has variant "outlined", THE Element_Renderer SHALL render a transparent background with a border using borderColor (or backgroundColor as border color if borderColor is not specified) and text in textColor (or backgroundColor as text color if textColor is not specified).
4. WHEN a `button` element has variant "text", THE Element_Renderer SHALL render a transparent background with no border and text in backgroundColor (used as the text color).
5. WHEN a `button` element has variant "tonal", THE Element_Renderer SHALL render a translucent background (backgroundColor at 15% opacity) with text in backgroundColor.
6. WHEN an `iconButton` element is present, THE Element_Renderer SHALL render a tappable icon-only button with the icon, size, backgroundColor, color, and borderRadius properties.
7. WHEN a `link` element is present, THE Element_Renderer SHALL render inline tappable text with the text, color, underline, and fontSize properties.
8. WHEN a user taps an Interactive_Element that has an action property, THE Card_Renderer SHALL emit the action event to the Action_Callback with the action type, action parameters, the elementId of the tapped element, and the full card JSON.
9. IF an Interactive_Element has a null or missing action property, THEN THE Element_Renderer SHALL render the element in a non-interactive (disabled) visual state.
10. WHEN a `button` element has disabled set to true, THE Element_Renderer SHALL render the button in a visually disabled state and ignore tap events.
11. (GOOD TO HAVE) THE Card_Renderer SHALL provide a public API for the consumer to set a specific Interactive_Element to a "loading" state by element ID, replacing the element's label/icon with a loading spinner and disabling tap events.
12. (GOOD TO HAVE) THE Card_Renderer SHALL provide a public API for the consumer to clear the "loading" state on a specific Interactive_Element by element ID, restoring the element's original label/icon and re-enabling tap events.

### Requirement 6: Action Emission

**User Story:** As a developer, I want all action events from interactive elements to be emitted to my Action_Callback, so that my application has full control over how every action is handled.

#### Action Reference

All actions are emitted to the consumer via the Action_Callback. The library does not execute any actions itself.

| Action | Parameters | Behavior |
|--------|-----------|----------|
| openUrl | url, openIn (optional: "browser" or "webview") | Emitted to consumer |
| copyToClipboard | value | Emitted to consumer |
| downloadFile | url, filename (optional) | Emitted to consumer |
| apiCall | url, method (default POST), headers, body | Emitted to consumer |
| chatWithUser | uid | Emitted to consumer |
| chatWithGroup | guid | Emitted to consumer |
| sendMessage | text, receiverUid (optional), receiverGuid (optional) | Emitted to consumer |
| initiateCall | callType (audio/video), uid or guid | Emitted to consumer |
| customCallback | callbackId, payload (optional) | Emitted to consumer |

#### Acceptance Criteria

1. WHEN a user taps an Interactive_Element (button, iconButton, link) with an action property, THE Card_Renderer SHALL emit the action event to the Action_Callback with the action type, all action parameters, the elementId of the tapped element, and the full card JSON.
2. WHEN an `openUrl` action is triggered, THE Card_Renderer SHALL emit the action event to the Action_Callback with the url and optional openIn parameter.
3. WHEN a `copyToClipboard` action is triggered, THE Card_Renderer SHALL emit the action event to the Action_Callback with the value string.
4. WHEN a `downloadFile` action is triggered, THE Card_Renderer SHALL emit the action event to the Action_Callback with the url and optional filename.
5. WHEN an `apiCall` action is triggered, THE Card_Renderer SHALL emit the action event to the Action_Callback with the url, method, headers, and body.
6. WHEN a `chatWithUser` action is triggered, THE Card_Renderer SHALL emit the action event to the Action_Callback with the uid.
7. WHEN a `chatWithGroup` action is triggered, THE Card_Renderer SHALL emit the action event to the Action_Callback with the guid.
8. WHEN a `sendMessage` action is triggered, THE Card_Renderer SHALL emit the action event to the Action_Callback with the text, optional receiverUid, and optional receiverGuid.
9. WHEN an `initiateCall` action is triggered, THE Card_Renderer SHALL emit the action event to the Action_Callback with the callType (audio or video) and the uid or guid.
10. WHEN a `customCallback` action is triggered, THE Card_Renderer SHALL emit the action event to the Action_Callback with the callbackId, optional payload, the elementId of the tapped element, and the full card JSON.
11. IF no Action_Callback is provided and a user taps an Interactive_Element, THEN THE Card_Renderer SHALL take no action.
12. IF an action's required field is missing or invalid (e.g., openUrl with empty url), THEN THE Card_Renderer SHALL take no action for that tap.

### Requirement 7: Data Display Element Rendering

**User Story:** As an end user, I want to view tabular data inside rendered cards, so that I can read structured information like order details, comparison tables, or schedules.

#### Element Reference — Data Display (1 type)

| Element | Properties |
|---------|-----------|
| table | id, columns (string[]), rows (string[][]), stripedRows, headerBackgroundColor, border, cellPadding, fontSize, stripedRowColor, borderColor |

#### Acceptance Criteria

1. WHEN a `table` element is present, THE Element_Renderer SHALL render a table with column headers from the `columns` string array and data rows from the `rows` array (each row is a string array of cell values).
2. WHEN the table's stripedRows property is true, THE Element_Renderer SHALL apply alternating background colors to table rows using the stripedRowColor if provided, or a platform-appropriate default.
3. WHEN the table's border property is true, THE Element_Renderer SHALL render visible borders around cells using borderColor if provided.
4. WHEN the table specifies headerBackgroundColor, THE Element_Renderer SHALL apply that color to the header row background.
5. THE Element_Renderer SHALL apply cellPadding and fontSize to all table cells.

### Requirement 8: Theme Support and Default Theme

**User Story:** As a developer, I want the library to render colors as defined in the Card Schema JSON, fall back to sensible defaults when the JSON omits values, and support automatic system theme switching, so that cards look correct in both light and dark modes with minimal configuration.

#### Acceptance Criteria

1. WHEN a color property in any element or Container_Style is a ColorValue object with light and dark fields, THE Card_Renderer SHALL resolve the color using the light value when the effective theme mode is light, and the dark value when the effective theme mode is dark.
2. WHEN a color property is a plain hex string, THE Card_Renderer SHALL use that value in both light and dark theme modes.
3. WHEN the Card_Schema JSON specifies an explicit value for a color, font size, or other visual property, THE Card_Renderer SHALL use the JSON-specified value as the primary styling source.
4. WHEN the Card_Schema JSON does not specify an explicit value for a color, font size, or other visual property, THE Card_Renderer SHALL apply the corresponding value from the Default_Theme based on the effective theme mode as a fallback.
5. THE Card_Renderer SHALL maintain a Default_Theme containing fallback text colors (for light and dark modes), a typography scale matching variant sizes (title=32, heading1=24, heading2=20, heading3=18, heading4=16, body=14, body1=14, body2=12, caption1=12, caption2=10), fallback button colors, border colors, background colors, divider color, and progress bar colors.
6. WHEN the consumer provides a Theme_Override at initialization or render time, THE Card_Renderer SHALL merge the override values with the Default_Theme, with override values taking precedence over Default_Theme values, but JSON-specified values still taking precedence over both.
7. WHEN Theme_Mode is set to "auto" (the default), THE Card_Renderer SHALL observe the system theme setting and automatically resolve all ColorValue objects using the current system theme (light or dark).
8. WHILE Theme_Mode is "auto" and the system theme changes, THE Card_Renderer SHALL re-resolve all ColorValue objects and Default_Theme color selections using the new system theme and update the rendered view.
9. WHEN Theme_Mode is set to "light" or "dark" explicitly, THE Card_Renderer SHALL use the consumer-provided value to resolve all ColorValue objects and ignore system theme changes.
10. THE Card_Renderer SHALL accept Theme_Mode as a parameter at render time with a default value of "auto".
11. THE Card_Renderer SHALL define standard predefined default values for all optional element properties and these defaults SHALL be identical across all 5 platforms (Android, iOS, Flutter, React, React Native).
12. THE Card_Renderer SHALL use the platform's default system font as the base font family (San Francisco on iOS/macOS, Roboto on Android, system-ui on Web, default on Flutter).
13. WHEN the consumer provides a fontFamily value in the Theme_Override, THE Card_Renderer SHALL use the overridden font family for all text rendering across the entire card.
14. WHEN an image or icon element's URL field is a theme-aware object with `light` and `dark` URL strings, THE Card_Renderer SHALL resolve the URL using the `light` value when the effective theme mode is light, and the `dark` value when the effective theme mode is dark.
15. WHEN an image or icon element's URL field is a plain string, THE Card_Renderer SHALL use that URL regardless of theme mode.

#### Default Values Reference

| Element | Default Property Values |
|---------|------------------------|
| image | height = 200dp, fit = "cover", borderRadius = 0 |
| text | variant = "body" (14sp/pt), color = from Default_Theme, align = "left", maxLines = unlimited |
| button | variant = "filled", size = "medium" (height 44dp), borderRadius = 8, fontSize = 15 |
| row | gap = 8, align = "start" |
| column | gap = 8, align = "start" |
| grid | columns = 2, gap = 8 |
| divider | thickness = 1, color = from Default_Theme |
| spacer | height = 8 |
| accordion | expandedByDefault = false |
| tabs | defaultActiveTab = 0, tabAlign = "start" |
| progressBar | height = 4, color = from Default_Theme |
| avatar | size = 44, shape = circle (borderRadius = size/2) |
| badge | fontSize = 12, borderRadius = 10 |
| chip | borderRadius = 14, fontSize = 13 |
| codeBlock | fontSize = 13, borderRadius = 4 |
| markdown | baseFontSize = 16 |
| icon | size = 24 |
| iconButton | size = 24 |
| table | cellPadding = 8, fontSize = 14 |

### Requirement 9: Unknown and Malformed Element Handling

**User Story:** As a developer, I want the card renderer to gracefully handle unknown or malformed elements, so that a single bad element does not break the entire card.

#### Acceptance Criteria

1. WHEN the body array contains an element with an unrecognized type string, THE Card_Renderer SHALL skip that element silently and continue rendering the remaining elements.
2. WHEN a Layout_Element's items array contains an element with an unrecognized type, THE Card_Renderer SHALL skip that child element and render the remaining children.
3. IF an element's required properties are missing or have invalid types (e.g., text element with no content), THEN THE Card_Renderer SHALL skip that element and log a warning.
4. IF the Card_Schema JSON fails to parse entirely, THEN THE Card_Renderer SHALL display the fallbackText as a plain text view.
5. IF both the Card_Schema and fallbackText are missing or invalid, THEN THE Card_Renderer SHALL display a placeholder view with the text "Unable to display this message".

### Requirement 10: Accessibility

**User Story:** As an end user using assistive technology, I want rendered cards to be accessible, so that I can navigate and interact with card content using screen readers and keyboard navigation.

#### Acceptance Criteria

1. THE Card_Renderer SHALL set appropriate accessibility labels on all Content_Elements, using the element's visible text content or altText as the label.
2. THE Card_Renderer SHALL mark all Interactive_Elements (button, iconButton, link) as accessible action elements with descriptive labels derived from the element's label or text property.
3. WHEN an accordion is collapsed, THE Card_Renderer SHALL announce the collapsed state to assistive technology, and WHEN expanded, THE Card_Renderer SHALL announce the expanded state.
4. WHEN a tabs element is present, THE Card_Renderer SHALL expose each tab as a selectable tab element with its label, and announce the currently selected tab to assistive technology.
5. THE Card_Renderer SHALL ensure that all image elements without altText are marked as decorative (hidden from assistive technology).
6. THE Card_Renderer SHALL support platform-native focus traversal order, following the top-to-bottom, left-to-right reading order of the card body.

### Requirement 11: Performance

**User Story:** As a developer, I want card rendering to be performant even with complex cards containing many elements, images, and deep nesting, so that the rendered card views are smooth and responsive.

#### Acceptance Criteria

1. WHEN an `image` element is rendered, THE Element_Renderer SHALL load the image asynchronously using the platform's standard image loading library (Coil on Android, SDWebImage/Kingfisher on iOS, cached_network_image on Flutter, native Image on React/React Native) with in-memory and disk caching.
2. WHILE an image is being loaded asynchronously, THE Element_Renderer SHALL display a shimmer/skeleton animation as the loading placeholder.
3. IF an image fails to load (network error, invalid URL, timeout), THEN THE Element_Renderer SHALL display a placeholder view with the altText if available, or a generic image placeholder icon.
4. THE Card_Renderer SHALL complete the initial render of a card with up to 50 elements within 100 milliseconds on mid-range devices, excluding image load time.

### Requirement 12: Card Schema Parsing

**User Story:** As a developer, I want the card schema JSON to be parsed reliably into typed models on each platform, so that rendering is type-safe and predictable.

#### Acceptance Criteria

1. THE Card_Renderer SHALL parse the Card_Schema JSON into platform-specific typed models (sealed classes on Kotlin, protocols/structs on Swift, union types on TypeScript, sealed classes on Dart) for all 20 element types and 9 action types.
2. THE Card_Renderer SHALL serialize typed card models back into valid Card_Schema JSON that matches the original structure.
3. FOR ALL valid Card_Schema JSON objects, parsing then serializing then parsing SHALL produce an equivalent typed model (round-trip property).
4. WHEN the Card_Schema JSON contains fields not defined in the current schema version, THE Card_Renderer SHALL ignore unknown fields without error during parsing.
5. THE Card_Renderer SHALL declare the set of schema versions it supports, starting with version "1.0".
6. WHEN the Card_Schema contains a version string not in the supported set, THE Card_Renderer SHALL attempt a best-effort render by parsing and rendering the body array using the known element types.
7. IF best-effort rendering of an unrecognized schema version fails entirely (e.g., the body cannot be parsed), THEN THE Card_Renderer SHALL display the fallbackText as a plain text view.

### Requirement 13: Logging

**User Story:** As a developer, I want to configure the library's log level for debugging, so that I can see detailed rendering information during development and suppress logs in production.

#### Acceptance Criteria

1. THE Card_Renderer SHALL support configurable log levels: none, error, warning, verbose.
2. WHEN log level is set to "none", THE Card_Renderer SHALL produce no log output.
3. WHEN log level is set to "error", THE Card_Renderer SHALL log only errors (e.g., JSON parse failures, unrecoverable rendering exceptions, complete card render failures).
4. WHEN log level is set to "warning", THE Card_Renderer SHALL log errors and warnings (e.g., skipped elements, missing required properties, failed image loads, invalid actions).
5. WHEN log level is set to "verbose", THE Card_Renderer SHALL log errors, warnings, and detailed rendering information including element types being rendered, theme resolution, action emissions, and timing.
6. THE Card_Renderer SHALL default to "warning" log level.
7. THE Card_Renderer SHALL accept the log level as a configuration parameter at initialization time.

### Requirement 14: Cross-Platform Architecture and Naming Conventions

**User Story:** As a developer working across multiple CometChat platforms, I want the library's architecture, class names, and nomenclature to follow the same core principles on every platform while respecting platform-specific naming conventions, so that the experience feels consistent and familiar regardless of which platform I'm working on.

#### Acceptance Criteria

1. THE library SHALL use a consistent architectural pattern across all 5 platforms: a top-level Card_Renderer entry point, an internal Element_Renderer per element type, a theme resolution layer, and an action emission layer.
2. THE library SHALL use the prefix "CometChatCard" for all public-facing class/struct/component names across all platforms (e.g., CometChatCardView, CometChatCardStyle, CometChatCardAction, CometChatCardTheme).
3. WHERE the platform is Android (Kotlin), THE library SHALL follow Kotlin naming conventions: PascalCase for classes (CometChatCardView for View, CometChatCardComposable for Compose), camelCase for functions and properties, sealed classes for element and action types, and package name `com.cometchat.cards`.
4. WHERE the platform is iOS (Swift), THE library SHALL follow Swift naming conventions: PascalCase for types (CometChatCardView for UIKit, CometChatCardSwiftUIView for SwiftUI), camelCase for functions and properties, protocols for renderer interfaces, and module name `CometChatCards`.
5. WHERE the platform is Flutter (Dart), THE library SHALL follow Dart naming conventions: PascalCase for classes (CometChatCardView), camelCase for functions and properties, snake_case for file names, and package name `cometchat_cards`.
6. WHERE the platform is Web (TypeScript), THE library SHALL follow TypeScript naming conventions: PascalCase for types and classes (CometChatCardView), camelCase for functions and properties. The vanilla core package SHALL be `@cometchat/cards`, with framework wrappers `@cometchat/cards-react`, `@cometchat/cards-angular`, and `@cometchat/cards-vue`.
7. WHERE the platform is React Native (TypeScript), THE library SHALL follow React Native/TypeScript naming conventions: PascalCase for components and types (CometChatCardView), camelCase for functions and props, and package name `@cometchat/cards-react-native`.
8. THE library SHALL use consistent naming for equivalent concepts across all platforms: the same element type names, action type names, theme mode names, log level names, and configuration parameter names.
9. THE library SHALL organize source code into consistent logical modules across all platforms: models (element types, action types, card schema), renderers (one per element type), theme (color resolution, default theme, theme override), actions (action emission), and core (entry point, logging, configuration).
10. THE library's internal architecture SHALL follow the registry pattern: a map of element type strings to their corresponding renderer implementations, with the registry populated at initialization with all 20 built-in element renderers.

### Requirement 15: Testing Strategy

**User Story:** As a library maintainer, I want a comprehensive testing strategy covering unit tests, rendering tests, and cross-platform consistency tests, so that the library is reliable and regressions are caught early.

#### Acceptance Criteria

1. THE library SHALL have unit tests for the Card Schema parser covering all 20 element types, all 9 action types, round-trip serialization, unknown field handling, and malformed JSON handling.
2. THE library SHALL have unit tests for the theme resolution layer covering ColorValue resolution in light mode, dark mode, and auto mode, Default_Theme fallback behavior, and Theme_Override merging.
3. THE library SHALL have unit tests for each of the 20 Element_Renderers verifying that the correct native view type is produced and that all element properties are applied correctly.
4. THE library SHALL have unit tests for the action emission layer verifying that all 9 action types emit the correct action type, parameters, elementId, and card JSON to the Action_Callback.
5. THE library SHALL have unit tests for error handling covering unknown element types (skipped silently), missing required properties (skipped with warning), empty body arrays, missing version/body fields, and complete parse failures falling back to fallbackText.
6. THE library SHALL have unit tests for the logging module verifying that each log level (none, error, warning, verbose) produces the expected output.
7. THE library SHALL have rendering tests (snapshot or visual regression tests where platform-supported) for each of the 20 element types verifying visual output matches expected appearance.
8. THE library SHALL have integration tests that render a complete Card_Schema JSON with mixed element types, nested layouts, and interactive elements, verifying the full rendering pipeline produces a valid view hierarchy.
9. THE library SHALL have tests for edge cases: maximum nesting depth enforcement, percentage-based image sizing, accordion expand/collapse state, tab switching, scrollable row behavior, and button variant visual states.
10. THE library SHALL maintain a shared set of test Card_Schema JSON fixtures used across all platforms to verify cross-platform rendering consistency.
11. THE library SHALL target a minimum of 80% code coverage across unit and rendering tests on each platform.

### Common Style Properties Reference

The following style conventions apply across all element types:

| Property | Type | Description |
|----------|------|-------------|
| padding | number or {top, right, bottom, left} | Spacing inside the element boundary. A single number applies uniformly to all four sides. An object allows per-side values; each field is optional and defaults to 0. All values are in density-independent pixels. |
| Colors | hex string "#FFFFFF" or ColorValue {light: "#FFFFFF", dark: "#1A1A1A"} | Theme-aware color values resolved by Theme_Mode |
| Image width/height | number (dp) or percentage string (e.g., "50%") | Numeric values are density-independent pixels. Percentage strings are relative to the parent container's width or height. |
