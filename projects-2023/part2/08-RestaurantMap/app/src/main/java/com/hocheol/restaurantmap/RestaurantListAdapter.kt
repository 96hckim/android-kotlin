package com.hocheol.restaurantmap

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hocheol.restaurantmap.databinding.ItemRestaurantBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.Tm128

class RestaurantListAdapter(private val onClick: (LatLng) -> Unit) : RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>() {

    private var dataSet = emptyList<SearchItem>()

    inner class ViewHolder(private val binding: ItemRestaurantBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchItem) {
            binding.root.setOnClickListener {
                onClick(Tm128(item.mapx.toDouble(), item.mapy.toDouble()).toLatLng())
            }

            binding.titleTextView.text = item.title
            binding.categoryTextView.text = item.category
            binding.roadAddressTextView.text = item.roadAddress
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRestaurantBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    fun setData(dataSet: List<SearchItem>) {
        this.dataSet = dataSet
        notifyDataSetChanged()
    }
}