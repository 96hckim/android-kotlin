package com.hocheol.starbucks

import android.animation.ValueAnimator
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

        val context = requireContext()

        val homeData = context.readData("home.json", Home::class.java) ?: return
        val menuData = context.readData("menu.json", Menu::class.java) ?: return

        initAppBar(homeData)
        initRecommendMenu(homeData, menuData)
        initBanner(homeData)
        initFoodMenu(menuData)
        initFloatingActionButton()
    }

    private fun initAppBar(homeData: Home) {
        binding.appBarTitleTextView.text = getString(R.string.app_bar_title, homeData.user.nickname)

        binding.starCountTextView.text = getString(R.string.app_bar_star_count, homeData.user.starCount, homeData.user.totalCount)

        binding.starProgressBar.max = homeData.user.totalCount

        Glide.with(binding.appBarImageView)
            .load(homeData.appBarImage)
            .into(binding.appBarImageView)

        ValueAnimator.ofInt(0, homeData.user.starCount).apply {
            duration = 1000
            addUpdateListener {
                binding.starProgressBar.progress = it.animatedValue as Int
            }
            start()
        }
    }

    private fun initRecommendMenu(homeData: Home, menuData: Menu) {
        binding.recommendMenuList.titleTextView.text = getString(R.string.recommend_menu_title, homeData.user.nickname)
        menuData.coffee.forEach { menuItem ->
            binding.recommendMenuList.menuLayout.addView(
                MenuView(context = requireContext()).apply {
                    setTitle(menuItem.name)
                    setImageUrl(menuItem.image)
                }
            )
        }
    }

    private fun initBanner(homeData: Home) {
        binding.bannerLayout.bannerImageView.apply {
            Glide.with(this)
                .load(homeData.banner.image)
                .into(this)
            this.contentDescription = homeData.banner.contentDescription
        }
    }

    private fun initFoodMenu(menuData: Menu) {
        binding.foodMenuList.titleTextView.text = getString(R.string.food_menu_title)
        menuData.coffee.forEach { menuItem ->
            binding.foodMenuList.menuLayout.addView(
                MenuView(context = requireContext()).apply {
                    setTitle(menuItem.name)
                    setImageUrl(menuItem.image)
                }
            )
        }
    }

    private fun initFloatingActionButton() {
        binding.nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY == 0) {
                binding.floatingActionButton.extend()
            } else {
                binding.floatingActionButton.shrink()
            }
        }
    }
}