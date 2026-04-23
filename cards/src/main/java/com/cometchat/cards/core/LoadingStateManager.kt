package com.cometchat.cards.core

import androidx.compose.runtime.mutableStateMapOf

class CometChatCardLoadingStateManager {

    private val loadingStates = mutableStateMapOf<String, Boolean>()

    fun setLoading(elementId: String, loading: Boolean) {
        if (loading) {
            loadingStates[elementId] = true
        } else {
            loadingStates.remove(elementId)
        }
    }

    fun isLoading(elementId: String): Boolean {
        return loadingStates[elementId] == true
    }

    fun clearAll() {
        loadingStates.clear()
    }
}
