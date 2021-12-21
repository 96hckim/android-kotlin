package com.hocheol.delivery.screen.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.hocheol.delivery.R
import com.hocheol.delivery.data.entity.location.LocationLatLngEntity
import com.hocheol.delivery.data.entity.location.MapSearchInfoEntity
import com.hocheol.delivery.databinding.FragmentHomeBinding
import com.hocheol.delivery.screen.base.BaseFragment
import com.hocheol.delivery.screen.main.MainActivity
import com.hocheol.delivery.screen.main.MainTabMenu
import com.hocheol.delivery.screen.main.home.restaurant.RestaurantCategory
import com.hocheol.delivery.screen.main.home.restaurant.RestaurantListFragment
import com.hocheol.delivery.screen.main.home.restaurant.RestaurantOrder
import com.hocheol.delivery.screen.mylocation.MyLocationActivity
import com.hocheol.delivery.screen.order.OrderMenuListActivity
import com.hocheol.delivery.widget.adapter.RestaurantListFragmentPagerAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override val viewModel by viewModel<HomeViewModel>()

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    private lateinit var viewPagerAdapter: RestaurantListFragmentPagerAdapter

    private lateinit var locationManager: LocationManager

    private lateinit var myLocationListener: MyLocationListener

    private val firebaseAuth by inject<FirebaseAuth>()

    private val changeLocationLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.getParcelableExtra<MapSearchInfoEntity>(HomeViewModel.MY_LOCATION_KEY)?.let { info ->
                    viewModel.loadReverseGeoInformation(info.locationLatLng)
                }
            }
        }

    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val responsePermissions = permissions.entries.filter {
                (it.key == Manifest.permission.ACCESS_FINE_LOCATION)
                        || (it.key == Manifest.permission.ACCESS_COARSE_LOCATION)
            }

            if (responsePermissions.filter { it.value == true }.size == locationPermissions.size) {
                setMyLocationListener()
            } else {
                with(binding.locationTitleTextView) {
                    text = getString(R.string.please_setup_your_location_permission)
                    setOnClickListener {
                        getMyLocation()
                    }
                }
                Toast.makeText(requireContext(), R.string.cannot_assigned_permission, Toast.LENGTH_SHORT).show()
            }
        }

    override fun initViews() = with(binding) {
        orderChipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipDefault -> {
                    chipInitialize.visibility = View.GONE
                    changeRestaurantOrder(RestaurantOrder.DEFAULT)
                }
                R.id.chipInitialize -> {
                    chipDefault.isChecked = true
                }
                R.id.chipLowDeliveryTip -> {
                    chipInitialize.visibility = View.VISIBLE
                    changeRestaurantOrder(RestaurantOrder.LOW_DELIVERY_TIP)
                }
                R.id.chipFastDelivery -> {
                    chipInitialize.visibility = View.VISIBLE
                    changeRestaurantOrder(RestaurantOrder.FAST_DELIVERY)
                }
                R.id.chipTopRate -> {
                    chipInitialize.visibility = View.VISIBLE
                    changeRestaurantOrder(RestaurantOrder.TOP_RATE)
                }
            }
        }
    }

    private fun changeRestaurantOrder(order: RestaurantOrder) {
        viewPagerAdapter.fragmentList.forEach {
            it.viewModel.setRestaurantOrder(order)
        }
    }

    private fun initViewPager(locationLatLng: LocationLatLngEntity) = with(binding) {
        val restaurantCategories = RestaurantCategory.values()

        if (::viewPagerAdapter.isInitialized.not()) {
            val restaurantListFragmentList = restaurantCategories.map {
                RestaurantListFragment.newInstance(it, locationLatLng)
            }

            viewPagerAdapter = RestaurantListFragmentPagerAdapter(
                this@HomeFragment,
                restaurantListFragmentList,
                locationLatLng
            )

            viewPager.adapter = viewPagerAdapter

            viewPager.offscreenPageLimit = restaurantCategories.size

            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.setText(restaurantCategories[position].categoryNameId)
            }.attach()
        }

        if (locationLatLng != viewPagerAdapter.locationLatLngEntity) {
            viewPagerAdapter.locationLatLngEntity = locationLatLng
            viewPagerAdapter.fragmentList.forEach {
                it.viewModel.setLocationLatLng(locationLatLng)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkMyBasket()
    }

    override fun observeData() {

        viewModel.homeStateLiveData.observe(viewLifecycleOwner) {
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
                    binding.orderChipGroup.visibility = View.VISIBLE
                    binding.viewPager.visibility = View.VISIBLE
                    binding.locationTitleTextView.setOnClickListener {
                        viewModel.getMapSearchInfo()?.let { info ->
                            changeLocationLauncher.launch(
                                MyLocationActivity.newIntent(requireContext(), info)
                            )
                        }
                    }
                    initViewPager(it.mapSearchInfo.locationLatLng)
                    if (it.isLocationSame.not()) {
                        Toast.makeText(requireContext(), R.string.please_set_your_current_location, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                is HomeState.Error -> {
                    binding.locationLoading.visibility = View.GONE
                    binding.locationTitleTextView.text = getString(R.string.location_not_found)
                    binding.locationTitleTextView.setOnClickListener {
                        getMyLocation()
                    }
                    Toast.makeText(requireContext(), it.messageId, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.foodMenuBasketLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                binding.basketButtonContainer.visibility = View.VISIBLE
                binding.basketCountTextView.text = getString(R.string.basket_count, it.size)
                binding.basketButton.setOnClickListener {
                    if (firebaseAuth.currentUser == null) {
                        alertNeedLogin {
                            (requireActivity() as MainActivity).goToTab(MainTabMenu.MY)
                        }
                    } else {
                        startActivity(
                            OrderMenuListActivity.newInstance(requireContext())
                        )
                    }
                }
            } else {
                binding.basketButtonContainer.visibility = View.GONE
                binding.basketButton.setOnClickListener(null)
            }
        }

    }

    private fun alertNeedLogin(afterAction: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle("로그인이 필요합니다.")
            .setMessage("주문하려면 로그인이 필요합니다. My 탭으로 이동하시겠습니까?")
            .setPositiveButton("이동") { dialog, _ ->
                afterAction()
                dialog.dismiss()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
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