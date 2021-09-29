package com.hocheol.moviereview.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.hocheol.moviereview.R
import com.hocheol.moviereview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navigationController by lazy {
        (supportFragmentManager.findFragmentById(R.id.mainNavigationHostContainer) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.home_dest, R.id.my_page_dest))
        binding.toolbar.setupWithNavController(navigationController, appBarConfiguration)
        binding.bottomNavigationView.setupWithNavController(navigationController)
    }

}