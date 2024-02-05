package com.hocheol.tomorrowhouse.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hocheol.tomorrowhouse.R
import com.hocheol.tomorrowhouse.databinding.ItemArticleBinding

class HomeArticleAdapter(
    private val onItemClicked: (ArticleItem) -> Unit,
    private val onBookmarkClicked: (String, Boolean) -> Unit
) : ListAdapter<ArticleItem, HomeArticleAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(private val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ArticleItem) {
            binding.root.setOnClickListener {
                onItemClicked(item)
            }

            Glide.with(binding.thumbnailImageView)
                .load(item.imageUrl)
                .into(binding.thumbnailImageView)

            setBookmarkButtonBackground(item.isBookMark)
            binding.bookmarkImageButton.setOnClickListener {
                onBookmarkClicked(item.articleId, item.isBookMark.not())

                item.isBookMark = item.isBookMark.not()
                setBookmarkButtonBackground(item.isBookMark)
            }

            binding.descriptionTextView.text = item.description
        }

        private fun setBookmarkButtonBackground(isBookmark: Boolean) {
            binding.bookmarkImageButton.setBackgroundResource(
                if (isBookmark) {
                    R.drawable.baseline_bookmark_24
                } else {
                    R.drawable.baseline_bookmark_border_24
                }
            )
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