package com.cometchat.cards.parser

import com.cometchat.cards.models.*
import org.junit.Assert.*
import org.junit.Test

class CardSchemaParserTest {

    private fun loadFixture(name: String): String {
        return javaClass.classLoader!!.getResourceAsStream("fixtures/$name")!!
            .bufferedReader().readText()
    }

    @Test
    fun `parse minimal card with single text element`() {
        val json = loadFixture("minimal-card.json")
        val result = CometChatCardSchemaParser.parse(json)
        assertTrue(result.isSuccess)
        val schema = result.getOrThrow()
        assertEquals("1.0", schema.version)
        assertEquals(1, schema.body.size)
        assertEquals("Hello World", schema.fallbackText)
        val text = schema.body[0] as CometChatCardTextElement
        assertEquals("txt_1", text.id)
        assertEquals("Hello World", text.content)
    }

    @Test
    fun `parse all 9 action types`() {
        val json = loadFixture("all-actions.json")
        val result = CometChatCardSchemaParser.parse(json)
        assertTrue(result.isSuccess)
        val schema = result.getOrThrow()
        assertEquals(9, schema.body.size)

        val btn1 = schema.body[0] as CometChatCardButtonElement
        assertTrue(btn1.action is CometChatCardOpenUrlAction)
        assertEquals("https://example.com", (btn1.action as CometChatCardOpenUrlAction).url)

        val btn2 = schema.body[1] as CometChatCardButtonElement
        assertTrue(btn2.action is CometChatCardCopyToClipboardAction)

        val btn3 = schema.body[2] as CometChatCardButtonElement
        assertTrue(btn3.action is CometChatCardDownloadFileAction)

        val btn4 = schema.body[3] as CometChatCardButtonElement
        assertTrue(btn4.action is CometChatCardApiCallAction)

        val btn5 = schema.body[4] as CometChatCardButtonElement
        assertTrue(btn5.action is CometChatCardChatWithUserAction)

        val btn6 = schema.body[5] as CometChatCardButtonElement
        assertTrue(btn6.action is CometChatCardChatWithGroupAction)

        val btn7 = schema.body[6] as CometChatCardButtonElement
        assertTrue(btn7.action is CometChatCardSendMessageAction)

        val btn8 = schema.body[7] as CometChatCardButtonElement
        assertTrue(btn8.action is CometChatCardInitiateCallAction)

        val btn9 = schema.body[8] as CometChatCardButtonElement
        assertTrue(btn9.action is CometChatCardCustomCallbackAction)
    }

    @Test
    fun `parse ColorOrHex as plain hex string`() {
        val json = """
        {
            "version": "1.0",
            "body": [{"type": "text", "id": "t1", "content": "Hi", "color": "#FF0000"}],
            "fallbackText": "test"
        }
        """.trimIndent()
        val schema = CometChatCardSchemaParser.parse(json).getOrThrow()
        val text = schema.body[0] as CometChatCardTextElement
        assertTrue(text.color is CometChatCardColorOrHex.Hex)
        assertEquals("#FF0000", (text.color as CometChatCardColorOrHex.Hex).value)
    }

    @Test
    fun `parse ColorOrHex as themed object`() {
        val json = """
        {
            "version": "1.0",
            "body": [{"type": "text", "id": "t1", "content": "Hi", "color": {"light": "#FFF", "dark": "#000"}}],
            "fallbackText": "test"
        }
        """.trimIndent()
        val schema = CometChatCardSchemaParser.parse(json).getOrThrow()
        val text = schema.body[0] as CometChatCardTextElement
        assertTrue(text.color is CometChatCardColorOrHex.Themed)
        val themed = text.color as CometChatCardColorOrHex.Themed
        assertEquals("#FFF", themed.colorValue.light)
        assertEquals("#000", themed.colorValue.dark)
    }

    @Test
    fun `parse Padding as uniform number`() {
        val json = """
        {
            "version": "1.0",
            "body": [{"type": "text", "id": "t1", "content": "Hi", "padding": 16}],
            "fallbackText": "test"
        }
        """.trimIndent()
        val schema = CometChatCardSchemaParser.parse(json).getOrThrow()
        val text = schema.body[0] as CometChatCardTextElement
        assertTrue(text.padding is CometChatCardPadding.Uniform)
        assertEquals(16, (text.padding as CometChatCardPadding.Uniform).value)
    }

