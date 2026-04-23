package com.cometchat.cards.core

import android.util.Log
import com.cometchat.cards.models.CometChatCardLogLevel

object CometChatCardLogger {

    var logLevel: CometChatCardLogLevel = CometChatCardLogLevel.WARNING

    private const val TAG = "CometChatCards"

    fun error(message: String) {
        if (logLevel.ordinal >= CometChatCardLogLevel.ERROR.ordinal) {
            Log.e(TAG, "[CometChatCards] [ERROR] $message")
        }
    }

    fun warning(message: String) {
        if (logLevel.ordinal >= CometChatCardLogLevel.WARNING.ordinal) {
            Log.w(TAG, "[CometChatCards] [WARNING] $message")
        }
    }

    fun verbose(message: String) {
        if (logLevel.ordinal >= CometChatCardLogLevel.VERBOSE.ordinal) {
            Log.d(TAG, "[CometChatCards] [VERBOSE] $message")
        }
    }
}
