package com.hocheol.weatherapp

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices

class UpdateWeatherService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        startForeground(1, createNotification())

        val appWidgetManager: AppWidgetManager = AppWidgetManager.getInstance(this)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                this,
                2,
                Intent(this, SettingActivity::class.java),
                PendingIntent.FLAG_IMMUTABLE
            )

            RemoteViews(packageName, R.layout.widget_weather).apply {
                setTextViewText(R.id.temperatureTextView, "권한없음")
                setTextViewText(R.id.weatherTextView, "")
                setOnClickPendingIntent(R.id.temperatureTextView, pendingIntent)
            }.also { views ->
                val appWidgetName = ComponentName(this, WeatherAppWidgetProvider::class.java)
                appWidgetManager.updateAppWidget(appWidgetName, views)
            }

            stopSelf()
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
                        val pendingServiceIntent: PendingIntent = PendingIntent.getService(
                            this,
                            1,
                            Intent(this, UpdateWeatherService::class.java),
                            PendingIntent.FLAG_IMMUTABLE
                        )

                        RemoteViews(packageName, R.layout.widget_weather).apply {
                            setTextViewText(R.id.temperatureTextView, "에러")
                            setTextViewText(R.id.weatherTextView, "")
                            setOnClickPendingIntent(R.id.temperatureTextView, pendingServiceIntent)
                        }.also { views ->
                            val appWidgetName = ComponentName(this, WeatherAppWidgetProvider::class.java)
                            appWidgetManager.updateAppWidget(appWidgetName, views)
                        }
                        stopSelf()
                    }
                )
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL,
            "날씨앱",
            NotificationManager.IMPORTANCE_LOW
        ).apply {
            description = "위젯을 업데이트하는 채널"
        }

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, NOTIFICATION_CHANNEL)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("날씨앱")
            .setContentText("날씨 업데이트")
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()

        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    companion object {
        private const val NOTIFICATION_CHANNEL = "widget_refresh_channel"
    }
}