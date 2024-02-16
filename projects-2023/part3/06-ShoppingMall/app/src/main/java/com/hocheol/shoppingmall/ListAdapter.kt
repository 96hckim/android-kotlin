package com.hocheol.shoppingmall

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.hocheol.shoppingmall.model.ListItem
import com.hocheol.shoppingmall.viewholder.BindingViewHolder
import com.hocheol.shoppingmall.viewholder.ViewHolderGenerator

class ListAdapter : ListAdapter<ListItem, BindingViewHolder<*>>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<*> {
        return ViewHolderGenerator.get(parent, viewType)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<*>, position: Int) {
        val item = getItem(position) ?: return
        holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return item?.viewType?.ordinal ?: -1
    }
}