package com.hocheol.locationsearchmap

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hocheol.locationsearchmap.databinding.ActivityMainBinding
import com.hocheol.locationsearchmap.model.LocationLatLngEntity
import com.hocheol.locationsearchmap.model.SearchResultEntity
import com.hocheol.locationsearchmap.utillity.RetrofitUtil
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        job = Job()

        initViews()
        bindViews()
        initAdapter()
        initData()
        setMockingData()
    }

    private fun initViews() {
        binding.emptyResultTextView.isVisible = false
    }

    private fun bindViews() = with(binding) {
        searchButton.setOnClickListener {
            searchKeyword(searchEditText.text.toString())
        }
    }

    private fun initAdapter() {
        adapter = SearchListAdapter(searchResultCLickListener = {
            Toast.makeText(
                this,
                "위치 : ${it.fullAddress}, 건물명 : ${it.name}\n 좌표 : ${it.locationLatLng.latitude}, ${it.locationLatLng.longitude}",
                Toast.LENGTH_SHORT
            ).show()
        })

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        val decoration = DividerItemDecoration(applicationContext, VERTICAL)
        binding.recyclerView.addItemDecoration(decoration)
    }

    private fun initData() {
        adapter.notifyDataSetChanged()
    }

    private fun setMockingData() {
        val dataList = (0..10).map {
            SearchResultEntity(
                name = "빌딩 $it",
                fullAddress = "주소 $it",
                locationLatLng = LocationLatLngEntity(
                    latitude = it.toFloat(),
                    longitude = it.toFloat()
                )
            )
        }

        adapter.submitList(dataList)
    }

    private fun searchKeyword(keyword: String) {
        launch(coroutineContext) {
            try {
                withContext(Dispatchers.IO) {
                    val response = RetrofitUtil.apiService.getSearchLocation(
                        keyword = keyword
                    )
                    if (response.isSuccessful) {
                        val body = response.body()
                        withContext(Dispatchers.Main) {
                            Log.e("response", body.toString())
                        }
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "검색하는 과정에서 오류가 발생했습니다. : ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}