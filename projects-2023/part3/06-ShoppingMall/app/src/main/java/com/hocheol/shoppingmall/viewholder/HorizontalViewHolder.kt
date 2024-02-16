package com.hocheol.shoppingmall.viewholder

import com.hocheol.shoppingmall.ListAdapter
import com.hocheol.shoppingmall.databinding.ItemHorizontalBinding
import com.hocheol.shoppingmall.model.Horizontal
import com.hocheol.shoppingmall.model.ListItem

class HorizontalViewHolder(
    private val binding: ItemHorizontalBinding
) : BindingViewHolder<ItemHorizontalBinding>(binding) {

    private val adapter = ListAdapter()

    init {
        binding.recyclerView.adapter = adapter
    }

    override fun bind(item: ListItem) {
        super.bind(item)
        item as Horizontal
        binding.titleTextView.text = item.title
        adapter.submitList(item.items)
    }
}