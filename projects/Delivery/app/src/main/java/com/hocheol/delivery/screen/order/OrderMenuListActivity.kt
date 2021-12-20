package com.hocheol.delivery.screen.order

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.hocheol.delivery.databinding.ActivityOrderMenuListBinding
import com.hocheol.delivery.model.restaurant.food.FoodModel
import com.hocheol.delivery.screen.base.BaseActivity
import com.hocheol.delivery.util.provider.ResourcesProvider
import com.hocheol.delivery.widget.adapter.ModelRecyclerAdapter
import com.hocheol.delivery.widget.adapter.listener.order.OrderMenuListListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderMenuListActivity : BaseActivity<OrderMenuListViewModel, ActivityOrderMenuListBinding>() {

    override val viewModel by viewModel<OrderMenuListViewModel>()

    override fun getViewBinding(): ActivityOrderMenuListBinding = ActivityOrderMenuListBinding.inflate(layoutInflater)

    private val resourcesProvider by inject<ResourcesProvider>()

    private val adapter by lazy {
        ModelRecyclerAdapter<FoodModel, OrderMenuListViewModel>(
            modelList = listOf(),
            viewModel = viewModel,
            resourcesProvider = resourcesProvider,
            adapterListener = object : OrderMenuListListener {

                override fun onRemoveItem(model: FoodModel) {
                    viewModel.removeOrderMenu(model)
                }

            }
        )
    }

    override fun initViews() = with(binding) {
        recyclerView.adapter = adapter

        toolbar.setNavigationOnClickListener { finish() }

        confirmButton.setOnClickListener {
            viewModel.orderMenu()
        }

        clearOrderButton.setOnClickListener {
            viewModel.clearOrderMenu()
        }
    }

    override fun observeData() = viewModel.orderMenuStateLiveData.observe(this) {
        when (it) {
            is OrderMenuState.Loading -> {
                handleLoading()
            }
            is OrderMenuState.Success -> {
                handleSuccess(it)
            }
            is OrderMenuState.Order -> {

            }
            is OrderMenuState.Error -> {
                handleError(it)
            }
            else -> Unit
        }
    }

    private fun handleLoading() = with(binding) {
        progressBar.visibility = View.VISIBLE
    }

    private fun handleSuccess(state: OrderMenuState.Success) = with(binding) {
        progressBar.visibility = View.GONE
        adapter.submitList(state.restaurantFoodModelList)
        val menuOrderIsEmpty = state.restaurantFoodModelList.isNullOrEmpty()
        confirmButton.isEnabled = menuOrderIsEmpty.not()
        if (menuOrderIsEmpty) {
            Toast.makeText(this@OrderMenuListActivity, "주문 메뉴가 없어 화면을 종료합니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun handleError(state: OrderMenuState.Error) {
        Toast.makeText(this, state.messageId, Toast.LENGTH_SHORT).show()
    }

    companion object {

        fun newInstance(context: Context) = Intent(context, OrderMenuListActivity::class.java)

    }

}