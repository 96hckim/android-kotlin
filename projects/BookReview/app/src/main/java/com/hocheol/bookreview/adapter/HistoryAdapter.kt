package com.hocheol.bookreview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hocheol.bookreview.databinding.ItemHistoryBinding
import com.hocheol.bookreview.model.History

class HistoryAdapter(
    val historyKeywordClickedListener: (String) -> Unit,
    val historyDeleteClickedListener: (String) -> Unit
) :
    ListAdapter<History, HistoryAdapter.HistoryItemViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        return HistoryItemViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class HistoryItemViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(historyModel: History) {
            binding.historyKeywordTextview.text = historyModel.keyword

            binding.historyKeywordTextview.setOnClickListener {
                historyKeywordClickedListener(historyModel.keyword.orEmpty())
            }

            binding.historyKeywordDeleteButton.setOnClickListener {
                historyDeleteClickedListener(historyModel.keyword.orEmpty())
            }
        }

    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<History>() {

            override fun areItemsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: History, newItem: History): Boolean {
                return oldItem.keyword == newItem.keyword
            }

        }
    }

}