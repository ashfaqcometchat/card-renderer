package org.cometchat.cardrender.xml

import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cometchat.cards.CometChatCardView
import com.cometchat.cards.actions.CometChatCardActionCallback
import com.cometchat.cards.models.CometChatCardLogLevel
import com.cometchat.cards.models.CometChatCardThemeMode
import com.cometchat.cards.models.type
import org.cometchat.cardrender.xml.databinding.ActivityPreviewBinding

class PreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.toolbar.setNavigationOnClickListener { finish() }

        val cardJson = intent.getStringExtra("card_json") ?: return

        val cardView = CometChatCardView(this).apply {
            layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            setLogLevel(CometChatCardLogLevel.VERBOSE)
            setThemeMode(CometChatCardThemeMode.AUTO)
            setActionCallback(CometChatCardActionCallback { event ->
                Log.d(
                    "CardDemo",
                    "Action: ${event.action.type}, elementId: ${event.elementId}"
                )
            })
            setCardSchema(cardJson)
        }

        binding.frameCardView.addView(cardView)
    }
}
