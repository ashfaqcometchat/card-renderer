package com.cometchat.cards.core

class CometChatCardElementRegistry {

    private val renderers = mutableMapOf<String, CometChatCardElementRenderer>()

    fun register(type: String, renderer: CometChatCardElementRenderer) {
        renderers[type] = renderer
    }

    fun getRenderer(type: String): CometChatCardElementRenderer? {
        return renderers[type]
    }

    fun registeredTypes(): Set<String> = renderers.keys.toSet()
}
