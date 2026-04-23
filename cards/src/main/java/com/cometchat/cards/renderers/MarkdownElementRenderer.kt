package com.cometchat.cards.renderers

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.cometchat.cards.actions.CometChatCardActionEmitter
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.CometChatCardElement
import com.cometchat.cards.models.CometChatCardMarkdownElement
import com.cometchat.cards.models.CometChatCardOpenUrlAction
import com.cometchat.cards.theme.CometChatCardThemeResolver

class MarkdownElementRenderer : CometChatCardElementRenderer {

    override fun renderView(context: Context, element: CometChatCardElement, renderContext: CometChatCardRenderContext): View {
        val el = element as CometChatCardMarkdownElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val textColor = CometChatCardThemeResolver.resolveColor(el.color, mode, theme.textColor)
        val linkColor = CometChatCardThemeResolver.resolveColor(el.linkColor, mode, theme.linkColor)
        val fontSize = el.baseFontSize ?: 16

        val html = markdownToHtml(el.content)
        val spanned: Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT)
        } else {
            @Suppress("DEPRECATION")
            Html.fromHtml(html)
        }

        return TextView(context).apply {
            text = spanned
            setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
            textColor?.let { runCatching { setTextColor(Color.parseColor(it)) } }
            linkColor?.let { runCatching { setLinkTextColor(Color.parseColor(it)) } }
            el.lineHeight?.let {
                setLineSpacing(0f, it)
            }
            movementMethod = LinkMovementMethod.getInstance()

            // Intercept link clicks to emit openUrl action
            post {
                val spannable = text as? android.text.Spannable ?: return@post
                val urlSpans = spannable.getSpans(0, spannable.length, URLSpan::class.java)
                for (span in urlSpans) {
                    val start = spannable.getSpanStart(span)
                    val end = spannable.getSpanEnd(span)
                    val flags = spannable.getSpanFlags(span)
                    spannable.removeSpan(span)
                    spannable.setSpan(object : URLSpan(span.url) {
                        override fun onClick(widget: View) {
                            CometChatCardActionEmitter.emit(
                                action = CometChatCardOpenUrlAction(url = url),
                                elementId = el.id,
                                cardJson = renderContext.cardJson,
                                callback = renderContext.onAction
                            )
                        }
                    }, start, end, flags)
                }
            }
        }
    }

    @Composable
    override fun RenderComposable(element: CometChatCardElement, renderContext: CometChatCardRenderContext) {
        val el = element as CometChatCardMarkdownElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val textColor = CometChatCardThemeResolver.resolveColor(el.color, mode, theme.textColor)
        val linkColor = CometChatCardThemeResolver.resolveColor(el.linkColor, mode, theme.linkColor)
        val fontSize = el.baseFontSize ?: 16

        val annotatedString = parseMarkdownToAnnotatedString(
            el.content,
            linkColor?.let { parseComposeColor(it) } ?: androidx.compose.ui.graphics.Color.Blue
        )

        ClickableText(
            text = annotatedString,
            style = TextStyle(
                fontSize = fontSize.sp,
                color = textColor?.let { parseComposeColor(it) } ?: androidx.compose.ui.graphics.Color.Unspecified,
                lineHeight = el.lineHeight?.let { (fontSize * it).sp } ?: TextStyle.Default.lineHeight
            ),
            onClick = { offset ->
                annotatedString.getStringAnnotations("URL", offset, offset).firstOrNull()?.let { annotation ->
                    CometChatCardActionEmitter.emit(
                        action = CometChatCardOpenUrlAction(url = annotation.item),
                        elementId = el.id,
                        cardJson = renderContext.cardJson,
                        callback = renderContext.onAction
                    )
                }
            }
        )
    }

    companion object {
        internal fun markdownToHtml(markdown: String): String {
            var html = markdown
            // Bold: **text** → <b>text</b>
            html = html.replace(Regex("\\*\\*(.+?)\\*\\*"), "<b>$1</b>")
            // Italic: *text* → <i>text</i>
            html = html.replace(Regex("(?<!\\*)\\*(?!\\*)(.+?)(?<!\\*)\\*(?!\\*)"), "<i>$1</i>")
            // Inline code: `code` → <code>code</code>
            html = html.replace(Regex("`(.+?)`"), "<code>$1</code>")
            // Links: [text](url) → <a href="url">text</a>
            html = html.replace(Regex("\\[(.+?)\\]\\((.+?)\\)"), "<a href=\"$2\">$1</a>")
            // Unordered lists: - item → <ul><li>item</li></ul>
            html = html.replace(Regex("(?m)^- (.+)$"), "<li>$1</li>")
            html = html.replace(Regex("(<li>.+</li>\\n?)+")) { "<ul>${it.value}</ul>" }
            // Ordered lists: 1. item → <ol><li>item</li></ol>
            html = html.replace(Regex("(?m)^\\d+\\. (.+)$"), "<li>$1</li>")
            // Line breaks
            html = html.replace("\n", "<br>")
            return html
        }

        internal fun parseMarkdownToAnnotatedString(
            markdown: String,
            linkColor: androidx.compose.ui.graphics.Color
        ): AnnotatedString {
            return buildAnnotatedString {
                var remaining = markdown
                val boldRegex = Regex("\\*\\*(.+?)\\*\\*")
                val italicRegex = Regex("(?<!\\*)\\*(?!\\*)(.+?)(?<!\\*)\\*(?!\\*)")
                val codeRegex = Regex("`(.+?)`")
                val linkRegex = Regex("\\[(.+?)\\]\\((.+?)\\)")

                // Simple sequential parsing
                var i = 0
                while (i < remaining.length) {
                    val boldMatch = boldRegex.find(remaining, i)
                    val italicMatch = italicRegex.find(remaining, i)
                    val codeMatch = codeRegex.find(remaining, i)
                    val linkMatch = linkRegex.find(remaining, i)

                    val matches = listOfNotNull(boldMatch, italicMatch, codeMatch, linkMatch)
                    val nearest = matches.minByOrNull { it.range.first }

                    if (nearest == null || nearest.range.first > remaining.length) {
                        append(remaining.substring(i))
                        break
                    }

                    if (nearest.range.first > i) {
                        append(remaining.substring(i, nearest.range.first))
                    }

                    when (nearest) {
                        boldMatch -> {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(nearest.groupValues[1])
                            }
                        }
                        italicMatch -> {
                            withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                                append(nearest.groupValues[1])
                            }
                        }
                        codeMatch -> {
                            withStyle(SpanStyle(fontFamily = FontFamily.Monospace, background = androidx.compose.ui.graphics.Color(0xFFF0F0F0))) {
                                append(nearest.groupValues[1])
                            }
                        }
                        linkMatch -> {
                            val text = nearest.groupValues[1]
                            val url = nearest.groupValues[2]
                            pushStringAnnotation("URL", url)
                            withStyle(SpanStyle(color = linkColor, textDecoration = TextDecoration.Underline)) {
                                append(text)
                            }
                            pop()
                        }
                    }

                    i = nearest.range.last + 1
                }
            }
        }
    }
}
