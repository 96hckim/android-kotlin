package com.hocheol.shopping.presentation.list

import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import com.hocheol.shopping.databinding.FragmentProductListBinding
import com.hocheol.shopping.extensions.toast
import com.hocheol.shopping.presentation.BaseFragment
import com.hocheol.shopping.presentation.adapter.ProductListAdapter
import com.hocheol.shopping.presentation.detail.ProductDetailActivity
import com.hocheol.shopping.presentation.main.MainActivity
import org.koin.android.ext.android.inject

internal class ProductListFragment : BaseFragment<ProductListViewModel, FragmentProductListBinding>() {

    companion object {
        const val TAG = "ProductListFragment"
    }

    override val viewModel by inject<ProductListViewModel>()

    override fun getViewBinding(): FragmentProductListBinding = FragmentProductListBinding.inflate(layoutInflater)

    private val adapter = ProductListAdapter()

    private val startProductDetailForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == ProductDetailActivity.PRODUCT_ORDERED_RESULT_CODE) {
                (requireActivity() as MainActivity).viewModel.refreshOrderList()
            }
        }

    override fun observeData() = viewModel.productListStateLiveData.observe(this) {
        when (it) {
            is ProductListState.UnInitialized -> {
                initViews(binding)
            }
            is ProductListState.Loading -> {
                handleLoadingState()
            }
            is ProductListState.Success -> {
                handleSuccessState(it)
            }
            is ProductListState.Error -> {
                handleErrorState()
            }
        }
    }

    private fun initViews(binding: FragmentProductListBinding) = with(binding) {
        recyclerView.adapter = adapter

        refreshLayout.setOnRefreshListener {
            viewModel.fetchData()
        }

    }

    private fun handleLoadingState() = with(binding) {
        refreshLayout.isRefreshing = true
    }

    private fun handleSuccessState(state: ProductListState.Success) = with(binding) {
        refreshLayout.isRefreshing = false

        if (state.productList.isEmpty()) {
            emptyResultTextView.isGone = false
            recyclerView.isGone = true
        } else {
            emptyResultTextView.isGone = true
            recyclerView.isGone = false
            adapter.setProductList(state.productList) {
                startProductDetailForResult.launch(
                    ProductDetailActivity.newIntent(requireContext(), it.id)
                )
            }
        }
    }

    private fun handleErrorState() {
        Toast.makeText(requireContext(), "에러가 발생했습니다.", Toast.LENGTH_SHORT).show()
    }

}