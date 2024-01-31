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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

            val retrofit = Retrofit.Builder()
                .baseUrl("http://apis.data.go.kr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(WeatherService::class.java)

            val baseDateTime = BaseDateTime.getBaseDateTime()
            val converter = GeoPointConverter()
            val point = converter.convert(lat = location.latitude, lon = location.longitude)

            service.getVillageForecast(
                serviceKey = "Q5dClFqwf3pxbC66tZUuHSHCyxPXCv45tQ8QcLnOvB2BvaFys+h6Yzqp/yx4JMoVZd0PS3XwT5drD6F+NLZ3UQ==",
                baseDate = baseDateTime.baseDate,
                baseTime = baseDateTime.baseTime,
                nx = point.nx,
                ny = point.ny
            ).enqueue(object : Callback<WeatherEntity> {
                override fun onResponse(call: Call<WeatherEntity>, response: Response<WeatherEntity>) {
                    val forecastMap = mutableMapOf<String, Forecast>()
                    val forecastList = response.body()?.response?.body?.items?.forecastEntities.orEmpty()
                    for (forecast in forecastList) {
                        val key = "${forecast.forecastDate}/${forecast.forecastTime}"
                        forecastMap.getOrPut(key) {
                            Forecast(
                                forecastDate = forecast.forecastDate,
                                forecastTime = forecast.forecastTime
                            )
                        }.apply {
                            forecast.category?.let { category ->
                                when (category) {
                                    Category.POP -> precipitation = forecast.forecastValue.toInt()
                                    Category.PTY -> precipitationType = transformRainType(forecast)
                                    Category.SKY -> sky = transformSky(forecast)
                                    Category.TMP -> temperature = forecast.forecastValue.toDouble()
                                    else -> Unit
                                }
                            }
                        }
                    }

                    val sortedList = forecastMap.values.sortedBy { "${it.forecastDate}${it.forecastTime}" }

                    Log.d(TAG, "onResponse: $forecastMap")

                    sortedList.firstOrNull()?.let { currentForecast ->
                        with(binding) {
                            temperatureTextView.text = getString(R.string.temperature_format, currentForecast.temperature)
                            weatherTextView.text = currentForecast.weather
                            precipitationTextView.text = getString(R.string.precipitation_format, currentForecast.precipitation)
                        }
                    }

                    binding.childForecastLayout.apply {
                        sortedList.drop(1).forEach { forecast ->
                            val itemView = ItemForecastBinding.inflate(layoutInflater)

                            with(itemView) {
                                timeTextView.text = transformTime(forecast.forecastTime)
                                weatherTextView.text = forecast.weather
                                temperatureTextView.text = getString(R.string.temperature_format, forecast.temperature)
                            }

                            addView(itemView.root)
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherEntity>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

    private fun transformRainType(forecast: ForecastEntity): String {
        return when (forecast.forecastValue.toInt()) {
            0 -> "없음"
            1 -> "비"
            2 -> "비/눈"
            3 -> "눈"
            4 -> "소나기"
            else -> ""
        }
    }

    private fun transformSky(forecast: ForecastEntity): String {
        return when (forecast.forecastValue.toInt()) {
            1 -> "맑음"
            3 -> "구름많음"
            4 -> "흐림"
            else -> ""
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