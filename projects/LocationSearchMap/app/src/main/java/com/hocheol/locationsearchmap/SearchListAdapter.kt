package com.hocheol.locationsearchmap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hocheol.locationsearchmap.databinding.ItemSearchBinding
import com.hocheol.locationsearchmap.model.SearchResultEntity

class SearchListAdapter(
    private val searchResultCLickListener: (SearchResultEntity) -> Unit
) : ListAdapter<SearchResultEntity, SearchListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: SearchResultEntity) = with(binding) {
            root.setOnClickListener {
                searchResultCLickListener(item)
            }

            locationTextView.text = item.fullAddress
            buildingTextView.text = item.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<SearchResultEntity>() {

            override fun areItemsTheSame(oldItem: SearchResultEntity, newItem: SearchResultEntity): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SearchResultEntity, newItem: SearchResultEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

}