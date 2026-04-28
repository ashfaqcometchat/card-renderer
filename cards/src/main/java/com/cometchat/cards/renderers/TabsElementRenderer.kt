package com.cometchat.cards.renderers

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.CometChatCardElement
import com.cometchat.cards.models.CometChatCardTabsElement
import com.cometchat.cards.models.type
import com.cometchat.cards.theme.CometChatCardThemeResolver

class TabsElementRenderer : CometChatCardElementRenderer {

    override fun renderView(context: Context, element: CometChatCardElement, renderContext: CometChatCardRenderContext): View {
        val el = element as CometChatCardTabsElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val density = context.resources.displayMetrics.density
        var activeTab = el.defaultActiveTab ?: 0
        val fontSize = el.fontSize ?: 14

        if (renderContext.depth >= CometChatCardRenderContext.MAX_DEPTH) {
            renderContext.logger.warning("Max nesting depth exceeded, id=${el.id}")
            return View(context)
        }

        val container = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        val contentContainer = FrameLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        val activeColor = CometChatCardThemeResolver.resolveColor(null, mode, theme.tabActiveColor)
        val inactiveColor = CometChatCardThemeResolver.resolveColor(null, mode, theme.tabInactiveColor)

        // Tab bar — use a horizontal LinearLayout with equal-weight tabs
        val tabRow = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        // Store tab containers for indicator updates
        val tabContainers = mutableListOf<LinearLayout>()

        fun renderContent(tabIndex: Int) {
            contentContainer.removeAllViews()
            val content = el.tabs.getOrNull(tabIndex)?.content ?: return
            val col = LinearLayout(context).apply { orientation = LinearLayout.VERTICAL }
            for (child in content) {
                val renderer = renderContext.registry.getRenderer(child.type)
                if (renderer != null) {
                    try { col.addView(renderer.renderView(context, child, renderContext.withDepth(renderContext.depth + 1))) }
                    catch (e: Exception) { renderContext.logger.error("Renderer exception: ${e.message}") }
                }
            }
            contentContainer.addView(col)
        }

        fun updateTabStyles(selectedIndex: Int) {
            for ((i, tc) in tabContainers.withIndex()) {
                val tv = tc.getChildAt(0) as? TextView ?: continue
                val indicator = tc.getChildAt(1) as? View ?: continue
                val isActive = i == selectedIndex
                val c = if (isActive) activeColor else inactiveColor
                c?.let { runCatching { tv.setTextColor(Color.parseColor(it)) } }
                tv.typeface = if (isActive) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
                if (isActive && activeColor != null) {
                    indicator.visibility = View.VISIBLE
                    runCatching { indicator.setBackgroundColor(Color.parseColor(activeColor)) }
                } else {
                    indicator.visibility = View.INVISIBLE
                }
            }
        }

        for ((index, tab) in el.tabs.withIndex()) {
            // Each tab is a vertical container: text + indicator line
            val tabContainer = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER_HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)
                setOnClickListener {
                    activeTab = index
                    renderContent(index)
                    updateTabStyles(index)
                }
            }

            val tabText = TextView(context).apply {
                text = tab.label
                setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
                gravity = Gravity.CENTER
                setPadding((12 * density).toInt(), (10 * density).toInt(), (12 * density).toInt(), (6 * density).toInt())
            }

            val indicator = View(context).apply {
                layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (2 * density).toInt()).apply {
                    topMargin = (2 * density).toInt()
                }
            }

            tabContainer.addView(tabText)
            tabContainer.addView(indicator)
            tabContainers.add(tabContainer)
            tabRow.addView(tabContainer)
        }

        container.addView(tabRow)
        container.addView(contentContainer)
        updateTabStyles(activeTab)
        renderContent(activeTab)

        return container
    }

    @Composable
    override fun RenderComposable(element: CometChatCardElement, renderContext: CometChatCardRenderContext) {
        val el = element as CometChatCardTabsElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        var activeTab by remember { mutableIntStateOf(el.defaultActiveTab ?: 0) }
        val fontSize = el.fontSize ?: 14

        if (renderContext.depth >= CometChatCardRenderContext.MAX_DEPTH) {
            renderContext.logger.warning("Max nesting depth exceeded, id=${el.id}")
            return
        }

        val activeColor = CometChatCardThemeResolver.resolveColor(null, mode, theme.tabActiveColor)
        val inactiveColor = CometChatCardThemeResolver.resolveColor(null, mode, theme.tabInactiveColor)

        Column(modifier = Modifier.fillMaxWidth()) {
            // Tab bar — equal-width tabs with bottom indicator
            Row(modifier = composePadding(el.tabPadding).fillMaxWidth()) {
                for ((index, tab) in el.tabs.withIndex()) {
                    val isActive = index == activeTab
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { activeTab = index },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = tab.label,
                            fontSize = fontSize.sp,
                            fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                            color = (if (isActive) activeColor else inactiveColor)?.let { parseComposeColor(it) }
                                ?: androidx.compose.ui.graphics.Color.Unspecified,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
                        )
                        // Bottom indicator line
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .then(
                                    if (isActive && activeColor != null)
                                        Modifier.background(parseComposeColor(activeColor))
                                    else Modifier
                                )
                        )
                    }
                }
            }

            // Content
            Box(modifier = composePadding(el.contentPadding).fillMaxWidth()) {
                val content = el.tabs.getOrNull(activeTab)?.content ?: emptyList()
                Column {
                    for (child in content) {
                        val renderer = renderContext.registry.getRenderer(child.type)
                        renderer?.RenderComposable(child, renderContext.withDepth(renderContext.depth + 1))
                    }
                }
            }
        }
    }
}
