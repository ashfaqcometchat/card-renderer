package com.cometchat.cards.renderers

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.cometchat.cards.actions.CometChatCardActionEmitter
import com.cometchat.cards.core.CometChatCardElementRenderer
import com.cometchat.cards.core.CometChatCardRenderContext
import com.cometchat.cards.models.CometChatCardButtonElement
import com.cometchat.cards.models.CometChatCardElement
import com.cometchat.cards.theme.CometChatCardThemeResolver

class ButtonElementRenderer : CometChatCardElementRenderer {

    override fun renderView(context: Context, element: CometChatCardElement, renderContext: CometChatCardRenderContext): View {
        val el = element as CometChatCardButtonElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val density = context.resources.displayMetrics.density
        val variant = el.variant ?: "filled"
        val borderRadius = el.borderRadius ?: 8
        val fontSize = el.fontSize ?: 15
        val isDisabled = el.disabled == true
        val isLoading = renderContext.loadingStateManager.isLoading(el.id)
        val heightDp = when (el.size) { "small" -> 32; "large" -> 52; else -> 44 }

        val bgColorHex = CometChatCardThemeResolver.resolveColor(el.backgroundColor, mode, theme.buttonFilledBg)
        val textColorHex = CometChatCardThemeResolver.resolveColor(el.textColor, mode, theme.buttonFilledText)
        val borderColorHex = CometChatCardThemeResolver.resolveColor(el.borderColor, mode)

        val row = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
            minimumHeight = (heightDp * density).toInt()
            layoutParams = if (el.fullWidth == true)
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            else
                LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            val bg = GradientDrawable().apply { cornerRadius = borderRadius * density }

            when (variant) {
                "filled" -> {
                    if (isDisabled) {
                        val disabledBg = CometChatCardThemeResolver.resolveColor(null, mode, theme.buttonDisabledBg)
                        disabledBg?.let { runCatching { bg.setColor(Color.parseColor(it)) } }
                    } else {
                        bgColorHex?.let { runCatching { bg.setColor(Color.parseColor(it)) } }
                    }
                }
                "outlined" -> {
                    bg.setColor(Color.TRANSPARENT)
                    val bc = borderColorHex ?: bgColorHex
                    bc?.let { runCatching { bg.setStroke((el.borderWidth ?: 1).let { w -> (w * density).toInt() }, Color.parseColor(it)) } }
                }
                "text" -> bg.setColor(Color.TRANSPARENT)
                "tonal" -> {
                    bgColorHex?.let {
                        runCatching {
                            val c = Color.parseColor(it)
                            bg.setColor(Color.argb(38, Color.red(c), Color.green(c), Color.blue(c)))
                        }
                    }
                }
            }
            background = bg
            applyPadding(this, el.padding)
            isEnabled = !isDisabled && !isLoading
            alpha = if (isDisabled) 0.5f else 1f

            setOnClickListener {
                if (!isDisabled && !isLoading) {
                    CometChatCardActionEmitter.emit(el.action, el.id, renderContext.cardJson, renderContext.onAction)
                }
            }
            contentDescription = el.label
        }

        if (isLoading) {
            row.addView(ProgressBar(context).apply {
                layoutParams = LinearLayout.LayoutParams((20 * density).toInt(), (20 * density).toInt())
            })
        } else {
            val iconUrl = CometChatCardThemeResolver.resolveUrl(el.icon, mode)
            if (iconUrl != null && el.iconPosition != "right") {
                val iconView = ImageView(context).apply {
                    layoutParams = LinearLayout.LayoutParams((18 * density).toInt(), (18 * density).toInt()).apply { marginEnd = (4 * density).toInt() }
                    scaleType = ImageView.ScaleType.FIT_CENTER
                }
                row.addView(iconView)
                coil3.SingletonImageLoader.get(context).enqueue(coil3.request.ImageRequest.Builder(context).data(iconUrl).target(iconView).build())
            }

            val effectiveTextColor = when (variant) {
                "filled" -> if (isDisabled) CometChatCardThemeResolver.resolveColor(null, mode, theme.buttonDisabledText) else textColorHex
                "outlined" -> el.textColor?.let { CometChatCardThemeResolver.resolveColor(it, mode) } ?: bgColorHex
                "text", "tonal" -> bgColorHex
                else -> textColorHex
            }

            row.addView(TextView(context).apply {
                text = el.label
                setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
                effectiveTextColor?.let { runCatching { setTextColor(Color.parseColor(it)) } }
            })

            if (iconUrl != null && el.iconPosition == "right") {
                val iconView = ImageView(context).apply {
                    layoutParams = LinearLayout.LayoutParams((18 * density).toInt(), (18 * density).toInt()).apply { marginStart = (4 * density).toInt() }
                    scaleType = ImageView.ScaleType.FIT_CENTER
                }
                row.addView(iconView)
                coil3.SingletonImageLoader.get(context).enqueue(coil3.request.ImageRequest.Builder(context).data(iconUrl).target(iconView).build())
            }
        }

