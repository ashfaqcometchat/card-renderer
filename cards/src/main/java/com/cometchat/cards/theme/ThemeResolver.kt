package com.cometchat.cards.theme

import com.cometchat.cards.models.CometChatCardColorOrHex
import com.cometchat.cards.models.CometChatCardColorValue
import com.cometchat.cards.models.CometChatCardThemeMode

data class CometChatCardResolvedTheme(
    val textColor: CometChatCardColorValue,
    val secondaryTextColor: CometChatCardColorValue,
    val backgroundColor: CometChatCardColorValue,
    val borderColor: CometChatCardColorValue,
    val dividerColor: CometChatCardColorValue,
    val buttonFilledBg: CometChatCardColorValue,
    val buttonFilledText: CometChatCardColorValue,
    val buttonOutlinedBorder: CometChatCardColorValue,
    val buttonOutlinedText: CometChatCardColorValue,
    val buttonTonalBg: CometChatCardColorValue,
    val buttonTonalText: CometChatCardColorValue,
    val buttonTextColor: CometChatCardColorValue,
    val buttonDisabledBg: CometChatCardColorValue,
    val buttonDisabledText: CometChatCardColorValue,
    val progressBarColor: CometChatCardColorValue,
    val progressTrackColor: CometChatCardColorValue,
    val codeBlockBg: CometChatCardColorValue,
    val codeBlockText: CometChatCardColorValue,
    val codeBlockLangLabel: CometChatCardColorValue,
    val chipFilledBg: CometChatCardColorValue,
    val chipFilledText: CometChatCardColorValue,
    val badgeBg: CometChatCardColorValue,
    val badgeText: CometChatCardColorValue,
    val avatarBg: CometChatCardColorValue,
    val avatarText: CometChatCardColorValue,
    val linkColor: CometChatCardColorValue,
    val tabActiveColor: CometChatCardColorValue,
    val tabInactiveColor: CometChatCardColorValue,
    val accordionHeaderBg: CometChatCardColorValue,
    val accordionBorderColor: CometChatCardColorValue,
    val tableHeaderBg: CometChatCardColorValue,
    val tableStripedRowBg: CometChatCardColorValue,
    val shimmerBase: CometChatCardColorValue,
    val shimmerHighlight: CometChatCardColorValue,
    val typography: Map<String, CometChatCardTypography>,
    val fontFamily: String?
)

object CometChatCardThemeResolver {

    fun resolveColor(
        value: CometChatCardColorOrHex?,
        effectiveMode: CometChatCardThemeMode,
        defaultValue: CometChatCardColorValue? = null
    ): String? {
        val raw = when (value) {
            is CometChatCardColorOrHex.Transparent -> "#00000000"
            is CometChatCardColorOrHex.Themed -> {
                if (effectiveMode == CometChatCardThemeMode.DARK) value.colorValue.dark
                else value.colorValue.light
            }
            is CometChatCardColorOrHex.Hex -> value.value
            null -> {
                if (defaultValue != null) {
                    if (effectiveMode == CometChatCardThemeMode.DARK) defaultValue.dark
                    else defaultValue.light
                } else null
            }
        }
        return raw?.let { expandHexShorthand(it) }
    }

    /** Expand 3-char (#RGB) and 4-char (#RGBA) hex shorthand to full 6/8-char form */
    private fun expandHexShorthand(hex: String): String {
        if (hex.length == 4 && hex.startsWith("#")) {
            return "#${hex[1]}${hex[1]}${hex[2]}${hex[2]}${hex[3]}${hex[3]}"
        }
        if (hex.length == 5 && hex.startsWith("#")) {
            return "#${hex[1]}${hex[1]}${hex[2]}${hex[2]}${hex[3]}${hex[3]}${hex[4]}${hex[4]}"
        }
        return hex
    }

