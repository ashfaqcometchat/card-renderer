package org.cometchat.cardrender

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cometchat.cards.CometChatCardComposable
import com.cometchat.cards.models.CometChatCardLogLevel
import com.cometchat.cards.models.CometChatCardThemeMode
import com.cometchat.cards.models.type

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CardDemoScreen()
            }
        }
    }
}

@Composable
fun CardDemoScreen() {
    var selectedCard by remember { mutableIntStateOf(0) }
    val cardNames = listOf("Product Card", "All Elements", "Nested Layout", "Table Card")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("CometChat Cards Demo", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        ScrollableTabRow(selectedTabIndex = selectedCard) {
            cardNames.forEachIndexed { index, name ->
                Tab(selected = selectedCard == index, onClick = { selectedCard = index }) {
                    Text(name, modifier = Modifier.padding(12.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val cardJson = when (selectedCard) {
            0 -> PRODUCT_CARD
            1 -> ALL_ELEMENTS_CARD
            2 -> NESTED_LAYOUT_CARD
            3 -> TABLE_CARD
            else -> PRODUCT_CARD
        }

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            CometChatCardComposable(
                cardJson = cardJson,
                themeMode = CometChatCardThemeMode.AUTO,
                logLevel = CometChatCardLogLevel.VERBOSE,
                onAction = { event ->
                    Log.d("CardDemo", "Action: ${event.action.type}, elementId: ${event.elementId}")
                },
                onContainerStyle = { style ->
                    Log.d("CardDemo", "ContainerStyle: borderRadius=${style.borderRadius}")
                }
            )
        }
    }
}

private val PRODUCT_CARD = """
{
  "version": "1.0",
  "body": [
    {
      "id": "img_1", "type": "image",
      "url": "https://picsum.photos/400/200",
      "fit": "cover", "height": 200, "borderRadius": 8
    },
    { "id": "sp_1", "type": "spacer", "height": 8 },
    {
      "id": "txt_1", "type": "text",
      "content": "Sneaker X — Limited Edition",
      "variant": "heading2",
      "color": {"light": "#141414", "dark": "#E8E8E8"}
    },
    {
      "id": "txt_2", "type": "text",
      "content": "${'$'}129.99",
      "variant": "heading3",
      "color": "#3A3AF4"
    },
    { "id": "sp_2", "type": "spacer", "height": 8 },
    {
      "id": "row_1", "type": "row", "gap": 8,
      "items": [
        {
          "id": "btn_1", "type": "button",
          "label": "Buy Now", "variant": "filled",
          "backgroundColor": {"light": "#3A3AF4", "dark": "#5A5AF6"},
          "textColor": "#FFFFFF",
          "action": {"type": "openUrl", "url": "https://example.com"}
        },
        {
          "id": "btn_2", "type": "button",
          "label": "Add to Cart", "variant": "outlined",
          "backgroundColor": {"light": "#3A3AF4", "dark": "#5A5AF6"},
          "action": {"type": "customCallback", "callbackId": "add_to_cart"}
        }
      ]
    }
  ],
  "style": {
    "background": {"light": "#FFFFFF", "dark": "#1E1E1E"},
    "borderRadius": 12, "padding": 16
  },
  "fallbackText": "Sneaker X — Limited Edition"
}
""".trimIndent()

private val ALL_ELEMENTS_CARD = """
{
  "version": "1.0",
  "body": [
    {"id": "t1", "type": "text", "content": "All Content Elements", "variant": "heading1"},
    {"id": "d1", "type": "divider"},
    {"id": "sp1", "type": "spacer", "height": 8},
    {"id": "av1", "type": "avatar", "fallbackInitials": "JD", "size": 48, "backgroundColor": "#3A3AF4"},
    {"id": "sp2", "type": "spacer", "height": 8},
    {"id": "b1", "type": "badge", "text": "NEW", "backgroundColor": "#3A3AF4", "color": "#FFFFFF", "borderRadius": 10},
    {"id": "sp3", "type": "spacer", "height": 8},
    {"id": "ch1", "type": "chip", "text": "Android", "backgroundColor": "#E8E8E8", "borderRadius": 14},
    {"id": "sp4", "type": "spacer", "height": 8},
    {"id": "pb1", "type": "progressBar", "value": 72, "label": "Progress: 72%", "color": "#3A3AF4", "height": 8, "borderRadius": 4},
    {"id": "sp5", "type": "spacer", "height": 8},
    {"id": "cb1", "type": "codeBlock", "content": "val card = CometChatCardView(context)\ncard.setCardSchema(json)", "language": "Kotlin"},
    {"id": "sp6", "type": "spacer", "height": 8},
    {"id": "md1", "type": "markdown", "content": "This is **bold** and *italic* text with a [link](https://cometchat.com)."},
    {"id": "sp7", "type": "spacer", "height": 8},
    {"id": "lnk1", "type": "link", "text": "Visit CometChat", "action": {"type": "openUrl", "url": "https://cometchat.com"}}
  ],
  "fallbackText": "All content elements demo"
}
""".trimIndent()

private val NESTED_LAYOUT_CARD = """
{
  "version": "1.0",
  "body": [
    {"id": "t1", "type": "text", "content": "Nested Layouts", "variant": "heading2"},
    {"id": "sp1", "type": "spacer", "height": 8},
    {
      "id": "col1", "type": "column", "gap": 8,
      "backgroundColor": "#F5F5F5", "borderRadius": 8, "padding": 12,
      "items": [
        {
          "id": "row1", "type": "row", "gap": 8,
          "items": [
            {"id": "av1", "type": "avatar", "fallbackInitials": "A", "size": 36, "backgroundColor": "#3A3AF4"},
            {"id": "t2", "type": "text", "content": "User A sent a message", "variant": "body"}
          ]
        },
        {"id": "d1", "type": "divider"},
        {
          "id": "row2", "type": "row", "gap": 8,
          "items": [
            {"id": "av2", "type": "avatar", "fallbackInitials": "B", "size": 36, "backgroundColor": "#FF6B6B"},
            {"id": "t3", "type": "text", "content": "User B replied", "variant": "body"}
          ]
        }
      ]
    },
    {"id": "sp2", "type": "spacer", "height": 12},
    {
      "id": "acc1", "type": "accordion",
      "header": "Show Details",
      "body": [
        {"id": "t4", "type": "text", "content": "These are the hidden details inside an accordion.", "variant": "body2"}
      ],
      "border": true, "borderRadius": 8
    },
    {"id": "sp3", "type": "spacer", "height": 12},
    {
      "id": "tabs1", "type": "tabs",
      "tabs": [
        {"label": "Info", "content": [{"id": "t5", "type": "text", "content": "Info tab content"}]},
        {"label": "Settings", "content": [{"id": "t6", "type": "text", "content": "Settings tab content"}]}
      ]
    }
  ],
  "fallbackText": "Nested layout demo"
}
""".trimIndent()

private val TABLE_CARD = """
{
  "version": "1.0",
  "body": [
    {"id": "t1", "type": "text", "content": "Order Summary", "variant": "heading2"},
    {"id": "sp1", "type": "spacer", "height": 8},
    {
      "id": "tbl1", "type": "table",
      "columns": ["Item", "Qty", "Price"],
      "rows": [
        ["Widget A", "2", "${'$'}10.00"],
        ["Widget B", "1", "${'$'}25.00"],
        ["Widget C", "3", "${'$'}7.50"]
      ],
      "stripedRows": true,
      "border": true,
      "headerBackgroundColor": "#3A3AF4"
    },
    {"id": "sp2", "type": "spacer", "height": 12},
    {
      "id": "row1", "type": "row", "gap": 8, "align": "end",
      "items": [
        {"id": "t2", "type": "text", "content": "Total: ${'$'}67.50", "variant": "heading3", "fontWeight": "bold"}
      ]
    },
    {"id": "sp3", "type": "spacer", "height": 8},
    {
      "id": "btn1", "type": "button",
      "label": "Place Order", "variant": "filled", "fullWidth": true,
      "backgroundColor": "#3A3AF4", "textColor": "#FFFFFF",
      "action": {"type": "customCallback", "callbackId": "place_order"}
    }
  ],
  "style": {"padding": 16, "borderRadius": 12},
  "fallbackText": "Order summary"
}
""".trimIndent()
