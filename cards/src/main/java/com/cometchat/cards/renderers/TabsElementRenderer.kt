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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.CometChatCardElement
import com.cometchat.cards.models.CometChatCardTabsElement
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

        val tabBar = HorizontalScrollView(context).apply {
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        val tabRow = LinearLayout(context).apply { orientation = LinearLayout.HORIZONTAL }

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

        val activeColor = CometChatCardThemeResolver.resolveColor(null, mode, theme.tabActiveColor)
        val inactiveColor = CometChatCardThemeResolver.resolveColor(null, mode, theme.tabInactiveColor)

        for ((index, tab) in el.tabs.withIndex()) {
            val tabView = TextView(context).apply {
                text = tab.label
                setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
                gravity = Gravity.CENTER
                setPadding((12 * density).toInt(), (8 * density).toInt(), (12 * density).toInt(), (8 * density).toInt())
                val color = if (index == activeTab) activeColor else inactiveColor
                color?.let { runCatching { setTextColor(Color.parseColor(it)) } }
                if (index == activeTab) typeface = Typeface.DEFAULT_BOLD
                setOnClickListener {
                    activeTab = index
                    renderContent(index)
                    // Update tab colors
                    for (i in 0 until tabRow.childCount) {
                        val tv = tabRow.getChildAt(i) as? TextView ?: continue
                        val c = if (i == index) activeColor else inactiveColor
                        c?.let { runCatching { tv.setTextColor(Color.parseColor(it)) } }
                        tv.typeface = if (i == index) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
                    }
                }
            }
            tabRow.addView(tabView)
        }

        tabBar.addView(tabRow)
        container.addView(tabBar)
        container.addView(contentContainer)
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
            // Tab bar
            LazyRow(
                modifier = composePadding(el.tabPadding).fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                itemsIndexed(el.tabs) { index, tab ->
                    val isActive = index == activeTab
                    Text(
                        text = tab.label,
                        fontSize = fontSize.sp,
                        fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal,
                        textDecoration = if (isActive) TextDecoration.Underline else TextDecoration.None,
                        color = (if (isActive) activeColor else inactiveColor)?.let { parseComposeColor(it) }
                            ?: androidx.compose.ui.graphics.Color.Unspecified,
                        modifier = Modifier
                            .clickable { activeTab = index }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
            }

            // Content
            Box(modifier = composePadding(el.contentPadding).fillMaxWidth()) {
                val content = el.tabs.getOrNull(activeTab)?.content ?: emptyList()
                Column {
                    for (child in content) {
                        val renderer = renderContext.registry.getRenderer(child.type)
                        if (renderer != null) {
                            try { renderer.RenderComposable(child, renderContext.withDepth(renderContext.depth + 1)) }
                            catch (e: Exception) { renderContext.logger.error("Renderer exception: ${e.message}") }
                        }
                    }
                }
            }
        }
    }
}
