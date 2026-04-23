package com.cometchat.cards.renderers

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.CometChatCardCodeBlockElement
import com.cometchat.cards.models.CometChatCardElement
import com.cometchat.cards.theme.CometChatCardThemeResolver

class CodeBlockElementRenderer : CometChatCardElementRenderer {

    override fun renderView(context: Context, element: CometChatCardElement, renderContext: CometChatCardRenderContext): View {
        val el = element as CometChatCardCodeBlockElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val density = context.resources.displayMetrics.density
        val bgColor = CometChatCardThemeResolver.resolveColor(el.backgroundColor, mode, theme.codeBlockBg)
        val textColor = CometChatCardThemeResolver.resolveColor(el.textColor, mode, theme.codeBlockText)
        val borderRadius = el.borderRadius ?: 4
        val fontSize = el.fontSize ?: 13

        val container = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            background = GradientDrawable().apply {
                cornerRadius = borderRadius * density
                bgColor?.let { runCatching { setColor(Color.parseColor(it)) } }
            }
            applyPadding(this, el.padding)
        }

        if (el.language != null) {
            val langColor = CometChatCardThemeResolver.resolveColor(el.languageLabelColor, mode, theme.codeBlockLangLabel)
            container.addView(TextView(context).apply {
                text = el.language
                setTextSize(TypedValue.COMPLEX_UNIT_SP, (el.languageLabelFontSize ?: 10).toFloat())
                langColor?.let { runCatching { setTextColor(Color.parseColor(it)) } }
                setPadding(0, 0, 0, (4 * density).toInt())
            })
        }

        container.addView(TextView(context).apply {
            text = el.content
            typeface = Typeface.MONOSPACE
            setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
            textColor?.let { runCatching { setTextColor(Color.parseColor(it)) } }
        })

        return container
    }

    @Composable
    override fun RenderComposable(element: CometChatCardElement, renderContext: CometChatCardRenderContext) {
        val el = element as CometChatCardCodeBlockElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val bgColor = CometChatCardThemeResolver.resolveColor(el.backgroundColor, mode, theme.codeBlockBg)
        val textColor = CometChatCardThemeResolver.resolveColor(el.textColor, mode, theme.codeBlockText)
        val borderRadius = el.borderRadius ?: 4
        val fontSize = el.fontSize ?: 13
        val shape = RoundedCornerShape(borderRadius.dp)

        Column(
            modifier = composePadding(el.padding)
                .clip(shape)
                .background(bgColor?.let { parseComposeColor(it) } ?: androidx.compose.ui.graphics.Color(0xFFF5F5F5))
                .padding(8.dp)
        ) {
            if (el.language != null) {
                val langColor = CometChatCardThemeResolver.resolveColor(el.languageLabelColor, mode, theme.codeBlockLangLabel)
                Text(
                    text = el.language,
                    fontSize = (el.languageLabelFontSize ?: 10).sp,
                    color = langColor?.let { parseComposeColor(it) } ?: androidx.compose.ui.graphics.Color.Gray,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            Text(
                text = el.content,
                fontFamily = FontFamily.Monospace,
                fontSize = fontSize.sp,
                color = textColor?.let { parseComposeColor(it) } ?: androidx.compose.ui.graphics.Color.Unspecified,
                modifier = Modifier.horizontalScroll(rememberScrollState())
            )
        }
    }
}
