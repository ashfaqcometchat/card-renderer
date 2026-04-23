package com.cometchat.cards.models

import kotlinx.serialization.Serializable

@Serializable
data class CometChatCardContainerStyle(
    val background: CometChatCardColorOrHex? = null,
    val borderRadius: Int? = null,
    val borderColor: CometChatCardColorOrHex? = null,
    val borderWidth: Int? = null,
    val padding: CometChatCardPadding? = null,
    val maxWidth: Int? = null,
    val maxHeight: Int? = null,
    val width: CometChatCardDimension? = null,
    val height: CometChatCardDimension? = null
)

@Serializable
data class CometChatCardSchema(
    val version: String,
    val body: List<CometChatCardElement>,
    val style: CometChatCardContainerStyle? = null,
    val fallbackText: String
)
