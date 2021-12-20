package com.hocheol.delivery.screen.main.home.restaurant

import androidx.core.os.bundleOf
import com.hocheol.delivery.data.entity.LocationLatLngEntity
import com.hocheol.delivery.databinding.FragmentRestaurantListBinding
import com.hocheol.delivery.model.restaurant.RestaurantModel
import com.hocheol.delivery.screen.base.BaseFragment
import com.hocheol.delivery.screen.main.home.restaurant.detail.RestaurantDetailActivity
import com.hocheol.delivery.util.provider.ResourcesProvider
import com.hocheol.delivery.widget.adapter.ModelRecyclerAdapter
import com.hocheol.delivery.widget.adapter.listener.restaurant.RestaurantListListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RestaurantListFragment : BaseFragment<RestaurantListViewModel, FragmentRestaurantListBinding>() {

    private val restaurantCategory by lazy { arguments?.getSerializable(RESTAURANT_CATEGORY_KEY) as RestaurantCategory }
    private val locationLatLng by lazy { arguments?.getParcelable<LocationLatLngEntity>(LOCATION_KEY) }

    override val viewModel by viewModel<RestaurantListViewModel> {
        parametersOf(
            restaurantCategory,
            locationLatLng
        )
    }

    override fun getViewBinding(): FragmentRestaurantListBinding = FragmentRestaurantListBinding.inflate(layoutInflater)

    private val resourcesProvider by inject<ResourcesProvider>()

    private val adapter by lazy {
        ModelRecyclerAdapter<RestaurantModel, RestaurantListViewModel>(
            modelList = listOf(),
            viewModel = viewModel,
            resourcesProvider = resourcesProvider,
            adapterListener = object : RestaurantListListener {

                override fun onClickItem(model: RestaurantModel) {
                    startActivity(
                        RestaurantDetailActivity.newIntent(
                            requireContext(),
                            model.toEntity()
                        )
                    )
                }

            }
        )
    }

    override fun initViews() = with(binding) {
        recyclerView.adapter = adapter
    }

    override fun observeData() = viewModel.restaurantListLiveData.observe(viewLifecycleOwner) {
        adapter.submitList(it)
    }

    companion object {

        const val RESTAURANT_CATEGORY_KEY = "restaurantCategory"
        const val LOCATION_KEY = "location"
        const val RESTAURANT_KEY = "Restaurant"

        fun newInstance(
            restaurantCategory: RestaurantCategory,
            locationLatLng: LocationLatLngEntity
        ): RestaurantListFragment {
            return RestaurantListFragment().apply {
                arguments = bundleOf(
                    RESTAURANT_CATEGORY_KEY to restaurantCategory,
                    LOCATION_KEY to locationLatLng
                )
            }
        }

    }

}