package com.hocheol.tomorrowhouse.ui.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hocheol.tomorrowhouse.databinding.ItemArticleBinding
import com.hocheol.tomorrowhouse.ui.home.ArticleItem

class BookmarkArticleAdapter(
    private val onItemClicked: (ArticleItem) -> Unit
) : ListAdapter<ArticleItem, BookmarkArticleAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArticleItem) {
            binding.root.setOnClickListener {
                onItemClicked(item)
            }

            binding.bookmarkImageButton.isVisible = false

            Glide.with(binding.thumbnailImageView)
                .load(item.imageUrl)
                .into(binding.thumbnailImageView)

            binding.descriptionTextView.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemArticleBinding.inflate(
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
        val diffCallback = object : DiffUtil.ItemCallback<ArticleItem>() {
            override fun areItemsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
                return oldItem.articleId == newItem.articleId
            }

            override fun areContentsTheSame(oldItem: ArticleItem, newItem: ArticleItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}