        return row
    }

    @Composable
    override fun RenderComposable(element: CometChatCardElement, renderContext: CometChatCardRenderContext) {
        val el = element as CometChatCardButtonElement
        val mode = renderContext.effectiveThemeMode
        val theme = renderContext.resolvedTheme
        val variant = el.variant ?: "filled"
        val borderRadius = el.borderRadius ?: 8
        val fontSize = el.fontSize ?: 15
        val isDisabled = el.disabled == true
        val isLoading = renderContext.loadingStateManager.isLoading(el.id)
        val shape = RoundedCornerShape(borderRadius.dp)

        val bgColor = CometChatCardThemeResolver.resolveColor(el.backgroundColor, mode, theme.buttonFilledBg)
        val textColor = CometChatCardThemeResolver.resolveColor(el.textColor, mode, theme.buttonFilledText)
        val borderColor = CometChatCardThemeResolver.resolveColor(el.borderColor, mode)

        val onClick: () -> Unit = {
            if (!isDisabled && !isLoading) {
                CometChatCardActionEmitter.emit(el.action, el.id, renderContext.cardJson, renderContext.onAction)
            }
        }

        val modifier = if (el.fullWidth == true) Modifier.fillMaxWidth() else Modifier

        when (variant) {
            "filled" -> {
                Button(
                    onClick = onClick,
                    enabled = !isDisabled && !isLoading,
                    shape = shape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = bgColor?.let { parseComposeColor(it) } ?: MaterialTheme.colorScheme.primary,
                        contentColor = textColor?.let { parseComposeColor(it) } ?: androidx.compose.ui.graphics.Color.White
                    ),
                    modifier = modifier
                ) {
                    ButtonContent(el, isLoading, fontSize, mode, renderContext)
                }
            }
            "outlined" -> {
                OutlinedButton(
                    onClick = onClick,
                    enabled = !isDisabled && !isLoading,
                    shape = shape,
                    border = BorderStroke(
                        (el.borderWidth ?: 1).dp,
                        (borderColor ?: bgColor)?.let { parseComposeColor(it) } ?: MaterialTheme.colorScheme.primary
                    ),
                    modifier = modifier
                ) {
                    val tc = (el.textColor?.let { CometChatCardThemeResolver.resolveColor(it, mode) } ?: bgColor)
                    ButtonContent(el, isLoading, fontSize, mode, renderContext, textColorOverride = tc)
                }
            }
            "text" -> {
                TextButton(onClick = onClick, enabled = !isDisabled && !isLoading, shape = shape, modifier = modifier) {
                    ButtonContent(el, isLoading, fontSize, mode, renderContext, textColorOverride = bgColor)
                }
            }
            "tonal" -> {
                FilledTonalButton(
                    onClick = onClick,
                    enabled = !isDisabled && !isLoading,
                    shape = shape,
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = bgColor?.let { parseComposeColor(it).copy(alpha = 0.15f) } ?: MaterialTheme.colorScheme.secondaryContainer
                    ),
                    modifier = modifier
                ) {
                    ButtonContent(el, isLoading, fontSize, mode, renderContext, textColorOverride = bgColor)
                }
            }
        }
    }

    @Composable
    private fun ButtonContent(
        el: CometChatCardButtonElement,
        isLoading: Boolean,
        fontSize: Int,
        mode: com.cometchat.cards.models.CometChatCardThemeMode,
        renderContext: CometChatCardRenderContext,
        textColorOverride: String? = null
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
        } else {
            val iconUrl = CometChatCardThemeResolver.resolveUrl(el.icon, mode)
            if (iconUrl != null && el.iconPosition != "right") {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(iconUrl).build(),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp).padding(end = 4.dp)
                )
            }
            Text(
                text = el.label,
                fontSize = fontSize.sp,
                color = textColorOverride?.let { parseComposeColor(it) } ?: androidx.compose.ui.graphics.Color.Unspecified
            )
            if (iconUrl != null && el.iconPosition == "right") {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current).data(iconUrl).build(),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp).padding(start = 4.dp)
                )
            }
        }
    }
}
