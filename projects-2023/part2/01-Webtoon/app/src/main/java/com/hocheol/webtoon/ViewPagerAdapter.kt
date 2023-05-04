package com.hocheol.webtoon

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(
    private val mainActivity: MainActivity,
) : FragmentStateAdapter(mainActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                return WebViewFragment(position, "https://comic.naver.com/webtoon/detail?titleId=783888&no=79").apply {
                    listener = mainActivity
                }
            }

            1 -> {
                return WebViewFragment(position, "https://comic.naver.com/webtoon/detail?titleId=807831&no=17").apply {
                    listener = mainActivity
                }
            }

            else -> {
                return WebViewFragment(position, "https://comic.naver.com/webtoon/detail?titleId=400739&no=431").apply {
                    listener = mainActivity
                }
            }
        }
    }
}