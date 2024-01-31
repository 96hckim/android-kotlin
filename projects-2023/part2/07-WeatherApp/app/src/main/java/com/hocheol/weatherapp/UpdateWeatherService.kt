package com.hocheol.weatherapp

import android.Manifest
import android.app.PendingIntent
import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices

class UpdateWeatherService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val appWidgetManager: AppWidgetManager = AppWidgetManager.getInstance(this)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO 위젯을 권한없음 상태로 표시하고, 클릭했을 때 권한 팝업을 얻을 수 있도록 수정
        } else {
            LocationServices.getFusedLocationProviderClient(this).lastLocation.addOnSuccessListener { location ->
                WeatherRepository.getVillageForecast(
                    latitude = location.latitude,
                    longitude = location.longitude,
                    successCallback = { forecastList ->
                        val pendingServiceIntent: PendingIntent = PendingIntent.getService(
                            this,
                            1,
                            Intent(this, UpdateWeatherService::class.java),
                            PendingIntent.FLAG_IMMUTABLE
                        )

                        val currentForecast = forecastList.first()

                        RemoteViews(packageName, R.layout.widget_weather).apply {
                            setTextViewText(
                                R.id.temperatureTextView,
                                getString(R.string.temperature_format, currentForecast.temperature)
                            )
                            setTextViewText(
                                R.id.weatherTextView,
                                currentForecast.weather
                            )
                            setOnClickPendingIntent(R.id.temperatureTextView, pendingServiceIntent)
                        }.also { views ->
                            val appWidgetName = ComponentName(this, WeatherAppWidgetProvider::class.java)
                            appWidgetManager.updateAppWidget(appWidgetName, views)
                        }

                        stopSelf()
                    },
                    failureCallback = { t ->
                        stopSelf()
                    }
                )
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }
}