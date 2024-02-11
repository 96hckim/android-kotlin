package com.hocheol.youtube.player

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hocheol.youtube.R
import com.hocheol.youtube.databinding.ItemVideoBinding
import com.hocheol.youtube.databinding.ItemVideoHeaderBinding

class PlayerVideoAdapter(
    private val context: Context,
    private val onClick: (PlayerVideo) -> Unit
) : ListAdapter<PlayerVideoModel, RecyclerView.ViewHolder>(diffCallback) {

    inner class HeaderViewHolder(private val binding: ItemVideoHeaderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlayerHeader) {
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

        fun bind(item: PlayerVideo) {
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
                holder.bind(currentList[position] as PlayerHeader)
            }

            is VideoViewHolder -> {
                holder.bind(currentList[position] as PlayerVideo)
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

        val diffCallback = object : DiffUtil.ItemCallback<PlayerVideoModel>() {
            override fun areItemsTheSame(oldItem: PlayerVideoModel, newItem: PlayerVideoModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PlayerVideoModel, newItem: PlayerVideoModel): Boolean {
                return if (oldItem is PlayerVideo && newItem is PlayerVideo) {
                    oldItem == newItem
                } else if (oldItem is PlayerHeader && newItem is PlayerHeader) {
                    oldItem == newItem
                } else {
                    false
                }
            }
        }
    }
}