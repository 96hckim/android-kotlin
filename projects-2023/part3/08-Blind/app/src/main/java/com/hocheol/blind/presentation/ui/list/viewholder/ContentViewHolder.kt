package com.hocheol.blind.presentation.ui.list.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.hocheol.blind.databinding.ItemContentBinding
import com.hocheol.blind.domain.model.Content
import com.hocheol.blind.presentation.ui.MainActivity

class ContentViewHolder(
    private val binding: ItemContentBinding,
    private val handler: MainActivity.Handler
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Content) {
        binding.item = item
        binding.handler = handler
    }
}