package com.cometchat.cards

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.cometchat.cards.actions.CometChatCardActionCallback
import com.cometchat.cards.core.*
import com.cometchat.cards.models.*
import com.cometchat.cards.parser.CometChatCardSchemaParser
import com.cometchat.cards.renderers.*
import com.cometchat.cards.theme.CometChatCardThemeOverride
import com.cometchat.cards.theme.CometChatCardThemeResolver

class CometChatCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var cardJson: String? = null
    private var themeMode: CometChatCardThemeMode = CometChatCardThemeMode.AUTO
    private var actionCallback: CometChatCardActionCallback? = null
    private var themeOverride: CometChatCardThemeOverride? = null
    private var containerStyle: CometChatCardContainerStyle? = null
    private val loadingStateManager = CometChatCardLoadingStateManager()
    private val registry = createDefaultRegistry()

    fun setCardSchema(json: String) {
        this.cardJson = json
        render()
    }

    fun setThemeMode(mode: CometChatCardThemeMode) {
        this.themeMode = mode
        if (cardJson != null) render()
    }

    fun setActionCallback(callback: CometChatCardActionCallback?) {
        this.actionCallback = callback
    }

    fun setThemeOverride(override: CometChatCardThemeOverride?) {
        this.themeOverride = override
        if (cardJson != null) render()
    }

    fun setLogLevel(level: CometChatCardLogLevel) {
        CometChatCardLogger.logLevel = level
    }

    fun setElementLoading(elementId: String, loading: Boolean) {
        loadingStateManager.setLoading(elementId, loading)
        if (cardJson != null) render()
    }

    fun getContainerStyle(): CometChatCardContainerStyle? = containerStyle

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (themeMode == CometChatCardThemeMode.AUTO && cardJson != null) {
            render()
        }
    }

    private fun render() {
        removeAllViews()
        val json = cardJson ?: return

        val result = CometChatCardSchemaParser.parse(json)
        if (result.isFailure) {
            CometChatCardLogger.error("Failed to parse card JSON: ${result.exceptionOrNull()?.message}")
            showFallback(null)
            return
        }

        val schema = result.getOrThrow()
        containerStyle = schema.style

        if (schema.body.isEmpty()) return

        val isSystemDark = (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        val effectiveMode = CometChatCardThemeResolver.resolveEffectiveMode(themeMode, isSystemDark)
        val resolvedTheme = CometChatCardThemeResolver.resolveTheme(themeOverride)

        val renderContext = CometChatCardRenderContext(
            effectiveThemeMode = effectiveMode,
            resolvedTheme = resolvedTheme,
            onAction = actionCallback,
            cardJson = json,
            depth = 0,
            registry = registry,
            loadingStateManager = loadingStateManager
        )

        val column = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        }

        for (element in schema.body) {
            val renderer = registry.getRenderer(element.type)
            if (renderer != null) {
                try {
                    val childView = renderer.renderView(context, element, renderContext)
                    // Top-level body children must fill card width, preserving original height
                    val originalLp = childView.layoutParams
                    val originalHeight = originalLp?.height ?: ViewGroup.LayoutParams.WRAP_CONTENT
                    val lp = originalLp as? LinearLayout.LayoutParams
                        ?: LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, originalHeight)
                    lp.width = ViewGroup.LayoutParams.MATCH_PARENT
                    childView.layoutParams = lp
                    column.addView(childView)
                } catch (e: Exception) {
                    CometChatCardLogger.error("Renderer exception for ${element.type}: ${e.message}")
                }
            } else {
                CometChatCardLogger.warning("Skipping unknown element type: ${element.type}, id: ${element.id}")
            }
        }

        addView(column)
    }

    private fun showFallback(fallbackText: String?) {
        removeAllViews()
        val text = fallbackText?.takeIf { it.isNotBlank() } ?: "Unable to display this message"
        addView(TextView(context).apply {
            this.text = text
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            gravity = Gravity.CENTER
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        })
    }

    companion object {
        fun createDefaultRegistry(): CometChatCardElementRegistry {
            return CometChatCardElementRegistry().apply {
                register("text", TextElementRenderer())
                register("image", ImageElementRenderer())
                register("icon", IconElementRenderer())
                register("avatar", AvatarElementRenderer())
                register("badge", BadgeElementRenderer())
                register("divider", DividerElementRenderer())
                register("spacer", SpacerElementRenderer())
                register("chip", ChipElementRenderer())
                register("progressBar", ProgressBarElementRenderer())
                register("codeBlock", CodeBlockElementRenderer())
                register("markdown", MarkdownElementRenderer())
                register("row", RowElementRenderer())
                register("column", ColumnElementRenderer())
                register("grid", GridElementRenderer())
                register("accordion", AccordionElementRenderer())
                register("tabs", TabsElementRenderer())
                register("button", ButtonElementRenderer())
                register("iconButton", IconButtonElementRenderer())
                register("link", LinkElementRenderer())
                register("table", TableElementRenderer())
            }
        }
    }
}
