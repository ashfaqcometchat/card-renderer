package com.cometchat.cards.models

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive

@Serializable
sealed interface CometChatCardElement {
    val id: String
}

// --- Content Elements (11) ---

@Serializable
@SerialName("text")
data class CometChatCardTextElement(
    override val id: String,
    val content: String,
    val variant: String? = null,
    val color: CometChatCardColorOrHex? = null,
    val fontWeight: String? = null,
    val align: String? = null,
    val maxLines: Int? = null,
    val padding: CometChatCardPadding? = null
) : CometChatCardElement

@Serializable
@SerialName("image")
data class CometChatCardImageElement(
    override val id: String,
    val url: CometChatCardColorOrHex,
    val altText: String? = null,
    val fit: String? = null,
    val width: CometChatCardDimension? = null,
    val height: CometChatCardDimension? = null,
    val borderRadius: Int? = null,
    val padding: CometChatCardPadding? = null
) : CometChatCardElement

@Serializable
@SerialName("icon")
data class CometChatCardIconElement(
    override val id: String,
    val name: CometChatCardColorOrHex,
    val size: Int? = null,
    val color: CometChatCardColorOrHex? = null,
    val backgroundColor: CometChatCardColorOrHex? = null,
    val borderRadius: Int? = null,
    val padding: Int? = null
) : CometChatCardElement


@Serializable
@SerialName("avatar")
data class CometChatCardAvatarElement(
    override val id: String,
    val imageUrl: String? = null,
    val fallbackInitials: String? = null,
    val size: Int? = null,
    val borderRadius: Int? = null,
    val backgroundColor: CometChatCardColorOrHex? = null,
    val fontSize: Int? = null,
    val fontWeight: String? = null
) : CometChatCardElement

@Serializable
@SerialName("badge")
data class CometChatCardBadgeElement(
    override val id: String,
    val text: String,
    val color: CometChatCardColorOrHex? = null,
    val backgroundColor: CometChatCardColorOrHex? = null,
    val borderColor: CometChatCardColorOrHex? = null,
    val borderWidth: Int? = null,
    val borderRadius: Int? = null,
    val fontSize: Int? = null,
    val padding: CometChatCardPadding? = null
) : CometChatCardElement

@Serializable
@SerialName("divider")
data class CometChatCardDividerElement(
    override val id: String,
    val color: CometChatCardColorOrHex? = null,
    val thickness: Int? = null,
    val margin: Int? = null
) : CometChatCardElement

@Serializable
@SerialName("spacer")
data class CometChatCardSpacerElement(
    override val id: String,
    val height: Int
) : CometChatCardElement

@Serializable
@SerialName("chip")
data class CometChatCardChipElement(
    override val id: String,
    val text: String,
    val color: CometChatCardColorOrHex? = null,
    val icon: CometChatCardColorOrHex? = null,
    val backgroundColor: CometChatCardColorOrHex? = null,
    val borderColor: CometChatCardColorOrHex? = null,
    val borderWidth: Int? = null,
    val borderRadius: Int? = null,
    val fontSize: Int? = null,
    val padding: CometChatCardPadding? = null
) : CometChatCardElement

@Serializable
@SerialName("progressBar")
data class CometChatCardProgressBarElement(
    override val id: String,
    val value: Int,
    val color: CometChatCardColorOrHex? = null,
    val trackColor: CometChatCardColorOrHex? = null,
    val height: Int? = null,
    val label: String? = null,
    val borderRadius: Int? = null,
    val labelFontSize: Int? = null,
    val labelColor: CometChatCardColorOrHex? = null
) : CometChatCardElement

@Serializable
@SerialName("codeBlock")
data class CometChatCardCodeBlockElement(
    override val id: String,
    val content: String,
    val language: String? = null,
    val backgroundColor: CometChatCardColorOrHex? = null,
    val textColor: CometChatCardColorOrHex? = null,
    val padding: CometChatCardPadding? = null,
    val borderRadius: Int? = null,
    val fontSize: Int? = null,
    val languageLabelFontSize: Int? = null,
    val languageLabelColor: CometChatCardColorOrHex? = null
) : CometChatCardElement

