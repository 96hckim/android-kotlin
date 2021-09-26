package com.hocheol.githubrepository.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hocheol.githubrepository.data.entity.GithubRepoEntity
import com.hocheol.githubrepository.databinding.ItemRepositoryBinding
import com.hocheol.githubrepository.extensions.loadCenterInside

class RepositoryAdapter(
    private val searchResultItemClickListener: (GithubRepoEntity) -> Unit
) : ListAdapter<GithubRepoEntity, RepositoryAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GithubRepoEntity) = with(binding) {
            ownerProfileImageView.loadCenterInside(item.owner.avatarUrl, 24f)
            ownerNameTextView.text = item.owner.login
            nameTextView.text = item.fullName
            subtextTextView.text = item.description
            stargazersCountText.text = item.stargazersCount.toString()
            item.language?.let { language ->
                languageText.isGone = false
                languageText.text = language
            } ?: kotlin.run {
                languageText.isGone = false
                languageText.text = ""
            }

            root.setOnClickListener {
                searchResultItemClickListener(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<GithubRepoEntity>() {

            override fun areItemsTheSame(oldItem: GithubRepoEntity, newItem: GithubRepoEntity): Boolean {
                return oldItem.fullName == newItem.fullName
            }

            override fun areContentsTheSame(oldItem: GithubRepoEntity, newItem: GithubRepoEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

}