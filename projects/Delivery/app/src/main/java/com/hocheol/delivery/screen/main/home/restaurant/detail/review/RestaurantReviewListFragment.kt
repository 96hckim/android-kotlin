package com.hocheol.delivery.screen.main.home.restaurant.detail.review

import androidx.core.os.bundleOf
import com.hocheol.delivery.databinding.FragmentRestaurantListBinding
import com.hocheol.delivery.model.restaurant.review.RestaurantReviewModel
import com.hocheol.delivery.screen.base.BaseFragment
import com.hocheol.delivery.util.provider.ResourcesProvider
import com.hocheol.delivery.widget.adapter.ModelRecyclerAdapter
import com.hocheol.delivery.widget.adapter.listener.AdapterListener
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class RestaurantReviewListFragment : BaseFragment<RestaurantReviewListViewModel, FragmentRestaurantListBinding>() {

    override val viewModel by inject<RestaurantReviewListViewModel> {
        parametersOf(
            arguments?.getString(RESTAURANT_ID_KEY)
        )
    }

    override fun getViewBinding(): FragmentRestaurantListBinding = FragmentRestaurantListBinding.inflate(layoutInflater)

    private val resourcesProvider by inject<ResourcesProvider>()

    private val adapter by lazy {
        ModelRecyclerAdapter<RestaurantReviewModel, RestaurantReviewListViewModel>(
            modelList = listOf(),
            viewModel = viewModel,
            adapterListener = object : AdapterListener {},
            resourcesProvider = resourcesProvider
        )
    }

    override fun initViews() {
        binding.recyclerView.adapter = adapter
    }

    override fun observeData() = viewModel.reviewStateLiveData.observe(viewLifecycleOwner) {
        when (it) {
            is RestaurantReviewState.Success -> {
                handleSuccess(it)
            }
            else -> Unit
        }
    }

    private fun handleSuccess(state: RestaurantReviewState.Success) {
        adapter.submitList(state.reviewList)
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