package com.cometchat.cards.renderers

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.CometChatCardElement
import com.cometchat.cards.models.CometChatCardTableElement
import com.cometchat.cards.theme.CometChatCardThemeResolver

class TableElementRenderer : CometChatCardElementRenderer {

    override fun renderView(context: Context, element: CometChatCardElement, renderContext: CometChatCardRenderContext): View {
        val el = element as CometChatCardTableElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val density = context.resources.displayMetrics.density
        val cellPadding = el.cellPadding ?: 8
        val fontSize = el.fontSize ?: 14
        val headerBg = CometChatCardThemeResolver.resolveColor(el.headerBackgroundColor, mode, theme.tableHeaderBg)
        val stripedBg = CometChatCardThemeResolver.resolveColor(el.stripedRowColor, mode, theme.tableStripedRowBg)
        val borderColor = CometChatCardThemeResolver.resolveColor(el.borderColor, mode, theme.borderColor)
        val textColor = CometChatCardThemeResolver.resolveColor(null, mode, theme.textColor)

        val table = TableLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            isStretchAllColumns = true
        }

        // Header row
        val headerRow = TableRow(context)
        headerBg?.let { headerRow.runCatching { setBackgroundColor(Color.parseColor(it)) } }
        for (col in el.columns) {
            headerRow.addView(createCell(context, col, cellPadding, fontSize, textColor, density, isBold = true, borderColor = if (el.border == true) borderColor else null))
        }
        table.addView(headerRow)

        // Data rows
        for ((index, row) in el.rows.withIndex()) {
            val tableRow = TableRow(context)
            if (el.stripedRows == true && index % 2 == 1) {
                stripedBg?.let { tableRow.runCatching { setBackgroundColor(Color.parseColor(it)) } }
            }
            for (cell in row) {
                tableRow.addView(createCell(context, cell, cellPadding, fontSize, textColor, density, isBold = false, borderColor = if (el.border == true) borderColor else null))
            }
            table.addView(tableRow)
        }

        return table
    }

    private fun createCell(context: Context, text: String, padding: Int, fontSize: Int, textColor: String?, density: Float, isBold: Boolean, borderColor: String?): TextView {
        return TextView(context).apply {
            this.text = text
            setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
            textColor?.let { runCatching { setTextColor(Color.parseColor(it)) } }
            if (isBold) setTypeface(null, android.graphics.Typeface.BOLD)
            gravity = Gravity.START or Gravity.CENTER_VERTICAL
            val px = (padding * density).toInt()
            setPadding(px, px, px, px)
            if (borderColor != null) {
                background = GradientDrawable().apply {
                    runCatching { setStroke(1, Color.parseColor(borderColor)) }
                }
            }
        }
    }

    @Composable
    override fun RenderComposable(element: CometChatCardElement, renderContext: CometChatCardRenderContext) {
        val el = element as CometChatCardTableElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val cellPadding = el.cellPadding ?: 8
        val fontSize = el.fontSize ?: 14
        val headerBg = CometChatCardThemeResolver.resolveColor(el.headerBackgroundColor, mode, theme.tableHeaderBg)
        val stripedBg = CometChatCardThemeResolver.resolveColor(el.stripedRowColor, mode, theme.tableStripedRowBg)
        val borderColor = CometChatCardThemeResolver.resolveColor(el.borderColor, mode, theme.borderColor)
        val textColor = CometChatCardThemeResolver.resolveColor(null, mode, theme.textColor)

        Column(modifier = Modifier.fillMaxWidth()) {
            // Header row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(headerBg?.let { Modifier.background(parseComposeColor(it)) } ?: Modifier)
            ) {
                for (col in el.columns) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .then(if (el.border == true && borderColor != null) Modifier.border(1.dp, parseComposeColor(borderColor)) else Modifier)
                            .padding(cellPadding.dp)
                    ) {
                        Text(
                            text = col,
                            fontSize = fontSize.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            color = textColor?.let { parseComposeColor(it) } ?: androidx.compose.ui.graphics.Color.Unspecified
                        )
                    }
                }
            }

            // Data rows
            for ((index, row) in el.rows.withIndex()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (el.stripedRows == true && index % 2 == 1 && stripedBg != null)
                                Modifier.background(parseComposeColor(stripedBg))
                            else Modifier
                        )
                ) {
                    for (cell in row) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .then(if (el.border == true && borderColor != null) Modifier.border(1.dp, parseComposeColor(borderColor)) else Modifier)
                                .padding(cellPadding.dp)
                        ) {
                            Text(
                                text = cell,
                                fontSize = fontSize.sp,
                                color = textColor?.let { parseComposeColor(it) } ?: androidx.compose.ui.graphics.Color.Unspecified
                            )
                        }
                    }
                }
            }
        }
    }
}
