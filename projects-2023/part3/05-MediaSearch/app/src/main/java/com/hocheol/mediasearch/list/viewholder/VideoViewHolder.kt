package com.hocheol.mediasearch.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.hocheol.mediasearch.databinding.ItemVideoBinding
import com.hocheol.mediasearch.model.ListItem
import com.hocheol.mediasearch.model.VideoItem

class VideoViewHolder(
    private val binding: ItemVideoBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ListItem) {
        binding.item = item as VideoItem
    }
}