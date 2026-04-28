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
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
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
            el.lineHeight?.let { setLineSpacing(0f, it) }
            movementMethod = LinkMovementMethod.getInstance()

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
            el.content, fontSize,
            linkColor?.let { parseComposeColor(it) } ?: androidx.compose.ui.graphics.Color.Blue
        )

        val resolvedColor = textColor?.let { parseComposeColor(it) }
            ?: androidx.compose.ui.graphics.Color(0xFF333333)

        BasicText(
            text = annotatedString,
            style = TextStyle(
                fontSize = fontSize.sp,
                color = resolvedColor,
                lineHeight = el.lineHeight?.let { (fontSize * it).sp } ?: (fontSize * 1.5f).sp
            )
        )
    }

    companion object {

        // ── View path: markdown → HTML ──

        internal fun markdownToHtml(markdown: String): String {
            var html = markdown

            // Code blocks: ```...``` → <pre><code>...</code></pre> (must be first to protect content)
            html = html.replace(Regex("```[\\w]*\\n([\\s\\S]*?)```")) { match ->
                "<pre><code>${match.groupValues[1].trim()}</code></pre>"
            }

            // Headings: # H1 through ###### H6
            html = html.replace(Regex("(?m)^#{6} (.+)$"), "<h6>$1</h6>")
            html = html.replace(Regex("(?m)^#{5} (.+)$"), "<h5>$1</h5>")
            html = html.replace(Regex("(?m)^#{4} (.+)$"), "<h4>$1</h4>")
            html = html.replace(Regex("(?m)^#{3} (.+)$"), "<h3>$1</h3>")
            html = html.replace(Regex("(?m)^#{2} (.+)$"), "<h2>$1</h2>")
            html = html.replace(Regex("(?m)^# (.+)$"), "<h1>$1</h1>")

            // Horizontal rules: --- or *** or ___ (standalone line)
            html = html.replace(Regex("(?m)^(---+|\\*\\*\\*+|___+)\\s*$"), "<hr>")

            // Block quotes: > text
            html = html.replace(Regex("(?m)^> (.+)$"), "<blockquote>$1</blockquote>")

            // Strikethrough: ~~text~~
            html = html.replace(Regex("~~(.+?)~~"), "<s>$1</s>")

            // Bold: **text**
            html = html.replace(Regex("\\*\\*(.+?)\\*\\*"), "<b>$1</b>")
            // Italic: *text*
            html = html.replace(Regex("(?<!\\*)\\*(?!\\*)(.+?)(?<!\\*)\\*(?!\\*)"), "<i>$1</i>")
            // Inline code: `code`
            html = html.replace(Regex("`(.+?)`"), "<code>$1</code>")
            // Links: [text](url)
            html = html.replace(Regex("\\[(.+?)\\]\\((.+?)\\)"), "<a href=\"$2\">$1</a>")

            // Unordered lists: - item
            html = html.replace(Regex("(?m)^- (.+)$"), "<li>$1</li>")
            html = html.replace(Regex("(<li>.+</li>\\n?)+")) { "<ul>${it.value}</ul>" }
            // Ordered lists: 1. item
            html = html.replace(Regex("(?m)^\\d+\\. (.+)$"), "<li>$1</li>")

            // Line breaks
            html = html.replace("\n", "<br>")
            return html
        }

        // ── Compose path: markdown → AnnotatedString ──

        internal fun parseMarkdownToAnnotatedString(
            markdown: String,
            baseFontSize: Int,
            linkColor: androidx.compose.ui.graphics.Color
        ): AnnotatedString {
            return buildAnnotatedString {
                // First, extract code blocks and replace with placeholders
                val codeBlockRegex = Regex("```[\\w]*\\n([\\s\\S]*?)```")
                val codeBlocks = mutableListOf<String>()
                val withPlaceholders = codeBlockRegex.replace(markdown) { match ->
                    codeBlocks.add(match.groupValues[1].trim())
                    "\u0000CODEBLOCK_${codeBlocks.size - 1}\u0000"
                }

                val lines = withPlaceholders.split("\n")
                for ((lineIndex, rawLine) in lines.withIndex()) {
                    val trimmed = rawLine.trimStart()

                    when {
                        // Code block placeholder
                        trimmed.startsWith("\u0000CODEBLOCK_") -> {
                            val idx = trimmed.removePrefix("\u0000CODEBLOCK_").removeSuffix("\u0000").toIntOrNull()
                            if (idx != null && idx < codeBlocks.size) {
                                withStyle(SpanStyle(
                                    fontFamily = FontFamily.Monospace,
                                    fontSize = (baseFontSize - 2).sp,
                                    background = androidx.compose.ui.graphics.Color(0x15000000)
                                )) {
                                    append(codeBlocks[idx])
                                }
                            }
                        }
                        // Headings: # through ######
                        trimmed.startsWith("# ") -> {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = (baseFontSize + 10).sp)) {
                                appendInlineFormatted(trimmed.removePrefix("# "), linkColor)
                            }
                        }
                        trimmed.startsWith("## ") -> {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = (baseFontSize + 6).sp)) {
                                appendInlineFormatted(trimmed.removePrefix("## "), linkColor)
                            }
                        }
                        trimmed.startsWith("### ") -> {
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = (baseFontSize + 4).sp)) {
                                appendInlineFormatted(trimmed.removePrefix("### "), linkColor)
                            }
                        }
                        trimmed.startsWith("#### ") || trimmed.startsWith("##### ") || trimmed.startsWith("###### ") -> {
                            val text = trimmed.replaceFirst(Regex("^#{1,6} "), "")
                            withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = (baseFontSize + 2).sp)) {
                                appendInlineFormatted(text, linkColor)
                            }
                        }
                        // Horizontal rule: --- or *** or ___
                        trimmed.matches(Regex("^(---+|\\*\\*\\*+|___+)\\s*$")) -> {
                            append("─".repeat(30))
                        }
                        // Block quote: > text
                        trimmed.startsWith("> ") -> {
                            withStyle(SpanStyle(
                                fontStyle = FontStyle.Italic,
                                color = androidx.compose.ui.graphics.Color(0xFF666666)
                            )) {
                                append("│ ")
                                appendInlineFormatted(trimmed.removePrefix("> "), linkColor)
                            }
                        }
                        // Bullet list: - item
                        trimmed.startsWith("- ") -> {
                            append("• ")
                            appendInlineFormatted(trimmed.removePrefix("- "), linkColor)
                        }
                        // Numbered list: 1. item
                        trimmed.matches(Regex("^\\d+\\. .+")) -> {
                            appendInlineFormatted(trimmed, linkColor)
                        }
                        // Regular line
                        else -> {
                            appendInlineFormatted(rawLine, linkColor)
                        }
                    }

                    if (lineIndex < lines.size - 1) append("\n")
                }
            }
        }

        private fun AnnotatedString.Builder.appendInlineFormatted(
            text: String,
            linkColor: androidx.compose.ui.graphics.Color
        ) {
            // Combined regex: strikethrough | bold | italic | inline code | link
            val inlineRegex = Regex(
                "~~(.+?)~~" +                                     // strikethrough (group 1)
                "|\\*\\*(.+?)\\*\\*" +                            // bold (group 2)
                "|(?<!\\*)\\*(?!\\*)(.+?)(?<!\\*)\\*(?!\\*)" +    // italic (group 3)
                "|`(.+?)`" +                                      // inline code (group 4)
                "|\\[(.+?)\\]\\((.+?)\\)"                         // link (group 5 text, group 6 url)
            )

            var lastEnd = 0
            for (match in inlineRegex.findAll(text)) {
                if (match.range.first > lastEnd) {
                    append(text.substring(lastEnd, match.range.first))
                }
                when {
                    match.groupValues[1].isNotEmpty() -> {
                        withStyle(SpanStyle(textDecoration = TextDecoration.LineThrough)) {
                            append(match.groupValues[1])
                        }
                    }
                    match.groupValues[2].isNotEmpty() -> {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(match.groupValues[2])
                        }
                    }
                    match.groupValues[3].isNotEmpty() -> {
                        withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                            append(match.groupValues[3])
                        }
                    }
                    match.groupValues[4].isNotEmpty() -> {
                        withStyle(SpanStyle(
                            fontFamily = FontFamily.Monospace,
                            background = androidx.compose.ui.graphics.Color(0x20000000)
                        )) {
                            append(match.groupValues[4])
                        }
                    }
                    match.groupValues[5].isNotEmpty() -> {
                        pushStringAnnotation("URL", match.groupValues[6])
                        withStyle(SpanStyle(color = linkColor, textDecoration = TextDecoration.Underline)) {
                            append(match.groupValues[5])
                        }
                        pop()
                    }
                }
                lastEnd = match.range.last + 1
            }
            if (lastEnd < text.length) {
                append(text.substring(lastEnd))
            }
        }
    }
}
