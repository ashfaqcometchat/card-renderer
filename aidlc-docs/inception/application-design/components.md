# Components

## Module Structure

The library is a single Gradle module:

### `:cards` — Complete library
Contains all models, parsing, theme resolution, action emission, registry logic, View renderers, Compose renderers, and the public entry points (`CometChatCardView`, `CometChatCardComposable`). Published as a single artifact `com.cometchat:cards`. Internal package structure provides logical separation.

---

## Component Inventory

### 1. CardSchemaParser
- **Module**: `:cards`
- **Package**: `com.cometchat.cards.parser`
- **Purpose**: Deserialize Card_Schema JSON into typed Kotlin models using Kotlinx Serialization
- **Responsibilities**:
  - Parse JSON string into `CometChatCardSchema` model
  - Handle polymorphic deserialization of 20 element types via `SerializersModule`
  - Handle polymorphic deserialization of 9 action types
  - Ignore unknown JSON fields
  - Return parse errors gracefully (no exceptions thrown to consumer)

### 2. ElementRegistry
- **Module**: `:cards`
- **Package**: `com.cometchat.cards.core`
- **Purpose**: Map element type strings to renderer implementations
- **Responsibilities**:
  - Maintain `Map<String, CometChatCardElementRenderer>` of all 20 built-in renderers
  - Lookup renderer by element type string
  - Return null for unknown types (caller handles skip logic)

### 3. ThemeResolver
- **Module**: `:cards`
- **Package**: `com.cometchat.cards.theme`
- **Purpose**: Resolve theme-aware values (colors, URLs) based on effective theme mode
- **Responsibilities**:
  - Resolve `CometChatCardColorValue` → hex string based on light/dark mode
  - Resolve theme-aware URLs (image/icon `{light, dark}` objects)
  - Merge Default_Theme + Theme_Override into `ResolvedTheme`
  - Apply precedence: JSON value > Theme_Override > Default_Theme

### 4. DefaultTheme
- **Module**: `:cards`
- **Package**: `com.cometchat.cards.theme`
- **Purpose**: Provide fallback color and typography values for light and dark modes
- **Responsibilities**:
  - Define all color tokens (text, button, border, background, etc.) for light and dark
  - Define typography scale (title=32 through caption2=10)
  - Serve as base layer in theme precedence chain

### 5. ActionEmitter
- **Module**: `:cards`
- **Package**: `com.cometchat.cards.actions`
- **Purpose**: Validate and emit action events to the consumer's callback
- **Responsibilities**:
  - Validate required action fields before emission
  - Construct `CometChatCardActionEvent` with action, elementId, cardJson
  - Emit to `CometChatCardActionCallback` or no-op if callback is null
  - Handle disabled elements and invalid actions (no-op)

### 6. Logger
- **Module**: `:cards`
- **Package**: `com.cometchat.cards.core`
- **Purpose**: Configurable logging with level filtering
- **Responsibilities**:
  - Filter log output by configured level (none/error/warning/verbose)
  - Format: `[CometChatCards] [{LEVEL}] {message}`
  - Backend: `android.util.Log` with tag `"CometChatCards"`

### 7. RenderContext
- **Module**: `:cards`
- **Package**: `com.cometchat.cards.core`
- **Purpose**: Carry rendering state through the element tree
- **Responsibilities**:
  - Hold effective theme mode, resolved theme, action callback, card JSON, current depth
  - Provide `renderElement()` function for recursive layout rendering
  - Track and enforce max nesting depth (5 levels)

### 8. CometChatCardElementRenderer (×20)
- **Module**: `:cards` (renderers)
- **Package**: `com.cometchat.cards.renderers`
- **Purpose**: Render a single element type into both View and Composable
- **Responsibilities**:
  - Each renderer implements `renderView(Context, element, renderContext): View` and `renderComposable(element, renderContext): @Composable`
  - Apply all element properties to native views
  - Handle default values from resolved theme
  - Wire tap handlers for interactive elements

#### Content Renderers (11):
- `TextElementRenderer`
- `ImageElementRenderer`
- `IconElementRenderer`
- `AvatarElementRenderer`
- `BadgeElementRenderer`
- `DividerElementRenderer`
- `SpacerElementRenderer`
- `ChipElementRenderer`
- `ProgressBarElementRenderer`
- `CodeBlockElementRenderer`
- `MarkdownElementRenderer`

#### Layout Renderers (5):
- `RowElementRenderer`
- `ColumnElementRenderer`
- `GridElementRenderer`
- `AccordionElementRenderer`
- `TabsElementRenderer`

#### Interactive Renderers (3):
- `ButtonElementRenderer`
- `IconButtonElementRenderer`
- `LinkElementRenderer`

#### Data Display Renderers (1):
- `TableElementRenderer`

### 9. CometChatCardView (View Entry Point)
- **Module**: `:cards`
- **Package**: `com.cometchat.cards`
- **Purpose**: Traditional View API entry point (FrameLayout subclass)
- **Responsibilities**:
  - Accept card JSON, theme mode, action callback, theme override, log level
  - Orchestrate: parse → resolve theme → iterate body → render via registry
  - Observe system theme in auto mode (`Configuration.uiMode`)
  - Expose `getContainerStyle()` and `setElementLoading()`
  - Handle errors (fallbackText, placeholder)

### 10. CometChatCardComposable (Compose Entry Point)
- **Module**: `:cards`
- **Package**: `com.cometchat.cards`
- **Purpose**: Jetpack Compose API entry point (@Composable function)
- **Responsibilities**:
  - Accept card JSON, theme mode, action callback, theme override, log level via parameters
  - Orchestrate same pipeline as View entry point
  - Observe system theme via `isSystemInDarkTheme()`
  - Emit container style via `onContainerStyle` callback
  - Handle errors (fallbackText, placeholder)

### 11. LoadingStateManager
- **Module**: `:cards`
- **Package**: `com.cometchat.cards.core`
- **Purpose**: Track loading state per interactive element by ID
- **Responsibilities**:
  - Maintain `Map<String, Boolean>` of element loading states
  - Provide `setLoading(elementId, loading)` and `isLoading(elementId)` APIs
  - Notify renderers to swap label/icon with spinner when loading
