package com.hocheol.delivery.screen.main.home.restaurant.detail

import android.content.Context
import android.content.Intent
import com.hocheol.delivery.data.entity.RestaurantEntity
import com.hocheol.delivery.databinding.ActivityRestaurantDetailBinding
import com.hocheol.delivery.screen.base.BaseActivity
import com.hocheol.delivery.screen.main.home.restaurant.RestaurantListFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RestaurantDetailActivity : BaseActivity<RestaurantDetailViewModel, ActivityRestaurantDetailBinding>() {

    override val viewModel by viewModel<RestaurantDetailViewModel> {
        parametersOf(
            intent.getParcelableExtra<RestaurantEntity>(RestaurantListFragment.RESTAURANT_KEY)
        )
    }

    override fun getViewBinding(): ActivityRestaurantDetailBinding =
        ActivityRestaurantDetailBinding.inflate(layoutInflater)

    override fun observeData() = viewModel.restaurantDetailStateLiveData.observe(this) {
        when (it) {
            is RestaurantDetailState.Success -> {

            }
        }
    }

    companion object {

        fun newIntent(context: Context, restaurantEntity: RestaurantEntity) =
            Intent(context, RestaurantDetailActivity::class.java).apply {
                putExtra(RestaurantListFragment.RESTAURANT_KEY, restaurantEntity)
            }

    }

}