# Unit 1: Models & Parser — Business Logic Model

## JSON Parsing Pipeline

```
JSON String
    → Kotlinx Json.decodeFromString<CometChatCardSchema>(json)
        → Polymorphic deserialization on "type" field
            → 20 element subclasses resolved via SerializersModule
            → 9 action subclasses resolved via SerializersModule
        → Unknown fields silently ignored (@JsonIgnoreUnknownKeys)
    → Result<CometChatCardSchema> (Success or Failure)
```

## Polymorphic Deserialization Strategy

### Element Type Resolution
The `CometChatCardElement` sealed interface uses a `SerializersModule` with explicit `polymorphic` registration. Each subclass is annotated with `@SerialName` matching the JSON `type` field value:

```
"text"        → CometChatCardTextElement
"image"       → CometChatCardImageElement
"icon"        → CometChatCardIconElement
"avatar"      → CometChatCardAvatarElement
"badge"       → CometChatCardBadgeElement
"divider"     → CometChatCardDividerElement
"spacer"      → CometChatCardSpacerElement
"chip"        → CometChatCardChipElement
"progressBar" → CometChatCardProgressBarElement
"codeBlock"   → CometChatCardCodeBlockElement
"markdown"    → CometChatCardMarkdownElement
"row"         → CometChatCardRowElement
"column"      → CometChatCardColumnElement
"grid"        → CometChatCardGridElement
"accordion"   → CometChatCardAccordionElement
"tabs"        → CometChatCardTabsElement
"button"      → CometChatCardButtonElement
"iconButton"  → CometChatCardIconButtonElement
"link"        → CometChatCardLinkElement
"table"       → CometChatCardTableElement
```

### Action Type Resolution
The `CometChatCardAction` sealed interface uses the same pattern:

```
"openUrl"          → CometChatCardOpenUrlAction
"copyToClipboard"  → CometChatCardCopyToClipboardAction
"downloadFile"     → CometChatCardDownloadFileAction
"apiCall"          → CometChatCardApiCallAction
"chatWithUser"     → CometChatCardChatWithUserAction
"chatWithGroup"    → CometChatCardChatWithGroupAction
"sendMessage"      → CometChatCardSendMessageAction
"initiateCall"     → CometChatCardInitiateCallAction
"customCallback"   → CometChatCardCustomCallbackAction
```

## ColorValue / Plain Hex Dual-Type Handling

Several properties can be either a plain hex string or a ColorValue object. In Kotlinx Serialization, this is handled with a custom serializer:

```
Property value in JSON:
  "#FF0000"                          → stored as CometChatCardColorOrHex.Hex("#FF0000")
  {"light": "#FFF", "dark": "#000"}  → stored as CometChatCardColorOrHex.Themed(CometChatCardColorValue(...))
```

A `CometChatCardColorOrHex` sealed interface with a custom `JsonContentPolymorphicSerializer` inspects whether the JSON element is a string or object.

## Padding Dual-Type Handling

Padding can be a single number or an object:

```
16                                              → CometChatCardPadding.Uniform(16)
{"top": 8, "right": 16, "bottom": 8, "left": 16} → CometChatCardPadding.PerSide(8, 16, 8, 16)
```

Custom serializer inspects whether JSON element is a number or object.

## Dimension Dual-Type Handling (width/height)

Width and height can be a number (dp), percentage string, or "auto":

```
200      → CometChatCardDimension.Dp(200)
"50%"    → CometChatCardDimension.Percent(50f)
"auto"   → CometChatCardDimension.Auto
```

Custom serializer inspects JSON element type and string content.

## Accordion Header Dual-Type Handling

Accordion header can be a string or an array of elements:

```
"Section Title"    → CometChatCardAccordionHeader.Text("Section Title")
[{...}, {...}]     → CometChatCardAccordionHeader.Elements(listOf(...))
```

## Round-Trip Serialization

`serialize(parse(json))` must produce JSON that, when parsed again, yields an equivalent model. This is ensured by:
- All `@Serializable` data classes with explicit `@SerialName` annotations
- Custom serializers that write back the same format they read
- `encodeDefaults = false` to avoid writing null optional fields

## Error Handling in Parser

| Error | Behavior |
|-------|----------|
| Malformed JSON (syntax error) | Return `Result.failure(exception)` |
| Missing `version` field | Return `Result.failure(exception)` |
| Missing `body` field | Return `Result.failure(exception)` |
| Unknown element type in body | Element deserialized as `CometChatCardUnknownElement` (fallback) |
| Unknown fields on known elements | Silently ignored |
| Missing optional fields | Default values applied |
| Missing required fields (e.g., text.content) | Deserialization exception for that element |
