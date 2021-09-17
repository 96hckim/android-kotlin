package com.hocheol.locationsearchmap

import android.os.Bundle
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hocheol.locationsearchmap.databinding.ActivityMainBinding
import com.hocheol.locationsearchmap.model.LocationLatLngEntity
import com.hocheol.locationsearchmap.model.SearchResultEntity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: SearchListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initAdapter()
        initData()
        setMockingData()
    }

    private fun initViews() {
        binding.emptyResultTextView.isVisible = false
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

}