@Serializable
@SerialName("markdown")
data class CometChatCardMarkdownElement(
    override val id: String,
    val content: String,
    val baseFontSize: Int? = null,
    val linkColor: CometChatCardColorOrHex? = null,
    val color: CometChatCardColorOrHex? = null,
    val lineHeight: Float? = null
) : CometChatCardElement

// --- Layout Elements (5) ---

@Serializable
@SerialName("row")
data class CometChatCardRowElement(
    override val id: String,
    val items: List<CometChatCardElement>,
    val gap: Int? = null,
    val align: String? = null,
    val crossAlign: String? = null,
    val wrap: Boolean? = null,
    val scrollable: Boolean? = null,
    val peek: Int? = null,
    val snap: String? = null,
    val padding: CometChatCardPadding? = null,
    val backgroundColor: CometChatCardColorOrHex? = null,
    val borderRadius: Int? = null,
    val borderColor: CometChatCardColorOrHex? = null,
    val borderWidth: Int? = null
) : CometChatCardElement

@Serializable
@SerialName("column")
data class CometChatCardColumnElement(
    override val id: String,
    val items: List<CometChatCardElement>,
    val gap: Int? = null,
    val align: String? = null,
    val crossAlign: String? = null,
    val padding: CometChatCardPadding? = null,
    val backgroundColor: CometChatCardColorOrHex? = null,
    val borderRadius: Int? = null,
    val borderColor: CometChatCardColorOrHex? = null,
    val borderWidth: Int? = null
) : CometChatCardElement

@Serializable
@SerialName("grid")
data class CometChatCardGridElement(
    override val id: String,
    val items: List<CometChatCardElement>,
    val columns: Int? = null,
    val gap: Int? = null,
    val padding: CometChatCardPadding? = null,
    val backgroundColor: CometChatCardColorOrHex? = null,
    val borderRadius: Int? = null,
    val borderColor: CometChatCardColorOrHex? = null,
    val borderWidth: Int? = null
) : CometChatCardElement

@Serializable(with = CometChatCardAccordionHeaderSerializer::class)
sealed interface CometChatCardAccordionHeader {
    data class Text(val value: String) : CometChatCardAccordionHeader
    data class Elements(val items: List<CometChatCardElement>) : CometChatCardAccordionHeader
}

object CometChatCardAccordionHeaderSerializer : KSerializer<CometChatCardAccordionHeader> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("CometChatCardAccordionHeader")

    override fun deserialize(decoder: Decoder): CometChatCardAccordionHeader {
        val jsonDecoder = decoder as JsonDecoder
        val element = jsonDecoder.decodeJsonElement()
        return when (element) {
            is JsonPrimitive -> CometChatCardAccordionHeader.Text(element.content)
            is JsonArray -> {
                val elements = element.map {
                    jsonDecoder.json.decodeFromJsonElement(CometChatCardElement.serializer(), it)
                }
                CometChatCardAccordionHeader.Elements(elements)
            }
            else -> CometChatCardAccordionHeader.Text("")
        }
    }

    override fun serialize(encoder: Encoder, value: CometChatCardAccordionHeader) {
        val jsonEncoder = encoder as JsonEncoder
        when (value) {
            is CometChatCardAccordionHeader.Text -> jsonEncoder.encodeJsonElement(JsonPrimitive(value.value))
            is CometChatCardAccordionHeader.Elements -> {
                val array = JsonArray(value.items.map {
                    jsonEncoder.json.encodeToJsonElement(CometChatCardElement.serializer(), it)
                })
                jsonEncoder.encodeJsonElement(array)
            }
        }
    }
}

