package com.hocheol.delivery.screen.main

import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.navigation.NavigationBarView
import com.hocheol.delivery.R
import com.hocheol.delivery.databinding.ActivityMainBinding
import com.hocheol.delivery.screen.main.home.HomeFragment
import com.hocheol.delivery.screen.main.like.RestaurantLikeListFragment
import com.hocheol.delivery.screen.main.my.MyFragment
import com.hocheol.delivery.util.event.MenuChangeEventBus
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    private val menuChangeEventBus by inject<MenuChangeEventBus>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observeData()
        initView()
    }

    private fun observeData() {
        lifecycleScope.launch {
            menuChangeEventBus.mainTabMenuFlow.collect {
                goToTab(it)
            }
        }
    }

    private fun initView() = with(binding) {
        bottomNav.setOnItemSelectedListener(this@MainActivity)
        showFragment(HomeFragment.newInstance(), HomeFragment.TAG)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_home -> {
                showFragment(HomeFragment.newInstance(), HomeFragment.TAG)
                true
            }
            R.id.menu_like -> {
                showFragment(RestaurantLikeListFragment.newInstance(), RestaurantLikeListFragment.TAG)
                true
            }
            R.id.menu_my -> {
                showFragment(MyFragment.newInstance(), MyFragment.TAG)
                true
            }
            else -> false
        }
    }

    private fun showFragment(fragment: Fragment, tag: String) {
        val findFragment = supportFragmentManager.findFragmentByTag(tag)

        supportFragmentManager.fragments.forEach {
            supportFragmentManager.beginTransaction().hide(it).commitAllowingStateLoss()
        }

        findFragment?.let {
            supportFragmentManager.beginTransaction().show(it).commitAllowingStateLoss()
        } ?: kotlin.run {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragment, tag)
                .commitAllowingStateLoss()
        }
    }

    fun goToTab(mainTabMenu: MainTabMenu) {
        binding.bottomNav.selectedItemId = mainTabMenu.menuId
    }

}

enum class MainTabMenu(@IdRes val menuId: Int) {

    HOME(R.id.menu_home), LIKE(R.id.menu_like), MY(R.id.menu_my)

}