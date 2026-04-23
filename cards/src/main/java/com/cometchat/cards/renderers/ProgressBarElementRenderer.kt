package com.cometchat.cards.renderers

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.CometChatCardElement
import com.cometchat.cards.models.CometChatCardProgressBarElement
import com.cometchat.cards.theme.CometChatCardThemeResolver

class ProgressBarElementRenderer : CometChatCardElementRenderer {

    override fun renderView(context: Context, element: CometChatCardElement, renderContext: CometChatCardRenderContext): View {
        val el = element as CometChatCardProgressBarElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val density = context.resources.displayMetrics.density
        val clamped = el.value.coerceIn(0, 100)
        val height = el.height ?: 4
        val borderRadius = el.borderRadius ?: (height / 2)

        val barColor = CometChatCardThemeResolver.resolveColor(el.color, mode, theme.progressBarColor)
        val trackColor = CometChatCardThemeResolver.resolveColor(el.trackColor, mode, theme.progressTrackColor)

        val container = LinearLayout(context).apply { orientation = LinearLayout.VERTICAL }

        if (el.label != null) {
            val labelColor = CometChatCardThemeResolver.resolveColor(el.labelColor, mode, theme.textColor)
            container.addView(TextView(context).apply {
                text = el.label
                setTextSize(TypedValue.COMPLEX_UNIT_SP, (el.labelFontSize ?: 12).toFloat())
                labelColor?.let { runCatching { setTextColor(Color.parseColor(it)) } }
                gravity = Gravity.START
            })
        }

        val track = FrameLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (height * density).toInt())
            background = GradientDrawable().apply {
                cornerRadius = borderRadius * density
                trackColor?.let { runCatching { setColor(Color.parseColor(it)) } }
            }
        }

        val fill = View(context).apply {
            layoutParams = FrameLayout.LayoutParams(0, FrameLayout.LayoutParams.MATCH_PARENT)
            background = GradientDrawable().apply {
                cornerRadius = borderRadius * density
                barColor?.let { runCatching { setColor(Color.parseColor(it)) } }
            }
        }

        track.addView(fill)
        track.post { fill.layoutParams.width = (track.width * clamped / 100); fill.requestLayout() }
        container.addView(track)

        container.contentDescription = "${el.label ?: "Progress"}: $clamped%"
        return container
    }

    @Composable
    override fun RenderComposable(element: CometChatCardElement, renderContext: CometChatCardRenderContext) {
        val el = element as CometChatCardProgressBarElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val clamped = el.value.coerceIn(0, 100)
        val height = el.height ?: 4
        val borderRadius = el.borderRadius ?: (height / 2)
        val shape = RoundedCornerShape(borderRadius.dp)

        val barColor = CometChatCardThemeResolver.resolveColor(el.color, mode, theme.progressBarColor)
        val trackColor = CometChatCardThemeResolver.resolveColor(el.trackColor, mode, theme.progressTrackColor)

        Column {
            if (el.label != null) {
                val labelColor = CometChatCardThemeResolver.resolveColor(el.labelColor, mode, theme.textColor)
                Text(
                    text = el.label,
                    fontSize = (el.labelFontSize ?: 12).sp,
                    color = labelColor?.let { parseComposeColor(it) } ?: androidx.compose.ui.graphics.Color.Unspecified
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height.dp)
                    .clip(shape)
                    .background(trackColor?.let { parseComposeColor(it) } ?: androidx.compose.ui.graphics.Color.LightGray)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(clamped / 100f)
                        .clip(shape)
                        .background(barColor?.let { parseComposeColor(it) } ?: androidx.compose.ui.graphics.Color.Blue)
                )
            }
        }
    }
}
