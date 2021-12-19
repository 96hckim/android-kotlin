package com.hocheol.delivery.screen.main.home.restaurant.detail.review

import androidx.core.os.bundleOf
import com.hocheol.delivery.databinding.FragmentRestaurantListBinding
import com.hocheol.delivery.screen.base.BaseFragment
import org.koin.android.ext.android.inject

class RestaurantReviewListFragment : BaseFragment<RestaurantReviewListViewModel, FragmentRestaurantListBinding>() {

    override val viewModel by inject<RestaurantReviewListViewModel>()

    override fun getViewBinding(): FragmentRestaurantListBinding = FragmentRestaurantListBinding.inflate(layoutInflater)

    override fun observeData() {
    }

    companion object {

        const val RESTAURANT_ID_KEY = "restaurantId"

        fun newInstance(restaurantId: Long): RestaurantReviewListFragment {
            val bundle = bundleOf(
                RESTAURANT_ID_KEY to restaurantId
            )
            return RestaurantReviewListFragment().apply {
                arguments = bundle
            }
        }

    }

}