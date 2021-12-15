package com.hocheol.delivery.util.mapper

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hocheol.delivery.databinding.ViewholderEmptyBinding
import com.hocheol.delivery.databinding.ViewholderRestaurantBinding
import com.hocheol.delivery.model.CellType
import com.hocheol.delivery.model.Model
import com.hocheol.delivery.screen.base.BaseViewModel
import com.hocheol.delivery.util.provider.ResourcesProvider
import com.hocheol.delivery.widget.adapter.viewholder.EmptyViewHolder
import com.hocheol.delivery.widget.adapter.viewholder.ModelViewHolder
import com.hocheol.delivery.widget.adapter.viewholder.restaurant.RestaurantViewHolder

object ModelViewHolderMapper {

    @Suppress("UNCHECKED_CAST")
    fun <M : Model> map(
        parent: ViewGroup,
        type: CellType,
        viewModel: BaseViewModel,
        resourcesProvider: ResourcesProvider
    ): ModelViewHolder<M> {
        val inflater = LayoutInflater.from(parent.context)

        val viewHolder = when (type) {
            CellType.EMPTY_CELL -> EmptyViewHolder(
                ViewholderEmptyBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )
            CellType.RESTAURANT_CELL -> RestaurantViewHolder(
                ViewholderRestaurantBinding.inflate(inflater, parent, false),
                viewModel,
                resourcesProvider
            )
        }

        return viewHolder as ModelViewHolder<M>
    }

}