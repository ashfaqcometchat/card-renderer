package com.cometchat.cards.renderers

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.load
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.CometChatCardElement
import com.cometchat.cards.models.CometChatCardIconElement
import com.cometchat.cards.theme.CometChatCardThemeResolver

class IconElementRenderer : CometChatCardElementRenderer {

    override fun renderView(context: Context, element: CometChatCardElement, renderContext: CometChatCardRenderContext): View {
        val el = element as CometChatCardIconElement
        val mode = renderContext.effectiveThemeMode
        val url = CometChatCardThemeResolver.resolveUrl(el.name, mode)
        val size = el.size ?: 24
        val density = context.resources.displayMetrics.density
        val sizePx = (size * density).toInt()
        val borderRadius = el.borderRadius ?: 0
        val padding = el.padding ?: 0

        val container = FrameLayout(context).apply {
            val bgColor = CometChatCardThemeResolver.resolveColor(el.backgroundColor, mode)
            if (bgColor != null) {
                background = GradientDrawable().apply {
                    cornerRadius = borderRadius * density
                    runCatching { setColor(Color.parseColor(bgColor)) }
                }
            }
            setPadding((padding * density).toInt(), (padding * density).toInt(), (padding * density).toInt(), (padding * density).toInt())
        }

        val imageView = ImageView(context).apply {
            layoutParams = FrameLayout.LayoutParams(sizePx, sizePx)
            scaleType = ImageView.ScaleType.FIT_CENTER
            val tintColor = CometChatCardThemeResolver.resolveColor(el.color, mode)
            tintColor?.let { runCatching { setColorFilter(Color.parseColor(it), PorterDuff.Mode.SRC_IN) } }
        }

        container.addView(imageView)

        if (url != null) {
            imageView.load(url) { crossfade(true) }
        }

        return container
    }

    @Composable
    override fun RenderComposable(element: CometChatCardElement, renderContext: CometChatCardRenderContext) {
        val el = element as CometChatCardIconElement
        val mode = renderContext.effectiveThemeMode
        val url = CometChatCardThemeResolver.resolveUrl(el.name, mode)
        val size = el.size ?: 24
        val borderRadius = el.borderRadius ?: 0
        val padding = el.padding ?: 0
        val bgColor = CometChatCardThemeResolver.resolveColor(el.backgroundColor, mode)
        val tintColor = CometChatCardThemeResolver.resolveColor(el.color, mode)

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(borderRadius.dp))
                .then(if (bgColor != null) Modifier.background(parseComposeColor(bgColor)) else Modifier)
                .padding(padding.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(url).crossfade(true).build(),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                colorFilter = tintColor?.let { ColorFilter.tint(parseComposeColor(it)) },
                modifier = Modifier.size(size.dp)
            )
        }
    }
}
