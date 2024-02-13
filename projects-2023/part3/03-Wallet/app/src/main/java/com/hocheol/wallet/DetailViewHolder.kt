package com.hocheol.wallet

import androidx.recyclerview.widget.RecyclerView
import com.hocheol.wallet.databinding.ItemDetailBinding
import com.hocheol.wallet.model.DetailItem

class DetailViewHolder(private val binding: ItemDetailBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DetailItem) {
        binding.item = item
    }
}