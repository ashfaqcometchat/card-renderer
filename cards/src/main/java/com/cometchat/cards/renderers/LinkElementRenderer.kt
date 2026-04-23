package com.cometchat.cards.renderers

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.cometchat.cards.actions.CometChatCardActionEmitter
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.CometChatCardElement
import com.cometchat.cards.models.CometChatCardLinkElement
import com.cometchat.cards.theme.CometChatCardThemeResolver

class LinkElementRenderer : CometChatCardElementRenderer {

    override fun renderView(context: Context, element: CometChatCardElement, renderContext: CometChatCardRenderContext): View {
        val el = element as CometChatCardLinkElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val linkColor = CometChatCardThemeResolver.resolveColor(el.color, mode, theme.linkColor)
        val fontSize = el.fontSize ?: 14
        val underline = el.underline ?: true

        return TextView(context).apply {
            text = el.text
            setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
            linkColor?.let { runCatching { setTextColor(Color.parseColor(it)) } }
            if (underline) paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
            setOnClickListener {
                CometChatCardActionEmitter.emit(el.action, el.id, renderContext.cardJson, renderContext.onAction)
            }
            contentDescription = el.text
        }
    }

    @Composable
    override fun RenderComposable(element: CometChatCardElement, renderContext: CometChatCardRenderContext) {
        val el = element as CometChatCardLinkElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val linkColor = CometChatCardThemeResolver.resolveColor(el.color, mode, theme.linkColor)
        val fontSize = el.fontSize ?: 14
        val underline = el.underline ?: true

        Text(
            text = el.text,
            fontSize = fontSize.sp,
            color = linkColor?.let { parseComposeColor(it) } ?: androidx.compose.ui.graphics.Color.Blue,
            textDecoration = if (underline) TextDecoration.Underline else TextDecoration.None,
            modifier = Modifier.clickable {
                CometChatCardActionEmitter.emit(el.action, el.id, renderContext.cardJson, renderContext.onAction)
            }
        )
    }
}
