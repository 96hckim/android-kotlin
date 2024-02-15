package com.hocheol.mediasearch

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import com.google.android.material.tabs.TabLayoutMediator
import com.hocheol.mediasearch.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val searchFragment = SearchFragment()
    private val fragments = listOf(searchFragment, FavoritesFragment())
    private val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle, fragments)

    private var observableTextQuery: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            view = this@MainActivity
        }

        initView()
    }

    private fun initView() {
        binding.viewPager.adapter = adapter
        binding.viewPager.isVisible = true

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (fragments[position]) {
                is SearchFragment -> tab.text = "검색 결과"
                is FavoritesFragment -> tab.text = "즐겨 찾기"
                else -> Unit
            }
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        observableTextQuery?.dispose()
        observableTextQuery = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (menu != null) {
            menuInflater.inflate(R.menu.options_menu, menu)

            observableTextQuery = Observable.create { emitter ->
                (menu.findItem(R.id.search).actionView as SearchView).apply {
                    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            emitter.onNext(query.orEmpty())
                            return false
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            binding.viewPager.setCurrentItem(0, true)
                            emitter.onNext(newText.orEmpty())
                            return false
                        }
                    })
                }
            }
                .debounce(500L, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { query ->
                    searchFragment.searchKeyword(query)
                }
        }

        return true
    }
}