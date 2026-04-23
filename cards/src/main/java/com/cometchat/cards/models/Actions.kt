package com.cometchat.cards.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
sealed interface CometChatCardAction

@Serializable
@SerialName("openUrl")
data class CometChatCardOpenUrlAction(
    val url: String,
    val openIn: String? = null
) : CometChatCardAction

@Serializable
@SerialName("copyToClipboard")
data class CometChatCardCopyToClipboardAction(
    val value: String
) : CometChatCardAction

@Serializable
@SerialName("downloadFile")
data class CometChatCardDownloadFileAction(
    val url: String,
    val filename: String? = null
) : CometChatCardAction

@Serializable
@SerialName("apiCall")
data class CometChatCardApiCallAction(
    val url: String,
    val method: String? = "POST",
    val headers: Map<String, String>? = null,
    val body: JsonElement? = null
) : CometChatCardAction

@Serializable
@SerialName("chatWithUser")
data class CometChatCardChatWithUserAction(
    val uid: String
) : CometChatCardAction

@Serializable
@SerialName("chatWithGroup")
data class CometChatCardChatWithGroupAction(
    val guid: String
) : CometChatCardAction

@Serializable
@SerialName("sendMessage")
data class CometChatCardSendMessageAction(
    val text: String,
    val receiverUid: String? = null,
    val receiverGuid: String? = null
) : CometChatCardAction

@Serializable
@SerialName("initiateCall")
data class CometChatCardInitiateCallAction(
    val callType: String,
    val uid: String? = null,
    val guid: String? = null
) : CometChatCardAction

@Serializable
@SerialName("customCallback")
data class CometChatCardCustomCallbackAction(
    val callbackId: String,
    val payload: JsonElement? = null
) : CometChatCardAction

val CometChatCardAction.type: String get() = when (this) {
    is CometChatCardOpenUrlAction -> "openUrl"
    is CometChatCardCopyToClipboardAction -> "copyToClipboard"
    is CometChatCardDownloadFileAction -> "downloadFile"
    is CometChatCardApiCallAction -> "apiCall"
    is CometChatCardChatWithUserAction -> "chatWithUser"
    is CometChatCardChatWithGroupAction -> "chatWithGroup"
    is CometChatCardSendMessageAction -> "sendMessage"
    is CometChatCardInitiateCallAction -> "initiateCall"
    is CometChatCardCustomCallbackAction -> "customCallback"
}
