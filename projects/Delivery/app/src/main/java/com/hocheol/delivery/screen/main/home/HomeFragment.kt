package com.hocheol.delivery.screen.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.tabs.TabLayoutMediator
import com.hocheol.delivery.R
import com.hocheol.delivery.data.entity.LocationLatLngEntity
import com.hocheol.delivery.databinding.FragmentHomeBinding
import com.hocheol.delivery.screen.base.BaseFragment
import com.hocheol.delivery.screen.main.home.restaurant.RestaurantCategory
import com.hocheol.delivery.screen.main.home.restaurant.RestaurantListFragment
import com.hocheol.delivery.widget.adapter.RestaurantListFragmentPagerAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override val viewModel by viewModel<HomeViewModel>()

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    private lateinit var viewPagerAdapter: RestaurantListFragmentPagerAdapter

    private lateinit var locationManager: LocationManager

    private lateinit var myLocationListener: MyLocationListener

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val responsePermissions = permissions.entries.filter {
                (it.key == Manifest.permission.ACCESS_FINE_LOCATION)
                        || (it.key == Manifest.permission.ACCESS_COARSE_LOCATION)
            }

            if (responsePermissions.filter { it.value == true }.size == locationPermissions.size) {
                setMyLocationListener()
            } else {
                binding.locationTitleTextView.setText(R.string.please_setup_your_location_permission)
                Toast.makeText(requireContext(), R.string.cannot_assigned_permission, Toast.LENGTH_SHORT).show()
            }
        }

    override fun initViews() {
        super.initViews()
        binding.locationTitleTextView.setOnClickListener {
            getMyLocation()
        }
    }

    private fun initViewPager(locationLatLng: LocationLatLngEntity) = with(binding) {
        val restaurantCategories = RestaurantCategory.values()

        if (::viewPagerAdapter.isInitialized.not()) {
            val restaurantListFragmentList = restaurantCategories.map {
                RestaurantListFragment.newInstance(it)
            }

            viewPagerAdapter = RestaurantListFragmentPagerAdapter(
                this@HomeFragment,
                restaurantListFragmentList
            )

            viewPager.adapter = viewPagerAdapter
        }

        viewPager.offscreenPageLimit = restaurantCategories.size

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setText(restaurantCategories[position].categoryNameId)
        }.attach()
    }

    override fun observeData() = viewModel.homeStateLiveData.observe(viewLifecycleOwner) {
        when (it) {
            is HomeState.UnInitialized -> {
                getMyLocation()
            }
            is HomeState.Loading -> {
                binding.locationLoading.visibility = View.VISIBLE
                binding.locationTitleTextView.text = getString(R.string.loading)
            }
            is HomeState.Success -> {
                binding.locationLoading.visibility = View.GONE
                binding.locationTitleTextView.text = it.mapSearchInfo.fullAddress
                binding.tabLayout.visibility = View.VISIBLE
                binding.filterChipGroup.visibility = View.VISIBLE
                binding.viewPager.visibility = View.VISIBLE
                initViewPager(it.mapSearchInfo.locationLatLng)
            }
            is HomeState.Error -> {
                binding.locationLoading.visibility = View.GONE
                binding.locationTitleTextView.text = getString(R.string.location_not_found)
                Toast.makeText(requireContext(), it.messageId, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getMyLocation() {
        if (::locationManager.isInitialized.not()) {
            locationManager = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }

        val isGpsEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isGpsEnable) {
            locationPermissionLauncher.launch(locationPermissions)
        } else {
            Toast.makeText(requireContext(), R.string.please_turn_on_gps, Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("MissingPermission")
    private fun setMyLocationListener() {
        val minTime = 1500L
        val minDistance = 100f

        if (::myLocationListener.isInitialized.not()) {
            myLocationListener = MyLocationListener()
        }

        with(locationManager) {
            requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime, minDistance, myLocationListener
            )
            requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                minTime, minDistance, myLocationListener
            )
        }
    }

    companion object {

        fun newInstance() = HomeFragment()

        const val TAG = "HomeFragment"

        val locationPermissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

    }

    private fun removeLocationListener() {
        if (::locationManager.isInitialized && ::myLocationListener.isInitialized) {
            locationManager.removeUpdates(myLocationListener)
        }
    }

    inner class MyLocationListener : LocationListener {

        override fun onLocationChanged(location: Location) {
            viewModel.loadReverseGeoInformation(
                LocationLatLngEntity(
                    latitude = location.latitude,
                    longitude = location.longitude
                )
            )
            removeLocationListener()
        }

    }

}