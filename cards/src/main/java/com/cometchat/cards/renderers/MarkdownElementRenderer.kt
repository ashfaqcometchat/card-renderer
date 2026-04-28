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

        val resolvedColor = textColor?.let { parseComposeColor(it) }
            ?: androidx.compose.ui.graphics.Color(0xFF333333)

        androidx.compose.foundation.text.BasicText(
            text = annotatedString,
            style = TextStyle(
                fontSize = fontSize.sp,
                color = resolvedColor,
                lineHeight = el.lineHeight?.let { (fontSize * it).sp } ?: (fontSize * 1.5f).sp
            )
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
                val lines = markdown.split("\n")
                for ((lineIndex, rawLine) in lines.withIndex()) {
                    val trimmed = rawLine.trimStart()
                    // Convert bullet list markers
                    val line = when {
                        trimmed.startsWith("- ") -> "• ${trimmed.removePrefix("- ")}"
                        trimmed.matches(Regex("^\\d+\\. .+")) -> trimmed
                        else -> rawLine
                    }
                    // Parse inline formatting within this line
                    appendInlineFormatted(line, linkColor)
                    // Add newline between lines (not after last)
                    if (lineIndex < lines.size - 1) append("\n")
                }
            }
        }

        private fun AnnotatedString.Builder.appendInlineFormatted(
            text: String,
            linkColor: androidx.compose.ui.graphics.Color
        ) {
            // Combined regex for all inline patterns
            val inlineRegex = Regex(
                "\\*\\*(.+?)\\*\\*" +           // bold
                "|(?<!\\*)\\*(?!\\*)(.+?)(?<!\\*)\\*(?!\\*)" + // italic
                "|`(.+?)`" +                      // inline code
                "|\\[(.+?)\\]\\((.+?)\\)"         // link
            )

            var lastEnd = 0
            for (match in inlineRegex.findAll(text)) {
                // Append plain text before this match
                if (match.range.first > lastEnd) {
                    append(text.substring(lastEnd, match.range.first))
                }
                when {
                    // Bold: group 1
                    match.groupValues[1].isNotEmpty() -> {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(match.groupValues[1])
                        }
                    }
                    // Italic: group 2
                    match.groupValues[2].isNotEmpty() -> {
                        withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                            append(match.groupValues[2])
                        }
                    }
                    // Code: group 3
                    match.groupValues[3].isNotEmpty() -> {
                        withStyle(SpanStyle(
                            fontFamily = FontFamily.Monospace,
                            background = androidx.compose.ui.graphics.Color(0x20000000)
                        )) {
                            append(match.groupValues[3])
                        }
                    }
                    // Link: group 4 (text) + group 5 (url)
                    match.groupValues[4].isNotEmpty() -> {
                        pushStringAnnotation("URL", match.groupValues[5])
                        withStyle(SpanStyle(color = linkColor, textDecoration = TextDecoration.Underline)) {
                            append(match.groupValues[4])
                        }
                        pop()
                    }
                }
                lastEnd = match.range.last + 1
            }
            // Append remaining plain text
            if (lastEnd < text.length) {
                append(text.substring(lastEnd))
            }
        }
    }
}
