package com.hocheol.recorder

import android.os.Handler
import android.os.Looper

class Timer(private val listener: OnTimerTickListener) {

    private var duration: Long = 0L
    private val handler = Handler(Looper.getMainLooper())
    private val tickInterval: Long = 40L

    private val runnable: Runnable = object : Runnable {
        override fun run() {
            duration += tickInterval
            handler.postDelayed(this, tickInterval)
            listener.onTick(duration)
        }
    }

    fun start() {
        handler.postDelayed(runnable, tickInterval)
    }

    fun stop() {
        handler.removeCallbacks(runnable)
        duration = 0L
    }
}

interface OnTimerTickListener {
    fun onTick(duration: Long)
}