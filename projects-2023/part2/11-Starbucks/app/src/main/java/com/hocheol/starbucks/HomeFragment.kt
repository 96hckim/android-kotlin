package com.hocheol.starbucks

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.hocheol.starbucks.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        val homeData = context?.readData() ?: return

        binding.appBarTitleTextView.text = getString(R.string.app_bar_title, homeData.user.nickname)

        binding.starCountTextView.text = getString(R.string.app_bar_star_count, homeData.user.starCount, homeData.user.totalCount)

        binding.starProgressBar.max = homeData.user.totalCount
        binding.starProgressBar.progress = homeData.user.starCount

        Glide.with(binding.appBarImageView)
            .load(homeData.appBarImage)
            .into(binding.appBarImageView)

        binding.recommendMenuList.titleTextView.text = getString(R.string.recommend_menu_title, homeData.user.nickname)
        binding.recommendMenuList.menuLayout.addView(
            MenuView(context = requireContext()).apply {
                setTitle("아이스 아메리카노")
                setImageUrl("https://picsum.photos/100/100")
            }
        )
    }
}