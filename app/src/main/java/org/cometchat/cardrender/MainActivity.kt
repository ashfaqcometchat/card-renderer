package org.cometchat.cardrender

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cometchat.cards.CometChatCardComposable
import com.cometchat.cards.models.CometChatCardLogLevel
import com.cometchat.cards.models.CometChatCardThemeMode
import com.cometchat.cards.models.type
import java.net.URLDecoder
import java.net.URLEncoder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme(colorScheme = dynamicColorScheme()) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CardDemoApp()
                }
            }
        }
    }
}

@Composable
private fun dynamicColorScheme(): ColorScheme {
    return darkColorScheme()
}


@Composable
fun CardDemoApp() {
    val navController = rememberNavController()
    val history = remember { mutableStateListOf<String>() }

    // Seed with sample cards
    LaunchedEffect(Unit) {
        if (history.isEmpty()) {
            history.addAll(listOf(PRODUCT_CARD, ALL_ELEMENTS_CARD, NESTED_LAYOUT_CARD, TABLE_CARD))
        }
    }

    NavHost(
        navController = navController,
        startDestination = "home",
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(300)) },
        exitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, tween(300)) },
        popEnterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300)) },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, tween(300)) }
    ) {
        composable("home") {
            HomeScreen(
                history = history,
                onRender = { json ->
                    if (!history.contains(json)) {
                        history.add(0, json)
                    }
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("preview/$encoded")
                },
                onHistoryItemClick = { json ->
                    val encoded = URLEncoder.encode(json, "UTF-8")
                    navController.navigate("preview/$encoded")
                },
                onDeleteHistoryItem = { json -> history.remove(json) }
            )
        }
        composable("preview/{cardJson}") { backStackEntry ->
            val encoded = backStackEntry.arguments?.getString("cardJson") ?: ""
            val json = URLDecoder.decode(encoded, "UTF-8")
            PreviewScreen(
                cardJson = json,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    history: List<String>,
    onRender: (String) -> Unit,
    onHistoryItemClick: (String) -> Unit,
    onDeleteHistoryItem: (String) -> Unit
) {
    var jsonInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CometChat Card Renderer") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            // JSON Input Section
            item {
                Text(
                    "Enter Card JSON",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = jsonInput,
                    onValueChange = { jsonInput = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 150.dp, max = 300.dp),
                    placeholder = { Text("Paste your Card Schema JSON here...", fontSize = 13.sp) },
                    textStyle = LocalTextStyle.current.copy(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 12.sp
                    ),
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        if (jsonInput.isNotBlank()) {
                            onRender(jsonInput.trim())
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = jsonInput.isNotBlank(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Render Card")
                }
            }

            // History Section
            if (history.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Rendered Cards",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                items(history, key = { it.hashCode() }) { json ->
                    HistoryCard(
                        json = json,
                        onClick = { onHistoryItemClick(json) },
                        onDelete = { onDeleteHistoryItem(json) }
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryCard(json: String, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Mini preview of the rendered card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(8.dp)
            ) {
                CometChatCardComposable(
                    cardJson = json,
                    themeMode = CometChatCardThemeMode.AUTO,
                    logLevel = CometChatCardLogLevel.NONE,
                    onAction = { /* no-op in preview */ }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // JSON snippet + delete
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = json.take(80).replace("\n", " ") + "...",
                    style = MaterialTheme.typography.bodySmall,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 10.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f)
                )
                IconButton(onClick = onDelete, modifier = Modifier.size(32.dp)) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewScreen(cardJson: String, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Card Preview") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
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
    }
}

private val PRODUCT_CARD = """{"version":"1.0","body":[{"id":"img_1","type":"image","url":"https://picsum.photos/400/200","fit":"cover","height":200,"borderRadius":8},{"id":"sp_1","type":"spacer","height":8},{"id":"txt_1","type":"text","content":"Sneaker X — Limited Edition","variant":"heading2","color":{"light":"#141414","dark":"#E8E8E8"}},{"id":"txt_2","type":"text","content":"${'$'}129.99","variant":"heading3","color":"#3A3AF4"},{"id":"sp_2","type":"spacer","height":8},{"id":"row_1","type":"row","gap":8,"items":[{"id":"btn_1","type":"button","label":"Buy Now","variant":"filled","backgroundColor":{"light":"#3A3AF4","dark":"#5A5AF6"},"textColor":"#FFFFFF","action":{"type":"openUrl","url":"https://example.com"}},{"id":"btn_2","type":"button","label":"Add to Cart","variant":"outlined","backgroundColor":{"light":"#3A3AF4","dark":"#5A5AF6"},"action":{"type":"customCallback","callbackId":"add_to_cart"}}]}],"style":{"background":{"light":"#FFFFFF","dark":"#1E1E1E"},"borderRadius":12,"padding":16},"fallbackText":"Sneaker X"}""".trimIndent()

private val ALL_ELEMENTS_CARD = """{"version":"1.0","body":[{"id":"t1","type":"text","content":"All Content Elements","variant":"heading1"},{"id":"d1","type":"divider"},{"id":"sp1","type":"spacer","height":8},{"id":"av1","type":"avatar","fallbackInitials":"JD","size":48,"backgroundColor":"#3A3AF4"},{"id":"sp2","type":"spacer","height":8},{"id":"b1","type":"badge","text":"NEW","backgroundColor":"#3A3AF4","color":"#FFFFFF","borderRadius":10},{"id":"sp3","type":"spacer","height":8},{"id":"ch1","type":"chip","text":"Android","backgroundColor":"#E8E8E8","borderRadius":14},{"id":"sp4","type":"spacer","height":8},{"id":"pb1","type":"progressBar","value":72,"label":"Progress: 72%","color":"#3A3AF4","height":8,"borderRadius":4},{"id":"sp5","type":"spacer","height":8},{"id":"cb1","type":"codeBlock","content":"val card = CometChatCardView(context)","language":"Kotlin"},{"id":"sp6","type":"spacer","height":8},{"id":"md1","type":"markdown","content":"This is **bold** and *italic* text."},{"id":"sp7","type":"spacer","height":8},{"id":"lnk1","type":"link","text":"Visit CometChat","action":{"type":"openUrl","url":"https://cometchat.com"}}],"fallbackText":"All elements"}""".trimIndent()

private val NESTED_LAYOUT_CARD = """{"version":"1.0","body":[{"id":"t1","type":"text","content":"Nested Layouts","variant":"heading2"},{"id":"sp1","type":"spacer","height":8},{"id":"col1","type":"column","gap":8,"backgroundColor":"#F5F5F5","borderRadius":8,"padding":12,"items":[{"id":"row1","type":"row","gap":8,"items":[{"id":"av1","type":"avatar","fallbackInitials":"A","size":36,"backgroundColor":"#3A3AF4"},{"id":"t2","type":"text","content":"User A sent a message","variant":"body"}]},{"id":"d1","type":"divider"},{"id":"row2","type":"row","gap":8,"items":[{"id":"av2","type":"avatar","fallbackInitials":"B","size":36,"backgroundColor":"#FF6B6B"},{"id":"t3","type":"text","content":"User B replied","variant":"body"}]}]},{"id":"sp2","type":"spacer","height":12},{"id":"acc1","type":"accordion","header":"Show Details","body":[{"id":"t4","type":"text","content":"Hidden details inside accordion.","variant":"body2"}],"border":true,"borderRadius":8}],"fallbackText":"Nested layout"}""".trimIndent()

private val TABLE_CARD = """{"version":"1.0","body":[{"id":"t1","type":"text","content":"Order Summary","variant":"heading2"},{"id":"sp1","type":"spacer","height":8},{"id":"tbl1","type":"table","columns":["Item","Qty","Price"],"rows":[["Widget A","2","${'$'}10"],["Widget B","1","${'$'}25"],["Widget C","3","${'$'}7.50"]],"stripedRows":true,"border":true,"headerBackgroundColor":"#3A3AF4"},{"id":"sp2","type":"spacer","height":12},{"id":"btn1","type":"button","label":"Place Order","variant":"filled","fullWidth":true,"backgroundColor":"#3A3AF4","textColor":"#FFFFFF","action":{"type":"customCallback","callbackId":"place_order"}}],"style":{"padding":16,"borderRadius":12},"fallbackText":"Order summary"}""".trimIndent()
