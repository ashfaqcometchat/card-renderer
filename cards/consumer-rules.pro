# Keep Kotlinx Serialization classes
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep CometChat Cards model classes for serialization
-keep,includedescriptorclasses class com.cometchat.cards.models.**$$serializer { *; }
-keepclassmembers class com.cometchat.cards.models.** {
    *** Companion;
}
-keepclasseswithmembers class com.cometchat.cards.models.** {
    kotlinx.serialization.KSerializer serializer(...);
}
