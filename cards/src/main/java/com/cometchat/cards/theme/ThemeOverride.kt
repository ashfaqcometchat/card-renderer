package com.cometchat.cards.theme

import com.cometchat.cards.models.CometChatCardColorValue

data class CometChatCardThemeOverride(
    val textColor: CometChatCardColorValue? = null,
    val secondaryTextColor: CometChatCardColorValue? = null,
    val backgroundColor: CometChatCardColorValue? = null,
    val borderColor: CometChatCardColorValue? = null,
    val dividerColor: CometChatCardColorValue? = null,
    val buttonFilledBg: CometChatCardColorValue? = null,
    val buttonFilledText: CometChatCardColorValue? = null,
    val buttonOutlinedBorder: CometChatCardColorValue? = null,
    val buttonOutlinedText: CometChatCardColorValue? = null,
    val buttonTonalBg: CometChatCardColorValue? = null,
    val buttonTonalText: CometChatCardColorValue? = null,
    val buttonTextColor: CometChatCardColorValue? = null,
    val buttonDisabledBg: CometChatCardColorValue? = null,
    val buttonDisabledText: CometChatCardColorValue? = null,
    val progressBarColor: CometChatCardColorValue? = null,
    val progressTrackColor: CometChatCardColorValue? = null,
    val codeBlockBg: CometChatCardColorValue? = null,
    val codeBlockText: CometChatCardColorValue? = null,
    val linkColor: CometChatCardColorValue? = null,
    val tabActiveColor: CometChatCardColorValue? = null,
    val tabInactiveColor: CometChatCardColorValue? = null,
    val shimmerBase: CometChatCardColorValue? = null,
    val shimmerHighlight: CometChatCardColorValue? = null,
    val fontFamily: String? = null
)
