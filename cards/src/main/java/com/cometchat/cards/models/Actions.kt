package com.cometchat.cards.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
sealed interface CometChatCardAction {
    val type: String
}

@Serializable
@SerialName("openUrl")
data class CometChatCardOpenUrlAction(
    override val type: String = "openUrl",
    val url: String,
    val openIn: String? = null
) : CometChatCardAction

@Serializable
@SerialName("copyToClipboard")
data class CometChatCardCopyToClipboardAction(
    override val type: String = "copyToClipboard",
    val value: String
) : CometChatCardAction

@Serializable
@SerialName("downloadFile")
data class CometChatCardDownloadFileAction(
    override val type: String = "downloadFile",
    val url: String,
    val filename: String? = null
) : CometChatCardAction

@Serializable
@SerialName("apiCall")
data class CometChatCardApiCallAction(
    override val type: String = "apiCall",
    val url: String,
    val method: String? = "POST",
    val headers: Map<String, String>? = null,
    val body: JsonElement? = null
) : CometChatCardAction

@Serializable
@SerialName("chatWithUser")
data class CometChatCardChatWithUserAction(
    override val type: String = "chatWithUser",
    val uid: String
) : CometChatCardAction

@Serializable
@SerialName("chatWithGroup")
data class CometChatCardChatWithGroupAction(
    override val type: String = "chatWithGroup",
    val guid: String
) : CometChatCardAction

@Serializable
@SerialName("sendMessage")
data class CometChatCardSendMessageAction(
    override val type: String = "sendMessage",
    val text: String,
    val receiverUid: String? = null,
    val receiverGuid: String? = null
) : CometChatCardAction

@Serializable
@SerialName("initiateCall")
data class CometChatCardInitiateCallAction(
    override val type: String = "initiateCall",
    val callType: String,
    val uid: String? = null,
    val guid: String? = null
) : CometChatCardAction

@Serializable
@SerialName("customCallback")
data class CometChatCardCustomCallbackAction(
    override val type: String = "customCallback",
    val callbackId: String,
    val payload: JsonElement? = null
) : CometChatCardAction
