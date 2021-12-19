package com.hocheol.delivery.widget.adapter.viewholder.food

import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.hocheol.delivery.R
import com.hocheol.delivery.databinding.ViewholderFoodMenuBinding
import com.hocheol.delivery.extensions.clear
import com.hocheol.delivery.extensions.load
import com.hocheol.delivery.model.restaurant.food.FoodModel
import com.hocheol.delivery.screen.base.BaseViewModel
import com.hocheol.delivery.util.provider.ResourcesProvider
import com.hocheol.delivery.widget.adapter.listener.AdapterListener
import com.hocheol.delivery.widget.adapter.viewholder.ModelViewHolder

class FoodMenuViewHolder(
    private val binding: ViewholderFoodMenuBinding,
    viewModel: BaseViewModel,
    resourcesProvider: ResourcesProvider
) : ModelViewHolder<FoodModel>(binding, viewModel, resourcesProvider) {

    override fun reset() = with(binding) {
        foodImageView.clear()
    }

    override fun bindData(model: FoodModel) {
        super.bindData(model)
        with(binding) {
            foodImageView.load(model.imageUrl, 24f, CenterCrop())
            foodTitleTextView.text = model.title
            foodDescriptionTextView.text = model.description
            priceTextView.text = resourcesProvider.getString(R.string.price, model.price)
        }
    }

    override fun bindViews(model: FoodModel, adapterListener: AdapterListener) {
    }

}