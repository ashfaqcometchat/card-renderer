package com.cometchat.cards.renderers

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.CometChatCardElement
import com.cometchat.cards.models.CometChatCardPadding
import com.cometchat.cards.models.CometChatCardTextElement
import com.cometchat.cards.theme.CometChatCardThemeResolver

class TextElementRenderer : CometChatCardElementRenderer {

    override fun renderView(
        context: Context,
        element: CometChatCardElement,
        renderContext: CometChatCardRenderContext
    ): View {
        val el = element as CometChatCardTextElement
        val theme = renderContext.resolvedTheme
        val mode = renderContext.effectiveThemeMode
        val typo = theme.typography[el.variant ?: "body"]

        return TextView(context).apply {
            text = el.content
            setTextSize(TypedValue.COMPLEX_UNIT_SP, (typo?.fontSize ?: 14).toFloat())

            val resolvedColor = CometChatCardThemeResolver.resolveColor(el.color, mode, theme.textColor)
            resolvedColor?.let { runCatching { setTextColor(Color.parseColor(it)) } }

            val weight = el.fontWeight ?: typo?.fontWeight ?: "regular"
            typeface = when (weight) {
                "bold" -> Typeface.DEFAULT_BOLD
                "medium" -> Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
                else -> Typeface.DEFAULT
            }

            gravity = when (el.align) {
                "center" -> Gravity.CENTER
                "right" -> Gravity.END
                else -> Gravity.START
            }

            el.maxLines?.let {
                maxLines = it
                ellipsize = TextUtils.TruncateAt.END
            }

            applyPadding(this, el.padding)
            contentDescription = el.content
        }
    }

    @Composable
    override fun RenderComposable(
        element: CometChatCardElement,
        renderContext: CometChatCardRenderContext
    ) {
        val el = element as CometChatCardTextElement
        val theme = renderContext.resolvedTheme
        val mode = renderContext.effectiveThemeMode
        val typo = theme.typography[el.variant ?: "body"]

        val resolvedColor = CometChatCardThemeResolver.resolveColor(el.color, mode, theme.textColor)
        val color = resolvedColor?.let { runCatching { androidx.compose.ui.graphics.Color(Color.parseColor(it)) }.getOrNull() }
            ?: androidx.compose.ui.graphics.Color.Unspecified

        val weight = el.fontWeight ?: typo?.fontWeight ?: "regular"
        val fontWeight = when (weight) {
            "bold" -> FontWeight.Bold
            "medium" -> FontWeight.Medium
            else -> FontWeight.Normal
        }

        val textAlign = when (el.align) {
            "center" -> TextAlign.Center
            "right" -> TextAlign.End
            else -> TextAlign.Start
        }

        Text(
            text = el.content,
            fontSize = (typo?.fontSize ?: 14).sp,
            color = color,
            fontWeight = fontWeight,
            textAlign = textAlign,
            maxLines = el.maxLines ?: Int.MAX_VALUE,
            overflow = if (el.maxLines != null) TextOverflow.Ellipsis else TextOverflow.Clip,
            modifier = composePadding(el.padding)
        )
    }
}

internal fun applyPadding(view: View, padding: CometChatCardPadding?) {
    if (padding == null) return
    val density = view.context.resources.displayMetrics.density
    when (padding) {
        is CometChatCardPadding.Uniform -> {
            val px = (padding.value * density).toInt()
            view.setPadding(px, px, px, px)
        }
        is CometChatCardPadding.PerSide -> {
            view.setPadding(
                (padding.left * density).toInt(),
                (padding.top * density).toInt(),
                (padding.right * density).toInt(),
                (padding.bottom * density).toInt()
            )
        }
    }
}

@Composable
internal fun composePadding(padding: CometChatCardPadding?): Modifier {
    if (padding == null) return Modifier
    return when (padding) {
        is CometChatCardPadding.Uniform -> Modifier.padding(padding.value.dp)
        is CometChatCardPadding.PerSide -> Modifier.padding(
            start = padding.left.dp,
            top = padding.top.dp,
            end = padding.right.dp,
            bottom = padding.bottom.dp
        )
    }
}
