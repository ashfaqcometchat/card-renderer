package com.cometchat.cards.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.int
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = CometChatCardPaddingSerializer::class)
sealed interface CometChatCardPadding {
    data class Uniform(val value: Int) : CometChatCardPadding
    data class PerSide(
        val top: Int = 0,
        val right: Int = 0,
        val bottom: Int = 0,
        val left: Int = 0
    ) : CometChatCardPadding
}

object CometChatCardPaddingSerializer : KSerializer<CometChatCardPadding> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("CometChatCardPadding")

    override fun deserialize(decoder: Decoder): CometChatCardPadding {
        val jsonDecoder = decoder as JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        return when (element) {
            is JsonPrimitive -> CometChatCardPadding.Uniform(element.int)
            is JsonObject -> CometChatCardPadding.PerSide(
                top = element["top"]?.jsonPrimitive?.intOrNull ?: 0,
                right = element["right"]?.jsonPrimitive?.intOrNull ?: 0,
                bottom = element["bottom"]?.jsonPrimitive?.intOrNull ?: 0,
                left = element["left"]?.jsonPrimitive?.intOrNull ?: 0
            )
            else -> CometChatCardPadding.Uniform(0)
        }
    }

    override fun serialize(encoder: Encoder, value: CometChatCardPadding) {
        val jsonEncoder = encoder as JsonEncoder
        when (value) {
            is CometChatCardPadding.Uniform -> jsonEncoder.encodeJsonElement(JsonPrimitive(value.value))
            is CometChatCardPadding.PerSide -> jsonEncoder.encodeJsonElement(
                JsonObject(buildMap {
                    if (value.top != 0) put("top", JsonPrimitive(value.top))
                    if (value.right != 0) put("right", JsonPrimitive(value.right))
                    if (value.bottom != 0) put("bottom", JsonPrimitive(value.bottom))
                    if (value.left != 0) put("left", JsonPrimitive(value.left))
                })
            )
        }
    }
}
