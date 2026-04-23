package com.cometchat.cards.renderers

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.load
import coil3.compose.AsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.*
import com.cometchat.cards.theme.CometChatCardThemeResolver

class ImageElementRenderer : CometChatCardElementRenderer {

    override fun renderView(context: Context, element: CometChatCardElement, renderContext: CometChatCardRenderContext): View {
        val el = element as CometChatCardImageElement
        val mode = renderContext.effectiveThemeMode
        val url = CometChatCardThemeResolver.resolveUrl(el.url, mode)
        val density = context.resources.displayMetrics.density
        val heightPx = resolveSize(el.height, 200, density)
        val widthPx = resolveSize(el.width, null, density)
        val borderRadius = el.borderRadius ?: 0

        val container = FrameLayout(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                widthPx ?: FrameLayout.LayoutParams.MATCH_PARENT,
                heightPx ?: (200 * density).toInt()
            )
        }

        val imageView = ImageView(context).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
            scaleType = when (el.fit) {
                "contain" -> ImageView.ScaleType.FIT_CENTER
                "fill" -> ImageView.ScaleType.FIT_XY
                else -> ImageView.ScaleType.CENTER_CROP
            }
            el.altText?.let { contentDescription = it }
                ?: run { importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_NO }
        }

        container.addView(imageView)

        if (url != null) {
            imageView.load(url) { crossfade(true) }
        }

        applyPadding(container, el.padding)
        return container
    }

    @Composable
    override fun RenderComposable(element: CometChatCardElement, renderContext: CometChatCardRenderContext) {
        val el = element as CometChatCardImageElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val url = CometChatCardThemeResolver.resolveUrl(el.url, mode)
        val borderRadius = el.borderRadius ?: 0
        val shape = RoundedCornerShape(borderRadius.dp)

        val heightDp = when (val h = el.height) {
            is CometChatCardDimension.Dp -> h.value.dp
            else -> 200.dp
        }

        val contentScale = when (el.fit) {
            "contain" -> ContentScale.Fit
            "fill" -> ContentScale.FillBounds
            else -> ContentScale.Crop
        }

        var modifier = composePadding(el.padding)
            .fillMaxWidth()
            .height(heightDp)
            .clip(shape)

        if (el.altText != null) {
            modifier = modifier.semantics { contentDescription = el.altText }
        }

        var isLoading by remember { mutableStateOf(true) }

        Box(modifier = modifier) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .crossfade(true)
                    .build(),
                contentDescription = el.altText,
                contentScale = contentScale,
                modifier = Modifier.fillMaxSize(),
                onState = { state ->
                    isLoading = state is AsyncImagePainter.State.Loading
                }
            )

            if (isLoading) {
                ShimmerBox(
                    modifier = Modifier.fillMaxSize(),
                    baseColor = parseComposeColor(
                        CometChatCardThemeResolver.resolveColor(null, mode, theme.shimmerBase) ?: "#E0E0E0"
                    ),
                    highlightColor = parseComposeColor(
                        CometChatCardThemeResolver.resolveColor(null, mode, theme.shimmerHighlight) ?: "#F5F5F5"
                    )
                )
            }
        }
    }

    private fun resolveSize(dim: CometChatCardDimension?, defaultDp: Int?, density: Float): Int? {
        return when (dim) {
            is CometChatCardDimension.Dp -> (dim.value * density).toInt()
            is CometChatCardDimension.Percent -> null // handled by parent
            is CometChatCardDimension.Auto -> null
            null -> defaultDp?.let { (it * density).toInt() }
        }
    }
}

@Composable
internal fun ShimmerBox(
    modifier: Modifier = Modifier,
    baseColor: androidx.compose.ui.graphics.Color,
    highlightColor: androidx.compose.ui.graphics.Color
) {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmerTranslate"
    )

    val brush = Brush.linearGradient(
        colors = listOf(baseColor, highlightColor, baseColor),
        start = Offset(translateAnim - 500f, 0f),
        end = Offset(translateAnim, 0f)
    )

    Box(modifier = modifier.background(brush))
}
