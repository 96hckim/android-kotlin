package com.hocheol.mediasearch.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.hocheol.mediasearch.databinding.ItemImageBinding
import com.hocheol.mediasearch.list.ItemHandler
import com.hocheol.mediasearch.model.ImageItem
import com.hocheol.mediasearch.model.ListItem

class ImageViewHolder(
    private val binding: ItemImageBinding,
    private val itemHandler: ItemHandler? = null
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ListItem) {
        binding.item = item as ImageItem
        binding.itemHandler = itemHandler
    }
}