package com.hocheol.youtube

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hocheol.youtube.databinding.ItemVideoBinding

class VideoAdapter(
    private val context: Context,
    private val onClick: (Video) -> Unit
) : ListAdapter<Video, VideoAdapter.ViewHolder>(diffCallback) {

    inner class ViewHolder(private val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemVideoBinding.inflate(
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