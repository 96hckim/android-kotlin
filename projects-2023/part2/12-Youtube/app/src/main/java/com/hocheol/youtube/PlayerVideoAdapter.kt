package com.hocheol.youtube

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hocheol.youtube.databinding.ItemVideoBinding
import com.hocheol.youtube.databinding.ItemVideoHeaderBinding

class PlayerVideoAdapter(
    private val context: Context,
    private val onClick: (Video) -> Unit
) : ListAdapter<Video, RecyclerView.ViewHolder>(diffCallback) {

    inner class HeaderViewHolder(private val binding: ItemVideoHeaderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Video) {
            binding.titleTextView.text = item.title

            binding.subTitleTextView.text = context.getString(R.string.header_sub_title_video_info, item.viewCount, item.dateText)

            binding.channelNameTextView.text = item.channelName

            Glide.with(binding.channelThumbnailImageView)
                .load(item.channelThumb)
                .circleCrop()
                .into(binding.channelThumbnailImageView)
        }
    }

    inner class VideoViewHolder(private val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Video) {
            binding.root.setOnClickListener {
                onClick(item)
            }

            Glide.with(binding.videoThumbnailImageView)
                .load(item.videoThumb)
                .into(binding.videoThumbnailImageView)

            Glide.with(binding.channelThumbnailImageView)
                .load(item.channelThumb)
                .circleCrop()
                .into(binding.channelThumbnailImageView)

            binding.titleTextView.text = item.title

            binding.subTitleTextView.text = context.getString(R.string.sub_title_video_info, item.channelName, item.viewCount, item.dateText)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_HEADER -> {
                HeaderViewHolder(
                    ItemVideoHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

            else -> {
                VideoViewHolder(
                    ItemVideoBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                holder.bind(currentList[position])
            }

            is VideoViewHolder -> {
                holder.bind(currentList[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            VIEW_TYPE_HEADER
        } else {
            VIEW_TYPE_VIDEO
        }
    }

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_VIDEO = 1

        val diffCallback = object : DiffUtil.ItemCallback<Video>() {
            override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
                return oldItem == newItem
            }
        }
    }
}