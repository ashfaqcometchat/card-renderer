package com.cometchat.cards.renderers

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.CometChatCardChipElement
import com.cometchat.cards.models.CometChatCardElement
import com.cometchat.cards.theme.CometChatCardThemeResolver

class ChipElementRenderer : CometChatCardElementRenderer {

    override fun renderView(context: Context, element: CometChatCardElement, renderContext: CometChatCardRenderContext): View {
        val el = element as CometChatCardChipElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val density = context.resources.displayMetrics.density
        val borderRadius = el.borderRadius ?: 14
        val fontSize = el.fontSize ?: 13

        val bgColor = CometChatCardThemeResolver.resolveColor(el.backgroundColor, mode, theme.chipFilledBg)
        val textColor = CometChatCardThemeResolver.resolveColor(el.color, mode, theme.chipFilledText)
        val borderColor = CometChatCardThemeResolver.resolveColor(el.borderColor, mode)

        val row = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            background = GradientDrawable().apply {
                cornerRadius = borderRadius * density
                bgColor?.let { runCatching { setColor(Color.parseColor(it)) } }
                if (borderColor != null && el.borderWidth != null) {
                    runCatching { setStroke((el.borderWidth * density).toInt(), Color.parseColor(borderColor)) }
                }
            }
            applyPadding(this, el.padding)
        }

        val iconUrl = CometChatCardThemeResolver.resolveUrl(el.icon, mode)
        if (iconUrl != null) {
            val iconView = ImageView(context).apply {
                layoutParams = LinearLayout.LayoutParams((16 * density).toInt(), (16 * density).toInt()).apply {
                    marginEnd = (4 * density).toInt()
                }
                scaleType = ImageView.ScaleType.FIT_CENTER
            }
            row.addView(iconView)
            coil3.SingletonImageLoader.get(context).enqueue(
                coil3.request.ImageRequest.Builder(context).data(iconUrl).target(iconView).build()
            )
        }

        row.addView(TextView(context).apply {
            text = el.text
            setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
            textColor?.let { runCatching { setTextColor(Color.parseColor(it)) } }
        })

        row.contentDescription = el.text
        return row
    }

    @Composable
    override fun RenderComposable(element: CometChatCardElement, renderContext: CometChatCardRenderContext) {
        val el = element as CometChatCardChipElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val borderRadius = el.borderRadius ?: 14
        val fontSize = el.fontSize ?: 13
        val shape = RoundedCornerShape(borderRadius.dp)

        val bgColor = CometChatCardThemeResolver.resolveColor(el.backgroundColor, mode, theme.chipFilledBg)
        val textColor = CometChatCardThemeResolver.resolveColor(el.color, mode, theme.chipFilledText)
        val borderColor = CometChatCardThemeResolver.resolveColor(el.borderColor, mode)
        val iconUrl = CometChatCardThemeResolver.resolveUrl(el.icon, mode)

        var modifier = composePadding(el.padding).clip(shape)
        bgColor?.let { modifier = modifier.background(parseComposeColor(it), shape) }
        if (borderColor != null && el.borderWidth != null) {
            modifier = modifier.border(el.borderWidth.dp, parseComposeColor(borderColor), shape)
        }

        Row(verticalAlignment = Alignment.CenterVertically, modifier = modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
            if (iconUrl != null) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(iconUrl).build(),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp).padding(end = 4.dp)
                )
            }
            Text(
                text = el.text,
                fontSize = fontSize.sp,
                color = textColor?.let { parseComposeColor(it) } ?: androidx.compose.ui.graphics.Color.Unspecified
            )
        }
    }
}
