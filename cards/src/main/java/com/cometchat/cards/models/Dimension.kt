package com.cometchat.cards.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.floatOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = CometChatCardDimensionSerializer::class)
sealed interface CometChatCardDimension {
    data class Dp(val value: Int) : CometChatCardDimension
    data class Percent(val value: Float) : CometChatCardDimension
    data object Auto : CometChatCardDimension
}

object CometChatCardDimensionSerializer : KSerializer<CometChatCardDimension> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("CometChatCardDimension")

    override fun deserialize(decoder: Decoder): CometChatCardDimension {
        val jsonDecoder = decoder as JsonDecoder
        val element = jsonDecoder.decodeJsonElement().jsonPrimitive
        return when {
            element.intOrNull != null -> CometChatCardDimension.Dp(element.int)
            element.isString && element.content == "auto" -> CometChatCardDimension.Auto
            element.isString && element.content.endsWith("%") -> {
                val pct = element.content.removeSuffix("%").toFloatOrNull() ?: 0f
                CometChatCardDimension.Percent(pct)
            }
            element.floatOrNull != null -> CometChatCardDimension.Dp(element.float.toInt())
            else -> CometChatCardDimension.Auto
        }
    }

    override fun serialize(encoder: Encoder, value: CometChatCardDimension) {
        val jsonEncoder = encoder as JsonEncoder
        when (value) {
            is CometChatCardDimension.Dp -> jsonEncoder.encodeJsonElement(JsonPrimitive(value.value))
            is CometChatCardDimension.Percent -> jsonEncoder.encodeJsonElement(JsonPrimitive("${value.value}%"))
            is CometChatCardDimension.Auto -> jsonEncoder.encodeJsonElement(JsonPrimitive("auto"))
        }
    }
}
