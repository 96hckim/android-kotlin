package com.hocheol.locationsearchmap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hocheol.locationsearchmap.databinding.ItemSearchBinding

class SearchListAdapter(
    private val searchResultCLickListener: (Any) -> Unit
) : ListAdapter<Any, SearchListAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Any) = with(binding) {
            binding.root.setOnClickListener {
                searchResultCLickListener(item)
            }
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
        holder.bind(currentList)
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Any>() {

            override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
                return oldItem == newItem
            }

        }
    }

}