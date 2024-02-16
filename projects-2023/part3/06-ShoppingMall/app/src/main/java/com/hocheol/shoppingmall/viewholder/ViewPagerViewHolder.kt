package com.hocheol.shoppingmall.viewholder

import com.hocheol.shoppingmall.ListAdapter
import com.hocheol.shoppingmall.databinding.ItemViewpagerBinding
import com.hocheol.shoppingmall.model.ListItem
import com.hocheol.shoppingmall.model.ViewPager

class ViewPagerViewHolder(
    binding: ItemViewpagerBinding
) : BindingViewHolder<ItemViewpagerBinding>(binding) {

    private val adapter = ListAdapter()

    init {
        binding.viewPager.adapter = adapter
    }

    override fun bind(item: ListItem) {
        super.bind(item)
        item as ViewPager
        adapter.submitList(item.items)
    }
}