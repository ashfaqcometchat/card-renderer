package com.cometchat.cards.actions

import com.cometchat.cards.models.CometChatCardAction

data class CometChatCardActionEvent(
    val action: CometChatCardAction,
    val elementId: String,
    val cardJson: String
)

fun interface CometChatCardActionCallback {
    fun onAction(event: CometChatCardActionEvent)
}
