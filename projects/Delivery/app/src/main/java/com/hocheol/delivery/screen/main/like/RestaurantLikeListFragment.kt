package com.hocheol.delivery.screen.main.like

import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.hocheol.delivery.databinding.FragmentRestaurantLikeListBinding
import com.hocheol.delivery.model.restaurant.RestaurantModel
import com.hocheol.delivery.screen.base.BaseFragment
import com.hocheol.delivery.screen.main.home.restaurant.detail.RestaurantDetailActivity
import com.hocheol.delivery.util.provider.ResourcesProvider
import com.hocheol.delivery.widget.adapter.ModelRecyclerAdapter
import com.hocheol.delivery.widget.adapter.listener.restaurant.RestaurantLikeListListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class RestaurantLikeListFragment : BaseFragment<RestaurantLikeListViewModel, FragmentRestaurantLikeListBinding>() {

    override val viewModel by viewModel<RestaurantLikeListViewModel>()

    override fun getViewBinding(): FragmentRestaurantLikeListBinding = FragmentRestaurantLikeListBinding.inflate(layoutInflater)

    private var isFirstShown = false

    private val resourcesProvider by inject<ResourcesProvider>()

    private val adapter by lazy {
        ModelRecyclerAdapter<RestaurantModel, RestaurantLikeListViewModel>(
            modelList = listOf(),
            viewModel = viewModel,
            resourcesProvider = resourcesProvider,
            adapterListener = object : RestaurantLikeListListener {

                override fun onDislikeItem(model: RestaurantModel) {
                    viewModel.dislikeRestaurant(model.toEntity())
                }

                override fun onClickItem(model: RestaurantModel) {
                    startActivity(
                        RestaurantDetailActivity.newIntent(requireContext(), model.toEntity())
                    )
                }

            }
        )
    }

    override fun onResume() {
        super.onResume()
        if (isFirstShown.not()) {
            isFirstShown = true
        } else {
            viewModel.fetchData()
        }
    }

    override fun initViews() {
        binding.recyclerView.adapter = adapter
    }

    override fun observeData() = viewModel.restaurantListLiveData.observe(viewLifecycleOwner) {
        checkListEmpty(it)
    }

    private fun checkListEmpty(restaurantList: List<RestaurantModel>) {
        val isEmpty = restaurantList.isEmpty()
        binding.recyclerView.isGone = isEmpty
        binding.emptyResultTextView.isVisible = isEmpty
        if (isEmpty.not()) {
            adapter.submitList(restaurantList)
        }
    }

    companion object {

        fun newInstance() = RestaurantLikeListFragment()

        const val TAG = "restaurantLikeListFragment"

    }

}