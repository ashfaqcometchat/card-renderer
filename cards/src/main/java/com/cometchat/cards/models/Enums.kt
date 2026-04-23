package com.cometchat.cards.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class CometChatCardThemeMode {
    @SerialName("auto") AUTO,
    @SerialName("light") LIGHT,
    @SerialName("dark") DARK
}

@Serializable
enum class CometChatCardLogLevel {
    @SerialName("none") NONE,
    @SerialName("error") ERROR,
    @SerialName("warning") WARNING,
    @SerialName("verbose") VERBOSE
}
