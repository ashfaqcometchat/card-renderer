package com.cometchat.cards.theme

import com.cometchat.cards.models.CometChatCardColorValue

data class CometChatCardTypography(
    val fontSize: Int,
    val fontWeight: String = "regular"
)

object CometChatCardDefaultTheme {
    val textColor = CometChatCardColorValue(light = "#141414", dark = "#E8E8E8")
    val secondaryTextColor = CometChatCardColorValue(light = "#727272", dark = "#A0A0A0")
    val backgroundColor = CometChatCardColorValue(light = "#FFFFFF", dark = "#1A1A1A")
    val borderColor = CometChatCardColorValue(light = "#E0E0E0", dark = "#3A3A3A")
    val dividerColor = CometChatCardColorValue(light = "#E0E0E0", dark = "#3A3A3A")

    val buttonFilledBg = CometChatCardColorValue(light = "#3A3AF4", dark = "#5A5AF6")
    val buttonFilledText = CometChatCardColorValue(light = "#FFFFFF", dark = "#FFFFFF")
    val buttonOutlinedBorder = CometChatCardColorValue(light = "#3A3AF4", dark = "#5A5AF6")
    val buttonOutlinedText = CometChatCardColorValue(light = "#3A3AF4", dark = "#5A5AF6")
    val buttonTonalBg = CometChatCardColorValue(light = "#3A3AF41A", dark = "#5A5AF61A")
    val buttonTonalText = CometChatCardColorValue(light = "#3A3AF4", dark = "#5A5AF6")
    val buttonTextColor = CometChatCardColorValue(light = "#3A3AF4", dark = "#5A5AF6")
    val buttonDisabledBg = CometChatCardColorValue(light = "#E0E0E0", dark = "#3A3A3A")
    val buttonDisabledText = CometChatCardColorValue(light = "#A0A0A0", dark = "#727272")

    val progressBarColor = CometChatCardColorValue(light = "#3A3AF4", dark = "#5A5AF6")
    val progressTrackColor = CometChatCardColorValue(light = "#E0E0E0", dark = "#3A3A3A")

    val codeBlockBg = CometChatCardColorValue(light = "#F5F5F5", dark = "#2A2A2A")
    val codeBlockText = CometChatCardColorValue(light = "#141414", dark = "#E8E8E8")
    val codeBlockLangLabel = CometChatCardColorValue(light = "#727272", dark = "#A0A0A0")

    val chipFilledBg = CometChatCardColorValue(light = "#F0F0F0", dark = "#333333")
    val chipFilledText = CometChatCardColorValue(light = "#141414", dark = "#E8E8E8")

    val badgeBg = CometChatCardColorValue(light = "#3A3AF4", dark = "#5A5AF6")
    val badgeText = CometChatCardColorValue(light = "#FFFFFF", dark = "#FFFFFF")

    val avatarBg = CometChatCardColorValue(light = "#E0E0E0", dark = "#3A3A3A")
    val avatarText = CometChatCardColorValue(light = "#141414", dark = "#E8E8E8")

    val linkColor = CometChatCardColorValue(light = "#3A3AF4", dark = "#5A5AF6")

    val tabActiveColor = CometChatCardColorValue(light = "#3A3AF4", dark = "#5A5AF6")
    val tabInactiveColor = CometChatCardColorValue(light = "#727272", dark = "#A0A0A0")

    val accordionHeaderBg = CometChatCardColorValue(light = "#F5F5F5", dark = "#2A2A2A")
    val accordionBorderColor = CometChatCardColorValue(light = "#E0E0E0", dark = "#3A3A3A")

    val tableHeaderBg = CometChatCardColorValue(light = "#F5F5F5", dark = "#2A2A2A")
    val tableStripedRowBg = CometChatCardColorValue(light = "#FAFAFA", dark = "#252525")

    val shimmerBase = CometChatCardColorValue(light = "#E0E0E0", dark = "#3A3A3A")
    val shimmerHighlight = CometChatCardColorValue(light = "#F5F5F5", dark = "#4A4A4A")

    val placeholderIconColor = CometChatCardColorValue(light = "#A0A0A0", dark = "#727272")

    val typography = mapOf(
        "title" to CometChatCardTypography(fontSize = 32, fontWeight = "bold"),
        "heading1" to CometChatCardTypography(fontSize = 24, fontWeight = "bold"),
        "heading2" to CometChatCardTypography(fontSize = 20, fontWeight = "bold"),
        "heading3" to CometChatCardTypography(fontSize = 18, fontWeight = "bold"),
        "heading4" to CometChatCardTypography(fontSize = 16, fontWeight = "bold"),
        "body" to CometChatCardTypography(fontSize = 14, fontWeight = "regular"),
        "body1" to CometChatCardTypography(fontSize = 14, fontWeight = "regular"),
        "body2" to CometChatCardTypography(fontSize = 12, fontWeight = "regular"),
        "caption1" to CometChatCardTypography(fontSize = 12, fontWeight = "regular"),
        "caption2" to CometChatCardTypography(fontSize = 10, fontWeight = "regular"),
    )

    val fontFamily: String? = null
}
