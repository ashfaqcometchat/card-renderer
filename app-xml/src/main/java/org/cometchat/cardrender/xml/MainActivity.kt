package org.cometchat.cardrender.xml

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import org.cometchat.cardrender.xml.adapter.HistoryAdapter
import org.cometchat.cardrender.xml.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val historyList = ArrayList<String>()
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        historyAdapter = HistoryAdapter(
            items = historyList,
            onItemClick = { json -> openPreview(json) },
            onDeleteClick = { position -> removeHistoryItem(position) }
        )

        binding.recyclerHistory.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = historyAdapter
        }

        binding.btnRender.setOnClickListener {
            val json = binding.editJsonInput.text.toString().trim()
            if (json.isNotBlank()) {
                addToHistory(json)
                openPreview(json)
            }
        }
    }

    private fun openPreview(json: String) {
        val intent = Intent(this, PreviewActivity::class.java)
        intent.putExtra("card_json", json)
        startActivity(intent)
    }

    private fun addToHistory(json: String) {
        val existingIndex = historyList.indexOf(json)
        if (existingIndex >= 0) {
            historyList.removeAt(existingIndex)
            historyAdapter.notifyItemRemoved(existingIndex)
        }
        historyList.add(0, json)
        historyAdapter.notifyItemInserted(0)
        updateHistoryVisibility()
    }

    private fun removeHistoryItem(position: Int) {
        if (position in historyList.indices) {
            historyList.removeAt(position)
            historyAdapter.notifyItemRemoved(position)
            updateHistoryVisibility()
        }
    }

    private fun updateHistoryVisibility() {
        val visible = historyList.isNotEmpty()
        binding.txtHistoryHeader.visibility = if (visible) View.VISIBLE else View.GONE
    }
}