@Serializable
@SerialName("accordion")
data class CometChatCardAccordionElement(
    override val id: String,
    val header: CometChatCardAccordionHeader,
    val headerIcon: CometChatCardColorOrHex? = null,
    val body: List<CometChatCardElement>,
    val expandedByDefault: Boolean? = null,
    val border: Boolean? = null,
    val padding: CometChatCardPadding? = null,
    val fontSize: Int? = null,
    val fontWeight: String? = null,
    val borderRadius: Int? = null
) : CometChatCardElement

@Serializable
data class CometChatCardTabItem(
    val label: String,
    val content: List<CometChatCardElement>
)

@Serializable
@SerialName("tabs")
data class CometChatCardTabsElement(
    override val id: String,
    val tabs: List<CometChatCardTabItem>,
    val defaultActiveTab: Int? = null,
    val tabAlign: String? = null,
    val tabPadding: CometChatCardPadding? = null,
    val contentPadding: CometChatCardPadding? = null,
    val fontSize: Int? = null
) : CometChatCardElement

// --- Interactive Elements (3) ---

@Serializable
@SerialName("button")
data class CometChatCardButtonElement(
    override val id: String,
    val label: String,
    val action: CometChatCardAction? = null,
    val variant: String? = null,
    val backgroundColor: CometChatCardColorOrHex? = null,
    val textColor: CometChatCardColorOrHex? = null,
    val borderColor: CometChatCardColorOrHex? = null,
    val borderWidth: Int? = null,
    val borderRadius: Int? = null,
    val padding: CometChatCardPadding? = null,
    val fontSize: Int? = null,
    val icon: CometChatCardColorOrHex? = null,
    val iconPosition: String? = null,
    val size: String? = null,
    val fullWidth: Boolean? = null,
    val disabled: Boolean? = null
) : CometChatCardElement

@Serializable
@SerialName("iconButton")
data class CometChatCardIconButtonElement(
    override val id: String,
    val icon: CometChatCardColorOrHex,
    val action: CometChatCardAction? = null,
    val size: Int? = null,
    val backgroundColor: CometChatCardColorOrHex? = null,
    val color: CometChatCardColorOrHex? = null,
    val borderRadius: Int? = null
) : CometChatCardElement

@Serializable
@SerialName("link")
data class CometChatCardLinkElement(
    override val id: String,
    val text: String,
    val action: CometChatCardAction? = null,
    val color: CometChatCardColorOrHex? = null,
    val underline: Boolean? = null,
    val fontSize: Int? = null
) : CometChatCardElement

// --- Data Display Elements (1) ---

@Serializable
@SerialName("table")
data class CometChatCardTableElement(
    override val id: String,
    val columns: List<String>,
    val rows: List<List<String>>,
    val stripedRows: Boolean? = null,
    val headerBackgroundColor: CometChatCardColorOrHex? = null,
    val border: Boolean? = null,
    val cellPadding: Int? = null,
    val fontSize: Int? = null,
    val stripedRowColor: CometChatCardColorOrHex? = null,
    val borderColor: CometChatCardColorOrHex? = null
) : CometChatCardElement

val CometChatCardElement.type: String get() = when (this) {
    is CometChatCardTextElement -> "text"
    is CometChatCardImageElement -> "image"
    is CometChatCardIconElement -> "icon"
    is CometChatCardAvatarElement -> "avatar"
    is CometChatCardBadgeElement -> "badge"
    is CometChatCardDividerElement -> "divider"
    is CometChatCardSpacerElement -> "spacer"
    is CometChatCardChipElement -> "chip"
    is CometChatCardProgressBarElement -> "progressBar"
    is CometChatCardCodeBlockElement -> "codeBlock"
    is CometChatCardMarkdownElement -> "markdown"
    is CometChatCardRowElement -> "row"
    is CometChatCardColumnElement -> "column"
    is CometChatCardGridElement -> "grid"
    is CometChatCardAccordionElement -> "accordion"
    is CometChatCardTabsElement -> "tabs"
    is CometChatCardButtonElement -> "button"
    is CometChatCardIconButtonElement -> "iconButton"
    is CometChatCardLinkElement -> "link"
    is CometChatCardTableElement -> "table"
}
