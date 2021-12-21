package com.hocheol.delivery.widget.adapter.viewholder.order

import com.hocheol.delivery.R
import com.hocheol.delivery.databinding.ViewholderOrderBinding
import com.hocheol.delivery.model.restaurant.order.OrderModel
import com.hocheol.delivery.screen.base.BaseViewModel
import com.hocheol.delivery.util.provider.ResourcesProvider
import com.hocheol.delivery.widget.adapter.listener.AdapterListener
import com.hocheol.delivery.widget.adapter.listener.order.OrderListListener
import com.hocheol.delivery.widget.adapter.viewholder.ModelViewHolder

class OrderViewHolder(
    private val binding: ViewholderOrderBinding,
    viewModel: BaseViewModel,
    resourcesProvider: ResourcesProvider
) : ModelViewHolder<OrderModel>(binding, viewModel, resourcesProvider) {

    override fun reset() = Unit

    override fun bindData(model: OrderModel) {
        super.bindData(model)
        with(binding) {
            orderTitleTextView.text = resourcesProvider.getString(R.string.order_history_title, model.orderId)

            val foodMenuList = model.foodMenuList

            foodMenuList
                .groupBy { it.title }
                .entries.forEach { (title, menuList) ->
                    val orderDataStr =
                        orderContentTextView.text.toString() + "메뉴 : $title | 가격 : ${menuList.first().price}원 X ${menuList.size}\n"
                    orderContentTextView.text = orderDataStr
                }
            orderContentTextView.text = orderContentTextView.text.trim()

            orderTotalPriceTextView.text =
                resourcesProvider.getString(
                    R.string.price,
                    foodMenuList.map { it.price }.reduce { total, price -> total + price }
                )
        }
    }

    override fun bindViews(model: OrderModel, adapterListener: AdapterListener) {
        if (adapterListener is OrderListListener) {
            binding.root.setOnClickListener {
                adapterListener.writeRestaurantReview(model.orderId, model.restaurantTitle)
            }
        }
    }

}