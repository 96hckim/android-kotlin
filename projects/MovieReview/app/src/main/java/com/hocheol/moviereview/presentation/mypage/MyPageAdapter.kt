package com.hocheol.moviereview.presentation.mypage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hocheol.moviereview.databinding.ItemReviewedMovieBinding
import com.hocheol.moviereview.domain.model.Movie
import com.hocheol.moviereview.domain.model.ReviewedMovie
import com.hocheol.moviereview.extension.toDecimalFormatString

class MyPageAdapter : RecyclerView.Adapter<MyPageAdapter.ViewHolder>() {

    var reviewedMovies: List<ReviewedMovie> = emptyList()
    var onMovieClickListener: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemReviewedMovieBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemCount(): Int = reviewedMovies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int): Unit =
        holder.bind(reviewedMovies[position])

    inner class ViewHolder(private val binding: ItemReviewedMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onMovieClickListener?.invoke(reviewedMovies[adapterPosition].movie)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(item: ReviewedMovie) {
            Glide.with(binding.root)
                .load(item.movie.posterUrl)
                .into(binding.posterImageView)

            binding.myScoreTextView.text = item.myReview.score?.toDecimalFormatString("0.0")
        }
    }

}
