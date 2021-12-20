package com.hocheol.delivery.screen.order

import com.hocheol.delivery.databinding.ActivityOrderMenuListBinding
import com.hocheol.delivery.screen.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class OrderMenuListActivity : BaseActivity<OrderMenuListViewModel, ActivityOrderMenuListBinding>() {

    override val viewModel by viewModel<OrderMenuListViewModel>()

    override fun getViewBinding(): ActivityOrderMenuListBinding = ActivityOrderMenuListBinding.inflate(layoutInflater)

    override fun observeData() {
    }

}