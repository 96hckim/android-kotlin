package com.hocheol.finedust

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource
import com.hocheol.finedust.data.Repository
import com.hocheol.finedust.data.models.airquality.Grade
import com.hocheol.finedust.data.models.airquality.MeasuredValue
import com.hocheol.finedust.data.models.monitoringstation.MonitoringStation
import com.hocheol.finedust.databinding.ActivityMainBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val scope = MainScope()

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var cancellationTokenSource: CancellationTokenSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        bindViews()
        initVariables()
        checkLocationPermission()
    }

    override fun onDestroy() {
        super.onDestroy()

        cancellationTokenSource?.cancel()
        scope.cancel()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val locationPermissionGranted =
            requestCode == FOREGROUND_LOCATION_PERMISSIONS_REQUEST_CODE &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED

        val backgroundLocationPermissionGranted =
            requestCode == BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (backgroundLocationPermissionGranted) {
                fetchAirQualityData()
            } else {
                requestBackgroundLocationPermission()
            }
        } else {
            if (locationPermissionGranted) {
                fetchAirQualityData()
            } else {
                finish()
            }
        }
    }

    private fun bindViews() {
        binding.refreshLayout.setOnRefreshListener {
            fetchAirQualityData()
        }
    }

    private fun initVariables() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPermission()
        } else {
            fetchAirQualityData()
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            FOREGROUND_LOCATION_PERMISSIONS_REQUEST_CODE
        )
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun requestBackgroundLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ),
            BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    @SuppressLint("MissingPermission")
    private fun fetchAirQualityData() {
        cancellationTokenSource = CancellationTokenSource()

        fusedLocationProviderClient
            .getCurrentLocation(
                LocationRequest.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource!!.token
            ).addOnSuccessListener { location ->
                scope.launch {
                    binding.errorDescriptionTextView.visibility = View.GONE
                    try {
                        val monitoringStation =
                            Repository.getNearbyMonitoringStation(location.latitude, location.longitude)

                        val measuredValue = Repository.getLatestAirQualityData(monitoringStation!!.stationName!!)

                        displayAirQualityData(monitoringStation, measuredValue!!)
                    } catch (e: Exception) {
                        binding.errorDescriptionTextView.visibility = View.VISIBLE
                        binding.contentsLayout.alpha = 0f
                    } finally {
                        binding.progressBar.visibility = View.GONE
                        binding.refreshLayout.isRefreshing = false
                    }
                }
            }
    }

    @SuppressLint("SetTextI18n")
    private fun displayAirQualityData(monitoringStation: MonitoringStation, measuredValue: MeasuredValue) =
        with(binding) {
            contentsLayout.animate()
                .alpha(1f)
                .start()
            measuringStationNameTextView.text = monitoringStation.stationName
            measuringStationAddressTextView.text = monitoringStation.addr

            (measuredValue.khaiGrade ?: Grade.UNKNOWN).let { grade ->
                root.setBackgroundResource(grade.colorResId)
                totalGradeLabelTextview.text = grade.label
                totalGradeEmojiTextView.text = grade.emoji
            }

            with(measuredValue) {
                fineDustInformationTextView.text = "미세먼지: $pm10Value ㎍/㎥ ${(pm10Grade ?: Grade.UNKNOWN).emoji}"
                ultraFineDustInformationTextView.text = "초미세먼지: $pm25Value ㎍/㎥ ${(pm25Grade ?: Grade.UNKNOWN).emoji}"

                with(so2Item) {
                    labelTextView.text = "아황산가스"
                    gradeTextView.text = (so2Grade ?: Grade.UNKNOWN).toString()
                    valueTextView.text = "$so2Value ppm"
                }

                with(coItem) {
                    labelTextView.text = "일산화탄소"
                    gradeTextView.text = (coGrade ?: Grade.UNKNOWN).toString()
                    valueTextView.text = "$coValue ppm"
                }

                with(o3Item) {
                    labelTextView.text = "오존"
                    gradeTextView.text = (o3Grade ?: Grade.UNKNOWN).toString()
                    valueTextView.text = "$o3Value ppm"
                }

                with(no2Item) {
                    labelTextView.text = "이산화질소"
                    gradeTextView.text = (no2Grade ?: Grade.UNKNOWN).toString()
                    valueTextView.text = "$no2Value ppm"
                }
            }
        }

    companion object {
        private const val FOREGROUND_LOCATION_PERMISSIONS_REQUEST_CODE = 100
        private const val BACKGROUND_LOCATION_PERMISSION_REQUEST_CODE = 101
    }

}