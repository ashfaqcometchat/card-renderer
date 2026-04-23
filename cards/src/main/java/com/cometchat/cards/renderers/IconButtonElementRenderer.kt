package com.cometchat.cards.renderers

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.cometchat.cards.actions.CometChatCardActionEmitter
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.CometChatCardElement
import com.cometchat.cards.models.CometChatCardIconButtonElement
import com.cometchat.cards.theme.CometChatCardThemeResolver

class IconButtonElementRenderer : CometChatCardElementRenderer {

    override fun renderView(context: Context, element: CometChatCardElement, renderContext: CometChatCardRenderContext): View {
        val el = element as CometChatCardIconButtonElement
        val mode = renderContext.effectiveThemeMode
        val density = context.resources.displayMetrics.density
        val size = el.size ?: 24
        val sizePx = (size * density).toInt()
        val borderRadius = el.borderRadius ?: 0
        val isLoading = renderContext.loadingStateManager.isLoading(el.id)

        val bgColor = CometChatCardThemeResolver.resolveColor(el.backgroundColor, mode)
        val tintColor = CometChatCardThemeResolver.resolveColor(el.color, mode)
        val iconUrl = CometChatCardThemeResolver.resolveUrl(el.icon, mode)

        val container = FrameLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(sizePx, sizePx)
            if (bgColor != null) {
                background = GradientDrawable().apply {
                    cornerRadius = borderRadius * density
                    runCatching { setColor(Color.parseColor(bgColor)) }
                }
            }
            setOnClickListener {
                if (!isLoading) {
                    CometChatCardActionEmitter.emit(el.action, el.id, renderContext.cardJson, renderContext.onAction)
                }
            }
            contentDescription = "Icon button"
        }

        if (isLoading) {
            container.addView(ProgressBar(context).apply {
                layoutParams = FrameLayout.LayoutParams((size * 0.6 * density).toInt(), (size * 0.6 * density).toInt(), android.view.Gravity.CENTER)
            })
        } else if (iconUrl != null) {
            val imageView = ImageView(context).apply {
                layoutParams = FrameLayout.LayoutParams(sizePx, sizePx)
                scaleType = ImageView.ScaleType.FIT_CENTER
                tintColor?.let { runCatching { setColorFilter(Color.parseColor(it), PorterDuff.Mode.SRC_IN) } }
            }
            container.addView(imageView)
            coil3.SingletonImageLoader.get(context).enqueue(
                coil3.request.ImageRequest.Builder(context).data(iconUrl).target(imageView).build()
            )
        }

        return container
    }

    @Composable
    override fun RenderComposable(element: CometChatCardElement, renderContext: CometChatCardRenderContext) {
        val el = element as CometChatCardIconButtonElement
        val mode = renderContext.effectiveThemeMode
        val size = el.size ?: 24
        val borderRadius = el.borderRadius ?: 0
        val isLoading = renderContext.loadingStateManager.isLoading(el.id)
        val shape = RoundedCornerShape(borderRadius.dp)

        val bgColor = CometChatCardThemeResolver.resolveColor(el.backgroundColor, mode)
        val tintColor = CometChatCardThemeResolver.resolveColor(el.color, mode)
        val iconUrl = CometChatCardThemeResolver.resolveUrl(el.icon, mode)

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(size.dp)
                .clip(shape)
                .then(bgColor?.let { Modifier.background(parseComposeColor(it)) } ?: Modifier)
                .clickable(enabled = !isLoading) {
                    CometChatCardActionEmitter.emit(el.action, el.id, renderContext.cardJson, renderContext.onAction)
                }
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size((size * 0.6f).dp), strokeWidth = 2.dp)
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(iconUrl).build(),
                    contentDescription = "Icon button",
                    contentScale = ContentScale.Fit,
                    colorFilter = tintColor?.let { ColorFilter.tint(parseComposeColor(it)) },
                    modifier = Modifier.size(size.dp)
                )
            }
        }
    }
}
