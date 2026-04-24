package org.cometchat.cardrender.xml.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.cometchat.cards.CometChatCardView
import com.cometchat.cards.models.CometChatCardLogLevel
import com.cometchat.cards.models.CometChatCardThemeMode
import org.cometchat.cardrender.xml.databinding.ItemHistoryBinding

class HistoryAdapter(
    private val items: List<String>,
    private val onItemClick: (String) -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(
        private val binding: ItemHistoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(json: String, position: Int) {
            binding.frameMiniPreview.removeAllViews()

            val cardView = CometChatCardView(binding.root.context).apply {
                layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                )
                setLogLevel(CometChatCardLogLevel.NONE)
                setThemeMode(CometChatCardThemeMode.AUTO)
                setCardSchema(json)
            }
            binding.frameMiniPreview.addView(cardView)

            binding.root.setOnClickListener { onItemClick(json) }
            binding.btnDelete.setOnClickListener { onDeleteClick(position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size
}
