package com.hocheol.memoapp.ui.home

import android.content.Intent
import com.hocheol.memoapp.ui.content.ContentActivity

class HomeState(
    private val activity: HomeActivity
) {
    fun showContent(index: Int) {
        activity.startActivity(
            Intent(activity, ContentActivity::class.java).apply {
                putExtra("id", index)
            }
        )
    }
}