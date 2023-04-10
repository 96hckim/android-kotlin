package com.hocheol.wordbook

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hocheol.wordbook.databinding.ItemWordBinding

class WordAdapter(
    val list: MutableList<Word>,
    private val itemClickListener: ItemClickListener? = null
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemWordBinding.inflate(inflater, parent, false)
        return WordViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            itemClickListener?.onClick(item)
        }
    }

    class WordViewHolder(private val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Word) = with(binding) {
            wordTextView.text = item.text
            meanTextView.text = item.mean
            typeChip.text = item.type
        }
    }

    interface ItemClickListener {
        fun onClick(word: Word)
    }
}