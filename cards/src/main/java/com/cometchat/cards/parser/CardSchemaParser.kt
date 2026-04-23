package com.cometchat.cards.parser

import com.cometchat.cards.models.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

object CometChatCardSchemaParser {

    private val serializersModule = SerializersModule {
        polymorphic(CometChatCardElement::class) {
            subclass(CometChatCardTextElement::class)
            subclass(CometChatCardImageElement::class)
            subclass(CometChatCardIconElement::class)
            subclass(CometChatCardAvatarElement::class)
            subclass(CometChatCardBadgeElement::class)
            subclass(CometChatCardDividerElement::class)
            subclass(CometChatCardSpacerElement::class)
            subclass(CometChatCardChipElement::class)
            subclass(CometChatCardProgressBarElement::class)
            subclass(CometChatCardCodeBlockElement::class)
            subclass(CometChatCardMarkdownElement::class)
            subclass(CometChatCardRowElement::class)
            subclass(CometChatCardColumnElement::class)
            subclass(CometChatCardGridElement::class)
            subclass(CometChatCardAccordionElement::class)
            subclass(CometChatCardTabsElement::class)
            subclass(CometChatCardButtonElement::class)
            subclass(CometChatCardIconButtonElement::class)
            subclass(CometChatCardLinkElement::class)
            subclass(CometChatCardTableElement::class)
        }
        polymorphic(CometChatCardAction::class) {
            subclass(CometChatCardOpenUrlAction::class)
            subclass(CometChatCardCopyToClipboardAction::class)
            subclass(CometChatCardDownloadFileAction::class)
            subclass(CometChatCardApiCallAction::class)
            subclass(CometChatCardChatWithUserAction::class)
            subclass(CometChatCardChatWithGroupAction::class)
            subclass(CometChatCardSendMessageAction::class)
            subclass(CometChatCardInitiateCallAction::class)
            subclass(CometChatCardCustomCallbackAction::class)
        }
    }

    val json = Json {
        serializersModule = this@CometChatCardSchemaParser.serializersModule
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = false
        classDiscriminator = "type"
    }

    fun parse(jsonString: String): Result<CometChatCardSchema> {
        return try {
            val schema = json.decodeFromString<CometChatCardSchema>(jsonString)
            Result.success(schema)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun serialize(schema: CometChatCardSchema): String {
        return json.encodeToString(CometChatCardSchema.serializer(), schema)
    }
}