    @Test
    fun `parse Padding as per-side object`() {
        val json = """
        {
            "version": "1.0",
            "body": [{"type": "text", "id": "t1", "content": "Hi", "padding": {"top": 8, "bottom": 16}}],
            "fallbackText": "test"
        }
        """.trimIndent()
        val schema = CometChatCardSchemaParser.parse(json).getOrThrow()
        val text = schema.body[0] as CometChatCardTextElement
        assertTrue(text.padding is CometChatCardPadding.PerSide)
        val pad = text.padding as CometChatCardPadding.PerSide
        assertEquals(8, pad.top)
        assertEquals(0, pad.right)
        assertEquals(16, pad.bottom)
        assertEquals(0, pad.left)
    }

    @Test
    fun `parse Dimension as dp number`() {
        val json = """
        {
            "version": "1.0",
            "body": [{"type": "image", "id": "img1", "url": "https://img.com/a.jpg", "height": 200}],
            "fallbackText": "test"
        }
        """.trimIndent()
        val schema = CometChatCardSchemaParser.parse(json).getOrThrow()
        val img = schema.body[0] as CometChatCardImageElement
        assertTrue(img.height is CometChatCardDimension.Dp)
        assertEquals(200, (img.height as CometChatCardDimension.Dp).value)
    }

    @Test
    fun `parse Dimension as percentage string`() {
        val json = """
        {
            "version": "1.0",
            "body": [{"type": "image", "id": "img1", "url": "https://img.com/a.jpg", "width": "50%"}],
            "fallbackText": "test"
        }
        """.trimIndent()
        val schema = CometChatCardSchemaParser.parse(json).getOrThrow()
        val img = schema.body[0] as CometChatCardImageElement
        assertTrue(img.width is CometChatCardDimension.Percent)
        assertEquals(50f, (img.width as CometChatCardDimension.Percent).value)
    }

    @Test
    fun `parse Dimension as auto string`() {
        val json = """
        {
            "version": "1.0",
            "body": [],
            "style": {"width": "auto"},
            "fallbackText": "test"
        }
        """.trimIndent()
        val schema = CometChatCardSchemaParser.parse(json).getOrThrow()
        assertTrue(schema.style?.width is CometChatCardDimension.Auto)
    }

    @Test
    fun `parse ContainerStyle with all fields`() {
        val json = """
        {
            "version": "1.0",
            "body": [],
            "style": {
                "background": {"light": "#FFF", "dark": "#1E1E1E"},
                "borderRadius": 12,
                "borderColor": "#E0E0E0",
                "borderWidth": 1,
                "padding": 16,
                "maxWidth": 400,
                "maxHeight": 600,
                "width": 300,
                "height": "auto"
            },
            "fallbackText": "test"
        }
        """.trimIndent()
        val schema = CometChatCardSchemaParser.parse(json).getOrThrow()
        val style = schema.style!!
        assertTrue(style.background is CometChatCardColorOrHex.Themed)
        assertEquals(12, style.borderRadius)
        assertTrue(style.borderColor is CometChatCardColorOrHex.Hex)
        assertEquals(1, style.borderWidth)
        assertTrue(style.padding is CometChatCardPadding.Uniform)
        assertEquals(400, style.maxWidth)
        assertEquals(600, style.maxHeight)
        assertTrue(style.width is CometChatCardDimension.Dp)
        assertTrue(style.height is CometChatCardDimension.Auto)
    }

    @Test
    fun `unknown fields are silently ignored`() {
        val json = """
        {
            "version": "1.0",
            "body": [{"type": "text", "id": "t1", "content": "Hi", "unknownField": "value", "anotherUnknown": 42}],
            "fallbackText": "test",
            "extraTopLevel": true
        }
        """.trimIndent()
        val result = CometChatCardSchemaParser.parse(json)
        assertTrue(result.isSuccess)
        val text = result.getOrThrow().body[0] as CometChatCardTextElement
        assertEquals("Hi", text.content)
    }

    @Test
    fun `malformed JSON returns failure`() {
        val result = CometChatCardSchemaParser.parse("{invalid json")
        assertTrue(result.isFailure)
    }

    @Test
    fun `empty body array parses successfully`() {
        val json = """{"version": "1.0", "body": [], "fallbackText": "empty"}"""
        val result = CometChatCardSchemaParser.parse(json)
        assertTrue(result.isSuccess)
        assertEquals(0, result.getOrThrow().body.size)
    }

