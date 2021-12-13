package com.hocheol.delivery.screen.main.my

import com.hocheol.delivery.databinding.FragmentMyBinding
import com.hocheol.delivery.screen.base.BaseFragment
import com.hocheol.delivery.screen.main.home.HomeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyFragment : BaseFragment<MyViewModel, FragmentMyBinding>() {

    override val viewModel by viewModel<MyViewModel>()

    override fun getViewBinding(): FragmentMyBinding = FragmentMyBinding.inflate(layoutInflater)

    override fun observeData() {
    }

    companion object {

        fun newInstance() = MyFragment()

        const val TAG = "MyFragment"

    }

}