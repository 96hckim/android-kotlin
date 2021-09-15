package com.hocheol.youtube.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hocheol.youtube.R
import com.hocheol.youtube.model.VideoModel

class VideoAdapter(val callback: (String, String) -> Unit) :
    ListAdapter<VideoModel, VideoAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(videoModel: VideoModel) {
            val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
            val subTitleTextView = view.findViewById<TextView>(R.id.subTitleTextView)
            val thumbnailImageView = view.findViewById<ImageView>(R.id.thumbnailImageView)

            titleTextView.text = videoModel.title
            subTitleTextView.text = videoModel.subtitle

            Glide.with(thumbnailImageView.context)
                .load(videoModel.thumb)
                .into(thumbnailImageView)

            view.setOnClickListener {
                callback(videoModel.sources, videoModel.title)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.item_video, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<VideoModel>() {

            override fun areItemsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: VideoModel, newItem: VideoModel): Boolean {
                return oldItem == newItem
            }

        }
    }

}