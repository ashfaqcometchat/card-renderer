package com.cometchat.cards.renderers

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.CometChatCardAvatarElement
import com.cometchat.cards.models.CometChatCardElement
import com.cometchat.cards.theme.CometChatCardThemeResolver

class AvatarElementRenderer : CometChatCardElementRenderer {

    override fun renderView(context: Context, element: CometChatCardElement, renderContext: CometChatCardRenderContext): View {
        val el = element as CometChatCardAvatarElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val size = el.size ?: 44
        val density = context.resources.displayMetrics.density
        val sizePx = (size * density).toInt()
        val borderRadius = el.borderRadius ?: (size / 2)
        val bgColor = CometChatCardThemeResolver.resolveColor(el.backgroundColor, mode, theme.avatarBg)

        val container = FrameLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(sizePx, sizePx)
            background = GradientDrawable().apply {
                cornerRadius = borderRadius * density
                bgColor?.let { runCatching { setColor(Color.parseColor(it)) } }
            }
        }

        if (el.imageUrl != null) {
            val imageView = ImageView(context).apply {
                layoutParams = FrameLayout.LayoutParams(sizePx, sizePx)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
            container.addView(imageView)
            val request = coil3.request.ImageRequest.Builder(context)
                .data(el.imageUrl).crossfade(true).target(imageView).build()
            coil3.SingletonImageLoader.get(context).enqueue(request)
        } else if (el.fallbackInitials != null) {
            val textView = TextView(context).apply {
                layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                text = el.fallbackInitials
                gravity = Gravity.CENTER
                setTextSize(TypedValue.COMPLEX_UNIT_SP, (el.fontSize ?: 16).toFloat())
                val textColor = CometChatCardThemeResolver.resolveColor(null, mode, theme.avatarText)
                textColor?.let { runCatching { setTextColor(Color.parseColor(it)) } }
                typeface = if (el.fontWeight == "bold") Typeface.DEFAULT_BOLD else Typeface.DEFAULT
            }
            container.addView(textView)
        }

        return container
    }

    @Composable
    override fun RenderComposable(element: CometChatCardElement, renderContext: CometChatCardRenderContext) {
        val el = element as CometChatCardAvatarElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val size = el.size ?: 44
        val borderRadius = el.borderRadius ?: (size / 2)
        val bgColor = CometChatCardThemeResolver.resolveColor(el.backgroundColor, mode, theme.avatarBg)
        val shape = RoundedCornerShape(borderRadius.dp)

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(size.dp)
                .clip(shape)
                .background(bgColor?.let { parseComposeColor(it) } ?: androidx.compose.ui.graphics.Color.Gray)
        ) {
            if (el.imageUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(el.imageUrl).crossfade(true).build(),
                    contentDescription = el.fallbackInitials ?: "Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(size.dp).clip(shape)
                )
            } else if (el.fallbackInitials != null) {
                val textColor = CometChatCardThemeResolver.resolveColor(null, mode, theme.avatarText)
                Text(
                    text = el.fallbackInitials,
                    fontSize = (el.fontSize ?: 16).sp,
                    fontWeight = if (el.fontWeight == "bold") FontWeight.Bold else FontWeight.Normal,
                    color = textColor?.let { parseComposeColor(it) } ?: androidx.compose.ui.graphics.Color.White
                )
            }
        }
    }
}