    @Test
    fun `round trip serialization produces equivalent model`() {
        val json = loadFixture("minimal-card.json")
        val original = CometChatCardSchemaParser.parse(json).getOrThrow()
        val serialized = CometChatCardSchemaParser.serialize(original)
        val reparsed = CometChatCardSchemaParser.parse(serialized).getOrThrow()
        assertEquals(original.version, reparsed.version)
        assertEquals(original.fallbackText, reparsed.fallbackText)
        assertEquals(original.body.size, reparsed.body.size)
        val origText = original.body[0] as CometChatCardTextElement
        val reparsedText = reparsed.body[0] as CometChatCardTextElement
        assertEquals(origText.id, reparsedText.id)
        assertEquals(origText.content, reparsedText.content)
    }

    @Test
    fun `parse accordion with string header`() {
        val json = """
        {
            "version": "1.0",
            "body": [{
                "type": "accordion", "id": "acc1",
                "header": "Section Title",
                "body": [{"type": "text", "id": "t1", "content": "Body text"}]
            }],
            "fallbackText": "test"
        }
        """.trimIndent()
        val schema = CometChatCardSchemaParser.parse(json).getOrThrow()
        val acc = schema.body[0] as CometChatCardAccordionElement
        assertTrue(acc.header is CometChatCardAccordionHeader.Text)
        assertEquals("Section Title", (acc.header as CometChatCardAccordionHeader.Text).value)
    }

    @Test
    fun `parse accordion with element array header`() {
        val json = """
        {
            "version": "1.0",
            "body": [{
                "type": "accordion", "id": "acc1",
                "header": [{"type": "text", "id": "h1", "content": "Header Text"}],
                "body": [{"type": "text", "id": "t1", "content": "Body text"}]
            }],
            "fallbackText": "test"
        }
        """.trimIndent()
        val schema = CometChatCardSchemaParser.parse(json).getOrThrow()
        val acc = schema.body[0] as CometChatCardAccordionElement
        assertTrue(acc.header is CometChatCardAccordionHeader.Elements)
        val elements = (acc.header as CometChatCardAccordionHeader.Elements).items
        assertEquals(1, elements.size)
        assertTrue(elements[0] is CometChatCardTextElement)
    }

    @Test
    fun `parse tabs element`() {
        val json = """
        {
            "version": "1.0",
            "body": [{
                "type": "tabs", "id": "tabs1",
                "tabs": [
                    {"label": "Tab 1", "content": [{"type": "text", "id": "t1", "content": "Content 1"}]},
                    {"label": "Tab 2", "content": [{"type": "text", "id": "t2", "content": "Content 2"}]}
                ],
                "defaultActiveTab": 1
            }],
            "fallbackText": "test"
        }
        """.trimIndent()
        val schema = CometChatCardSchemaParser.parse(json).getOrThrow()
        val tabs = schema.body[0] as CometChatCardTabsElement
        assertEquals(2, tabs.tabs.size)
        assertEquals("Tab 1", tabs.tabs[0].label)
        assertEquals(1, tabs.defaultActiveTab)
    }

    @Test
    fun `parse nested layout elements`() {
        val json = """
        {
            "version": "1.0",
            "body": [{
                "type": "column", "id": "col1",
                "items": [{
                    "type": "row", "id": "row1",
                    "items": [
                        {"type": "text", "id": "t1", "content": "Left"},
                        {"type": "text", "id": "t2", "content": "Right"}
                    ]
                }]
            }],
            "fallbackText": "test"
        }
        """.trimIndent()
        val schema = CometChatCardSchemaParser.parse(json).getOrThrow()
        val col = schema.body[0] as CometChatCardColumnElement
        assertEquals(1, col.items.size)
        val row = col.items[0] as CometChatCardRowElement
        assertEquals(2, row.items.size)
    }

    @Test
    fun `parse table element`() {
        val json = """
        {
            "version": "1.0",
            "body": [{
                "type": "table", "id": "tbl1",
                "columns": ["Name", "Price"],
                "rows": [["Item A", "$10"], ["Item B", "$20"]],
                "stripedRows": true,
                "border": true
            }],
            "fallbackText": "test"
        }
        """.trimIndent()
        val schema = CometChatCardSchemaParser.parse(json).getOrThrow()
        val table = schema.body[0] as CometChatCardTableElement
        assertEquals(2, table.columns.size)
        assertEquals(2, table.rows.size)
        assertEquals(true, table.stripedRows)
    }
}
