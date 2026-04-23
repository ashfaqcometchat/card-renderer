package com.cometchat.cards.actions

import com.cometchat.cards.models.*

object CometChatCardActionEmitter {

    fun emit(
        action: CometChatCardAction?,
        elementId: String,
        cardJson: String,
        callback: CometChatCardActionCallback?,
        isDisabled: Boolean = false
    ): Boolean {
        if (isDisabled) return false
        if (action == null) return false
        if (callback == null) return false
        if (!isValidAction(action)) return false

        callback.onAction(CometChatCardActionEvent(action, elementId, cardJson))
        return true
    }

    fun isValidAction(action: CometChatCardAction): Boolean {
        return when (action) {
            is CometChatCardOpenUrlAction -> action.url.isNotBlank()
            is CometChatCardCopyToClipboardAction -> action.value.isNotBlank()
            is CometChatCardDownloadFileAction -> action.url.isNotBlank()
            is CometChatCardApiCallAction -> action.url.isNotBlank()
            is CometChatCardChatWithUserAction -> action.uid.isNotBlank()
            is CometChatCardChatWithGroupAction -> action.guid.isNotBlank()
            is CometChatCardSendMessageAction -> action.text.isNotBlank()
            is CometChatCardInitiateCallAction -> {
                action.callType in listOf("audio", "video") &&
                    (action.uid?.isNotBlank() == true || action.guid?.isNotBlank() == true)
            }
            is CometChatCardCustomCallbackAction -> action.callbackId.isNotBlank()
        }
    }
}
