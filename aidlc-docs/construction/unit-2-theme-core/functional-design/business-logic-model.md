# Unit 2: Theme & Core Infrastructure — Business Logic Model

## Theme Resolution Pipeline

```
Input: (propertyValue, effectiveMode, resolvedTheme)
    ↓
1. If propertyValue is CometChatCardColorOrHex.Themed → resolve light/dark based on effectiveMode
2. If propertyValue is CometChatCardColorOrHex.Hex → return hex string as-is
3. If propertyValue is null → look up resolvedTheme fallback
4. If resolvedTheme fallback is CometChatCardColorValue → resolve light/dark based on effectiveMode
5. If resolvedTheme fallback is null → return null (no color applied)
```

## Theme Merging Logic

```
ResolvedTheme = deepMerge(DefaultTheme, ThemeOverride)

For each token:
  resolvedTheme[token] = themeOverride[token] ?? defaultTheme[token]

At render time for each element property:
  effectiveValue = element.jsonValue ?? resolvedTheme[token]
```

## Effective Theme Mode Resolution

```
resolveEffectiveMode(mode, isSystemDark):
  if mode == AUTO:
    return if isSystemDark then DARK else LIGHT
  return mode  // LIGHT or DARK as-is
```

## Action Emission Logic

```
emit(action, elementId, cardJson, callback, isDisabled):
  if isDisabled → return false (no-op)
  if action is null → return false (no-op)
  if callback is null → return false (no-op)
  if not isValidAction(action) → return false (no-op)
  callback.onAction(CometChatCardActionEvent(action, elementId, cardJson))
  return true
```

## Action Validation Rules

```
isValidAction(action):
  when action:
    OpenUrl → action.url.isNotBlank()
    CopyToClipboard → action.value.isNotBlank()
    DownloadFile → action.url.isNotBlank()
    ApiCall → action.url.isNotBlank()
    ChatWithUser → action.uid.isNotBlank()
    ChatWithGroup → action.guid.isNotBlank()
    SendMessage → action.text.isNotBlank()
    InitiateCall → action.callType in ["audio","video"] && (action.uid?.isNotBlank() == true || action.guid?.isNotBlank() == true)
    CustomCallback → action.callbackId.isNotBlank()
```

## Logger Filtering Logic

```
shouldLog(messageLevel, configuredLevel):
  levelOrder = [NONE=0, ERROR=1, WARNING=2, VERBOSE=3]
  return levelOrder[messageLevel] <= levelOrder[configuredLevel]
```

## Loading State Management

```
loadingStates: MutableMap<String, Boolean> = mutableMapOf()

setLoading(elementId, loading):
  if loading: loadingStates[elementId] = true
  else: loadingStates.remove(elementId)

isLoading(elementId):
  return loadingStates[elementId] == true
```

## Testable Properties (PBT-01)

| Property | Category | Description |
|----------|----------|-------------|
| P3: Theme-aware value resolution | Invariant | ColorValue.light returned in light mode, .dark in dark mode; plain hex unchanged |
| P4: Theme precedence chain | Invariant | JSON > ThemeOverride > DefaultTheme |
| P19: Log level filtering | Invariant | Only messages at or above configured level are output |
| P28: Manual mode ignores system | Invariant | Explicit light/dark mode unaffected by system changes |
