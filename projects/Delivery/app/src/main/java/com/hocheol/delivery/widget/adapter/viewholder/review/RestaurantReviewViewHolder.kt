package com.hocheol.delivery.widget.adapter.viewholder.review

import android.view.View
import com.hocheol.delivery.databinding.ViewholderRestaurantReviewBinding
import com.hocheol.delivery.extensions.clear
import com.hocheol.delivery.extensions.load
import com.hocheol.delivery.model.restaurant.review.RestaurantReviewModel
import com.hocheol.delivery.screen.base.BaseViewModel
import com.hocheol.delivery.util.provider.ResourcesProvider
import com.hocheol.delivery.widget.adapter.listener.AdapterListener
import com.hocheol.delivery.widget.adapter.viewholder.ModelViewHolder

class RestaurantReviewViewHolder(
    private val binding: ViewholderRestaurantReviewBinding,
    viewModel: BaseViewModel,
    resourcesProvider: ResourcesProvider
) : ModelViewHolder<RestaurantReviewModel>(binding, viewModel, resourcesProvider) {

    override fun reset() = with(binding) {
        reviewThumbnailImageView.clear()
        reviewThumbnailImageView.visibility = View.GONE
    }

    override fun bindData(model: RestaurantReviewModel) {
        super.bindData(model)
        with(binding) {
            if (model.thumbnailImageUri != null) {
                reviewThumbnailImageView.visibility = View.VISIBLE
                reviewThumbnailImageView.load(model.thumbnailImageUri.toString(), 24f)
            } else {
                reviewThumbnailImageView.visibility = View.GONE
            }
            reviewTitleTextView.text = model.title
            ratingBar.rating = model.grade.toFloat()
            reviewTextView.text = model.description
        }
    }

    override fun bindViews(model: RestaurantReviewModel, adapterListener: AdapterListener) = Unit

}