    fun resolveUrl(
        value: CometChatCardColorOrHex?,
        effectiveMode: CometChatCardThemeMode
    ): String? {
        return when (value) {
            is CometChatCardColorOrHex.Transparent -> null
            is CometChatCardColorOrHex.Themed -> {
                if (effectiveMode == CometChatCardThemeMode.DARK) value.colorValue.dark
                else value.colorValue.light
            }
            is CometChatCardColorOrHex.Hex -> value.value
            null -> null
        }
    }

    fun resolveEffectiveMode(
        mode: CometChatCardThemeMode,
        isSystemDark: Boolean
    ): CometChatCardThemeMode {
        return when (mode) {
            CometChatCardThemeMode.AUTO -> if (isSystemDark) CometChatCardThemeMode.DARK else CometChatCardThemeMode.LIGHT
            else -> mode
        }
    }

    fun resolveTheme(override: CometChatCardThemeOverride?): CometChatCardResolvedTheme {
        val d = CometChatCardDefaultTheme
        return CometChatCardResolvedTheme(
            textColor = override?.textColor ?: d.textColor,
            secondaryTextColor = override?.secondaryTextColor ?: d.secondaryTextColor,
            backgroundColor = override?.backgroundColor ?: d.backgroundColor,
            borderColor = override?.borderColor ?: d.borderColor,
            dividerColor = override?.dividerColor ?: d.dividerColor,
            buttonFilledBg = override?.buttonFilledBg ?: d.buttonFilledBg,
            buttonFilledText = override?.buttonFilledText ?: d.buttonFilledText,
            buttonOutlinedBorder = override?.buttonOutlinedBorder ?: d.buttonOutlinedBorder,
            buttonOutlinedText = override?.buttonOutlinedText ?: d.buttonOutlinedText,
            buttonTonalBg = override?.buttonTonalBg ?: d.buttonTonalBg,
            buttonTonalText = override?.buttonTonalText ?: d.buttonTonalText,
            buttonTextColor = override?.buttonTextColor ?: d.buttonTextColor,
            buttonDisabledBg = override?.buttonDisabledBg ?: d.buttonDisabledBg,
            buttonDisabledText = override?.buttonDisabledText ?: d.buttonDisabledText,
            progressBarColor = override?.progressBarColor ?: d.progressBarColor,
            progressTrackColor = override?.progressTrackColor ?: d.progressTrackColor,
            codeBlockBg = override?.codeBlockBg ?: d.codeBlockBg,
            codeBlockText = override?.codeBlockText ?: d.codeBlockText,
            codeBlockLangLabel = override?.codeBlockLangLabel ?: d.codeBlockLangLabel,
            chipFilledBg = override?.chipFilledBg ?: d.chipFilledBg,
            chipFilledText = override?.chipFilledText ?: d.chipFilledText,
            badgeBg = override?.badgeBg ?: d.badgeBg,
            badgeText = override?.badgeText ?: d.badgeText,
            avatarBg = override?.avatarBg ?: d.avatarBg,
            avatarText = override?.avatarText ?: d.avatarText,
            linkColor = override?.linkColor ?: d.linkColor,
            tabActiveColor = override?.tabActiveColor ?: d.tabActiveColor,
            tabInactiveColor = override?.tabInactiveColor ?: d.tabInactiveColor,
            accordionHeaderBg = override?.accordionHeaderBg ?: d.accordionHeaderBg,
            accordionBorderColor = override?.accordionBorderColor ?: d.accordionBorderColor,
            tableHeaderBg = override?.tableHeaderBg ?: d.tableHeaderBg,
            tableStripedRowBg = override?.tableStripedRowBg ?: d.tableStripedRowBg,
            shimmerBase = override?.shimmerBase ?: d.shimmerBase,
            shimmerHighlight = override?.shimmerHighlight ?: d.shimmerHighlight,
            typography = d.typography,
            fontFamily = override?.fontFamily ?: d.fontFamily
        )
    }
}
