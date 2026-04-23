# Unit 1: Models & Parser — Business Rules

## BR-1: Schema Version Validation
- Supported versions: ["1.0"]
- Unsupported version → best-effort parse, log warning
- Missing version → parse failure

## BR-2: Required Field Validation
- CometChatCardSchema: `version` and `body` are required, `fallbackText` is required
- Each element: `id` and `type` are required
- Element-specific required fields per domain-entities.md table

## BR-3: Unknown Element Type Handling
- Unknown `type` values during deserialization → deserialize as `CometChatCardUnknownElement` with raw JSON preserved
- Parser does NOT fail on unknown types — they are passed through for the renderer to skip

## BR-4: Unknown Field Handling
- `@OptIn(ExperimentalSerializationApi::class)` with `ignoreUnknownKeys = true`
- Extra JSON fields on any element are silently discarded

## BR-5: ColorOrHex Deserialization Rules
- JSON string → `CometChatCardColorOrHex.Hex(value)`
- JSON object with `light` and `dark` → `CometChatCardColorOrHex.Themed(CometChatCardColorValue(light, dark))`
- Other → deserialization error (skip element)

## BR-6: Padding Deserialization Rules
- JSON number → `CometChatCardPadding.Uniform(value.toInt())`
- JSON object → `CometChatCardPadding.PerSide(top, right, bottom, left)` with defaults of 0

## BR-7: Dimension Deserialization Rules
- JSON number → `CometChatCardDimension.Dp(value.toInt())`
- JSON string "auto" → `CometChatCardDimension.Auto`
- JSON string ending with "%" → `CometChatCardDimension.Percent(parseFloat(value.removeSuffix("%")))`
- Other string → deserialization error

## BR-8: Accordion Header Deserialization Rules
- JSON string → `CometChatCardAccordionHeader.Text(value)`
- JSON array → `CometChatCardAccordionHeader.Elements(list)` with recursive element deserialization

## BR-9: Action Required Field Validation (for emission, not parsing)
- openUrl: `url` must be non-empty
- copyToClipboard: `value` must be non-empty
- downloadFile: `url` must be non-empty
- apiCall: `url` must be non-empty
- chatWithUser: `uid` must be non-empty
- chatWithGroup: `guid` must be non-empty
- sendMessage: `text` must be non-empty
- initiateCall: `callType` must be "audio" or "video", at least one of uid/guid non-empty
- customCallback: `callbackId` must be non-empty

## BR-10: Round-Trip Serialization
- `parse(serialize(parse(json)))` must produce a model equivalent to `parse(json)`
- `encodeDefaults = false` to avoid writing null optional fields
- Custom serializers must write back the same format they read

## Testable Properties (PBT-01)

| Property | Category | Description |
|----------|----------|-------------|
| P1: Parse round-trip | Round-trip | parse → serialize → parse produces equivalent model |
| P2: ContainerStyle extraction | Invariant | All style fields preserved through parsing |
| P20: Unknown fields ignored | Invariant | Extra JSON fields don't cause parse failure |
