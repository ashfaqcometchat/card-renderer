package com.cometchat.cards.renderers

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.CometChatCardBadgeElement
import com.cometchat.cards.models.CometChatCardElement
import com.cometchat.cards.theme.CometChatCardThemeResolver

class BadgeElementRenderer : CometChatCardElementRenderer {

    override fun renderView(context: Context, element: CometChatCardElement, renderContext: CometChatCardRenderContext): View {
        val el = element as CometChatCardBadgeElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val density = context.resources.displayMetrics.density

        val bgColor = CometChatCardThemeResolver.resolveColor(el.backgroundColor, mode, theme.badgeBg)
        val textColor = CometChatCardThemeResolver.resolveColor(el.color, mode, theme.badgeText)
        val borderColor = CometChatCardThemeResolver.resolveColor(el.borderColor, mode)
        val borderRadius = el.borderRadius ?: 10
        val fontSize = el.fontSize ?: 12

        return TextView(context).apply {
            text = el.text
            setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
            textColor?.let { runCatching { setTextColor(Color.parseColor(it)) } }
            gravity = Gravity.CENTER

            background = GradientDrawable().apply {
                cornerRadius = borderRadius * density
                bgColor?.let { runCatching { setColor(Color.parseColor(it)) } }
                if (borderColor != null && el.borderWidth != null) {
                    runCatching { setStroke((el.borderWidth * density).toInt(), Color.parseColor(borderColor)) }
                }
            }

            applyPadding(this, el.padding)
            contentDescription = el.text
        }
    }

    @Composable
    override fun RenderComposable(element: CometChatCardElement, renderContext: CometChatCardRenderContext) {
        val el = element as CometChatCardBadgeElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme

        val bgColor = CometChatCardThemeResolver.resolveColor(el.backgroundColor, mode, theme.badgeBg)
        val textColor = CometChatCardThemeResolver.resolveColor(el.color, mode, theme.badgeText)
        val borderColor = CometChatCardThemeResolver.resolveColor(el.borderColor, mode)
        val borderRadius = el.borderRadius ?: 10
        val fontSize = el.fontSize ?: 12
        val shape = RoundedCornerShape(borderRadius.dp)

        var modifier = composePadding(el.padding)
            .clip(shape)

        bgColor?.let { modifier = modifier.background(parseComposeColor(it), shape) }
        if (borderColor != null && el.borderWidth != null) {
            modifier = modifier.border(el.borderWidth.dp, parseComposeColor(borderColor), shape)
        }

        Text(
            text = el.text,
            fontSize = fontSize.sp,
            color = textColor?.let { parseComposeColor(it) } ?: androidx.compose.ui.graphics.Color.White,
            modifier = modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

internal fun parseComposeColor(hex: String): androidx.compose.ui.graphics.Color {
    return runCatching { androidx.compose.ui.graphics.Color(Color.parseColor(hex)) }
        .getOrDefault(androidx.compose.ui.graphics.Color.Transparent)
}
