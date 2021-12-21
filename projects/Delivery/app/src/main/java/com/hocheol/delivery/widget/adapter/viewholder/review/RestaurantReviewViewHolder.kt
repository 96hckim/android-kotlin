package com.hocheol.delivery.widget.adapter.viewholder.review

import android.view.View
import com.hocheol.delivery.databinding.ViewholderRestaurantReviewBinding
import com.hocheol.delivery.extensions.clear
import com.hocheol.delivery.extensions.load
import com.hocheol.delivery.model.restaurant.review.ReviewModel
import com.hocheol.delivery.screen.base.BaseViewModel
import com.hocheol.delivery.util.provider.ResourcesProvider
import com.hocheol.delivery.widget.adapter.listener.AdapterListener
import com.hocheol.delivery.widget.adapter.viewholder.ModelViewHolder

class RestaurantReviewViewHolder(
    private val binding: ViewholderRestaurantReviewBinding,
    viewModel: BaseViewModel,
    resourcesProvider: ResourcesProvider
) : ModelViewHolder<ReviewModel>(binding, viewModel, resourcesProvider) {

    override fun reset() = with(binding) {
        reviewThumbnailImageView.clear()
        reviewThumbnailImageView.visibility = View.GONE
    }

    override fun bindData(model: ReviewModel) {
        super.bindData(model)
        with(binding) {
            if (model.thumbnailImageUri != null) {
                reviewThumbnailImageView.visibility = View.VISIBLE
                reviewThumbnailImageView.load(model.thumbnailImageUri.toString())
            } else {
                reviewThumbnailImageView.visibility = View.GONE
            }
            reviewTitleTextView.text = model.title
            ratingBar.rating = model.grade
            reviewTextView.text = model.description
        }
    }

    override fun bindViews(model: ReviewModel, adapterListener: AdapterListener) = Unit

}