package com.hocheol.unsplash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hocheol.unsplash.data.models.PhotoResponse
import com.hocheol.unsplash.databinding.ItemPhotoBinding

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    var photos: List<PhotoResponse> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int = photos.size

    class ViewHolder(
        private val binding: ItemPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: PhotoResponse) {
            val dimensionRatio = photo.height / photo.width.toFloat()
            val targetWidth = binding.root.resources.displayMetrics.widthPixels -
                    (binding.root.paddingStart + binding.root.paddingEnd)
            val targetHeight = (targetWidth * dimensionRatio).toInt()

            binding.contentsContainer.layoutParams =
                binding.contentsContainer.layoutParams.apply {
                    height = targetHeight
                }


            Glide.with(binding.root)
                .load(photo.urls?.regular)
                .into(binding.photoImageView)

            Glide.with(binding.root)
                .load(photo.user?.profileImageUrls?.small)
                .into(binding.profileImageView)

            if (photo.user?.name.isNullOrBlank()) {
                binding.authorTextView.visibility = View.GONE
            } else {
                binding.authorTextView.text = photo.user?.name
            }


            if (photo.user?.name.isNullOrBlank()) {
                binding.descriptionTextView.visibility = View.GONE
            } else {
                binding.descriptionTextView.text = photo.description
            }
        }

    }

}