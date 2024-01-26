package com.hocheol.githubrepoexplorer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hocheol.githubrepoexplorer.databinding.ItemRepoBinding
import com.hocheol.githubrepoexplorer.model.Repo

class RepoAdapter(private val onClick: (Repo) -> Unit) : ListAdapter<Repo, RepoAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(private val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Repo) {
            binding.root.setOnClickListener {
                onClick(item)
            }

            binding.repoNameTextView.text = item.name
            binding.descriptionTextView.text = item.description
            binding.starCountTextView.text = item.starCount.toString()
            binding.forkCountTextView.text = item.forkCount.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRepoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = currentList[position]
        holder.bind(repo)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem == newItem
            }
        }
    }
}