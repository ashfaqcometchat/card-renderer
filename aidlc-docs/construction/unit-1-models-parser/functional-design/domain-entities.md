# Unit 1: Models & Parser — Domain Entities

## Entity Hierarchy

### CometChatCardSchema (Root)
```
CometChatCardSchema
├── version: String
├── body: List<CometChatCardElement>
├── style: CometChatCardContainerStyle?
└── fallbackText: String
```

### CometChatCardElement (Sealed Interface — 20 subclasses)

#### Content Elements (11)
| Class | Required Fields | Optional Fields |
|-------|----------------|-----------------|
| CometChatCardTextElement | id, content | variant, color, fontWeight, align, maxLines, padding |
| CometChatCardImageElement | id, url | altText, fit, width, height, borderRadius, padding |
| CometChatCardIconElement | id, name | size, color, backgroundColor, borderRadius, padding |
| CometChatCardAvatarElement | id | imageUrl, fallbackInitials, size, borderRadius, backgroundColor, fontSize, fontWeight |
| CometChatCardBadgeElement | id, text | color, backgroundColor, borderColor, borderWidth, borderRadius, fontSize, padding |
| CometChatCardDividerElement | id | color, thickness, margin |
| CometChatCardSpacerElement | id, height | — |
| CometChatCardChipElement | id, text | color, icon, backgroundColor, borderColor, borderWidth, borderRadius, fontSize, padding |
| CometChatCardProgressBarElement | id, value | color, trackColor, height, label, borderRadius, labelFontSize, labelColor |
| CometChatCardCodeBlockElement | id, content | language, backgroundColor, textColor, padding, borderRadius, fontSize, languageLabelFontSize, languageLabelColor |
| CometChatCardMarkdownElement | id, content | baseFontSize, linkColor, color, lineHeight |

#### Layout Elements (5)
| Class | Required Fields | Optional Fields |
|-------|----------------|-----------------|
| CometChatCardRowElement | id, items | gap, align, wrap, scrollable, peek, snap, padding, backgroundColor, borderRadius, borderColor, borderWidth |
| CometChatCardColumnElement | id, items | gap, align, padding, backgroundColor, borderRadius, borderColor, borderWidth |
| CometChatCardGridElement | id, items | columns, gap, padding, backgroundColor, borderRadius, borderColor, borderWidth |
| CometChatCardAccordionElement | id, header, body | headerIcon, expandedByDefault, border, padding, fontSize, fontWeight, borderRadius |
| CometChatCardTabsElement | id, tabs | defaultActiveTab, tabAlign, tabPadding, contentPadding, fontSize |

#### Interactive Elements (3)
| Class | Required Fields | Optional Fields |
|-------|----------------|-----------------|
| CometChatCardButtonElement | id, label | action, variant, backgroundColor, textColor, borderColor, borderWidth, borderRadius, padding, fontSize, icon, iconPosition, size, fullWidth, disabled |
| CometChatCardIconButtonElement | id, icon | action, size, backgroundColor, color, borderRadius |
| CometChatCardLinkElement | id, text | action, color, underline, fontSize |

#### Data Display Elements (1)
| Class | Required Fields | Optional Fields |
|-------|----------------|-----------------|
| CometChatCardTableElement | id, columns, rows | stripedRows, headerBackgroundColor, border, cellPadding, fontSize, stripedRowColor, borderColor |

### CometChatCardAction (Sealed Interface — 9 subclasses)
| Class | Required Fields | Optional Fields |
|-------|----------------|-----------------|
| CometChatCardOpenUrlAction | url | openIn |
| CometChatCardCopyToClipboardAction | value | — |
| CometChatCardDownloadFileAction | url | filename |
| CometChatCardApiCallAction | url | method, headers, body |
| CometChatCardChatWithUserAction | uid | — |
| CometChatCardChatWithGroupAction | guid | — |
| CometChatCardSendMessageAction | text | receiverUid, receiverGuid |
| CometChatCardInitiateCallAction | callType | uid, guid |
| CometChatCardCustomCallbackAction | callbackId | payload |

### Supporting Types
| Type | Description |
|------|-------------|
| CometChatCardContainerStyle | background, borderRadius, borderColor, borderWidth, padding, maxWidth, maxHeight, width, height |
| CometChatCardColorValue | light: String, dark: String |
| CometChatCardColorOrHex | Sealed: Hex(value) or Themed(CometChatCardColorValue) |
| CometChatCardPadding | Sealed: Uniform(value) or PerSide(top, right, bottom, left) |
| CometChatCardDimension | Sealed: Dp(value), Percent(value), Auto |
| CometChatCardAccordionHeader | Sealed: Text(value) or Elements(List<CometChatCardElement>) |
| CometChatCardTabItem | label: String, content: List<CometChatCardElement> |
| CometChatCardThemeMode | Enum: AUTO, LIGHT, DARK |
| CometChatCardLogLevel | Enum: NONE, ERROR, WARNING, VERBOSE |
| CometChatCardActionEvent | action, elementId, cardJson |
