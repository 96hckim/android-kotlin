package com.hocheol.delivery.screen.main.home.restaurant.detail.menu

import androidx.core.os.bundleOf
import com.hocheol.delivery.data.entity.RestaurantFoodEntity
import com.hocheol.delivery.databinding.FragmentRestaurantListBinding
import com.hocheol.delivery.model.restaurant.food.FoodModel
import com.hocheol.delivery.screen.base.BaseFragment
import com.hocheol.delivery.util.provider.ResourcesProvider
import com.hocheol.delivery.widget.adapter.ModelRecyclerAdapter
import com.hocheol.delivery.widget.adapter.listener.AdapterListener
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class RestaurantMenuListFragment : BaseFragment<RestaurantMenuListViewModel, FragmentRestaurantListBinding>() {

    private val restaurantId by lazy { arguments?.getLong(RESTAURANT_ID_KEY, -1) }
    private val restaurantFoodList by lazy { arguments?.getParcelableArrayList<RestaurantFoodEntity>(FOOD_LIST_KEY) }

    override val viewModel by inject<RestaurantMenuListViewModel> {
        parametersOf(
            restaurantId,
            restaurantFoodList
        )
    }

    override fun getViewBinding(): FragmentRestaurantListBinding = FragmentRestaurantListBinding.inflate(layoutInflater)

    private val resourcesProvider by inject<ResourcesProvider>()

    private val adapter by lazy {
        ModelRecyclerAdapter<FoodModel, RestaurantMenuListViewModel>(
            modelList = listOf(),
            viewModel = viewModel,
            adapterListener = object : AdapterListener {

            },
            resourcesProvider = resourcesProvider
        )
    }

    override fun initViews() {
        binding.recyclerView.adapter = adapter
    }

    override fun observeData() = viewModel.restaurantFoodListLiveData.observe(this) {
        adapter.submitList(it)
    }

    companion object {

        const val RESTAURANT_ID_KEY = "restaurantId"

        const val FOOD_LIST_KEY = "foodList"

        fun newInstance(restaurantId: Long, foodList: ArrayList<RestaurantFoodEntity>): RestaurantMenuListFragment {
            val bundle = bundleOf(
                RESTAURANT_ID_KEY to restaurantId,
                FOOD_LIST_KEY to foodList
            )
            return RestaurantMenuListFragment().apply {
                arguments = bundle
            }
        }

    }

}