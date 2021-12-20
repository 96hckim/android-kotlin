package com.hocheol.delivery.screen.main.home.restaurant.detail.review

import android.widget.Toast
import androidx.core.os.bundleOf
import com.hocheol.delivery.databinding.FragmentRestaurantListBinding
import com.hocheol.delivery.screen.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class RestaurantReviewListFragment : BaseFragment<RestaurantReviewListViewModel, FragmentRestaurantListBinding>() {

    override val viewModel by inject<RestaurantReviewListViewModel> {
        parametersOf(
            arguments?.getString(RESTAURANT_ID_KEY)
        )
    }

    override fun getViewBinding(): FragmentRestaurantListBinding = FragmentRestaurantListBinding.inflate(layoutInflater)

    override fun observeData() = viewModel.reviewStateLiveData.observe(viewLifecycleOwner) {
        when (it) {
            is RestaurantReviewState.Success -> {
                handleSuccess(it)
            }
            else -> Unit
        }
    }

    private fun handleSuccess(state: RestaurantReviewState.Success) {
        Toast.makeText(requireContext(), state.reviewList.toString(), Toast.LENGTH_SHORT).show()
    }

    companion object {

        const val RESTAURANT_ID_KEY = "restaurantId"

        fun newInstance(restaurantTitle: String): RestaurantReviewListFragment {
            val bundle = bundleOf(
                RESTAURANT_ID_KEY to restaurantTitle
            )
            return RestaurantReviewListFragment().apply {
                arguments = bundle
            }
        }

    }

}