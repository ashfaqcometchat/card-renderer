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
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@Serializable
data class CometChatCardColorValue(
    val light: String,
    val dark: String
)

@Serializable(with = CometChatCardColorOrHexSerializer::class)
sealed interface CometChatCardColorOrHex {
    data class Hex(val value: String) : CometChatCardColorOrHex
    data class Themed(val colorValue: CometChatCardColorValue) : CometChatCardColorOrHex
}

object CometChatCardColorOrHexSerializer : KSerializer<CometChatCardColorOrHex> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("CometChatCardColorOrHex")

    override fun deserialize(decoder: Decoder): CometChatCardColorOrHex {
        val jsonDecoder = decoder as JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        return when (element) {
            is JsonPrimitive -> CometChatCardColorOrHex.Hex(element.content)
            is JsonObject -> {
                val light = element["light"]?.jsonPrimitive?.content ?: ""
                val dark = element["dark"]?.jsonPrimitive?.content ?: ""
                CometChatCardColorOrHex.Themed(CometChatCardColorValue(light, dark))
            }
            else -> CometChatCardColorOrHex.Hex("")
        }
    }

    override fun serialize(encoder: Encoder, value: CometChatCardColorOrHex) {
        val jsonEncoder = encoder as JsonEncoder
        when (value) {
            is CometChatCardColorOrHex.Hex -> jsonEncoder.encodeJsonElement(JsonPrimitive(value.value))
            is CometChatCardColorOrHex.Themed -> jsonEncoder.encodeJsonElement(
                JsonObject(
                    mapOf(
                        "light" to JsonPrimitive(value.colorValue.light),
                        "dark" to JsonPrimitive(value.colorValue.dark)
                    )
                )
            )
        }
    }
}
