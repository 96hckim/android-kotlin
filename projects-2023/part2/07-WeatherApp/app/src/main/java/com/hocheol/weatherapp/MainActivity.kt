package com.hocheol.weatherapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.hocheol.weatherapp.databinding.ActivityMainBinding
import com.hocheol.weatherapp.databinding.ItemForecastBinding
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                updateLocation()
            }

            else -> {
                Toast.makeText(this, "위치 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    private fun updateLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION))
            return
        }

        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            Log.e(TAG, "lastLocation: $location")

            Thread {
                try {
                    val addressList = Geocoder(this, Locale.KOREA).getFromLocation(
                        location.latitude,
                        location.longitude,
                        1
                    )
                    runOnUiThread {
                        binding.locationTextView.text = addressList?.firstOrNull()?.thoroughfare.orEmpty()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()

            WeatherRepository.getVillageForecast(
                latitude = location.latitude,
                longitude = location.longitude,
                successCallback = { forecastList ->
                    Log.d(TAG, "forecastList: $forecastList")

                    forecastList.firstOrNull()?.let { currentForecast ->
                        with(binding) {
                            temperatureTextView.text = getString(com.hocheol.weatherapp.R.string.temperature_format, currentForecast.temperature)
                            weatherTextView.text = currentForecast.weather
                            precipitationTextView.text = getString(com.hocheol.weatherapp.R.string.precipitation_format, currentForecast.precipitation)
                        }
                    }

                    binding.childForecastLayout.apply {
                        forecastList.drop(1).forEach { forecast ->
                            val itemView = ItemForecastBinding.inflate(layoutInflater)

                            with(itemView) {
                                timeTextView.text = transformTime(forecast.forecastTime)
                                weatherTextView.text = forecast.weather
                                temperatureTextView.text = getString(R.string.temperature_format, forecast.temperature)
                            }

                            addView(itemView.root)
                        }
                    }
                },
                failureCallback = { t ->
                    t.printStackTrace()
                }
            )
        }
    }

    private fun transformTime(time: String): String {
        val hour = time.substring(0, 2).toInt()
        val minute = time.substring(2).toInt()

        val period = if (hour < 12) "오전" else "오후"
        val formattedHour = if (hour > 12) hour - 12 else hour

        return "$period ${String.format("%02d:%02d", formattedHour, minute)}"
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}