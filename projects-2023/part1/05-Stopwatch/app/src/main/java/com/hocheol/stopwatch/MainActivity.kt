package com.hocheol.stopwatch

import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import com.hocheol.stopwatch.databinding.ActivityMainBinding
import com.hocheol.stopwatch.databinding.DialogCountdownSettingBinding
import java.util.Timer
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var countdownSeconds = 10
    private var currentCountdownDeciSeconds = countdownSeconds * 10
    private var currentDeciSeconds = 0

    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.countdownValueTextView.setOnClickListener {
            showCountdownSettingDialog()
        }

        binding.startButton.setOnClickListener {
            start()
            binding.startButton.visibility = View.GONE
            binding.stopButton.visibility = View.GONE
            binding.pauseButton.visibility = View.VISIBLE
            binding.lapButton.visibility = View.VISIBLE
        }

        binding.stopButton.setOnClickListener {
            showStopAlertDialog()
        }

        binding.pauseButton.setOnClickListener {
            pause()
            binding.startButton.visibility = View.VISIBLE
            binding.stopButton.visibility = View.VISIBLE
            binding.pauseButton.visibility = View.GONE
            binding.lapButton.visibility = View.GONE
        }

        binding.lapButton.setOnClickListener {
            lap()
        }

        initCountdownViews()
    }

    private fun initCountdownViews() {
        binding.countdownValueTextView.text = String.format("%02d", countdownSeconds)
        binding.countdownProgressBar.progress = INITIAL_COUNTDOWN_MAX
    }

    private fun start() {
        timer = timer(initialDelay = 0, period = 100L) {
            if (currentCountdownDeciSeconds == 0) {
                currentDeciSeconds++

                val totalSeconds = currentDeciSeconds.div(10)
                val minutes = totalSeconds / 60
                val seconds = totalSeconds % 60
                val deciSeconds = currentDeciSeconds % 10

                runOnUiThread {
                    binding.timeTextView.text = String.format("%02d:%02d", minutes, seconds)
                    binding.tickTextView.text = deciSeconds.toString()
                    binding.countdownGroup.visibility = View.GONE
                }
            } else {
                currentCountdownDeciSeconds--

                val seconds = currentCountdownDeciSeconds / 10
                val progress = ((currentCountdownDeciSeconds / (countdownSeconds * 10F)) * INITIAL_COUNTDOWN_MAX).toInt()

                binding.root.post {
                    binding.countdownValueTextView.text = String.format("%02d", seconds)
                    binding.countdownProgressBar.progress = progress
                }
            }

            if (currentDeciSeconds == 0 && currentCountdownDeciSeconds <= 30 && currentCountdownDeciSeconds % 10 == 0) {
                val toneType = if (currentCountdownDeciSeconds == 0) {
                    ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD
                } else {
                    ToneGenerator.TONE_PROP_BEEP
                }
                ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME)
                    .startTone(toneType, 100)
            }
        }
    }

    private fun stop() {
        binding.startButton.visibility = View.VISIBLE
        binding.stopButton.visibility = View.VISIBLE
        binding.pauseButton.visibility = View.GONE
        binding.lapButton.visibility = View.GONE

        currentDeciSeconds = 0
        // stop 한 뒤 다시 start 하면, 그때도 카운트다운 실행되도록
        currentCountdownDeciSeconds = countdownSeconds * 10

        binding.timeTextView.text = "00:00"
        binding.tickTextView.text = "0"

        binding.countdownGroup.visibility = View.VISIBLE
        initCountdownViews()

        binding.lapContainer.removeAllViews()
    }

    private fun pause() {
        timer?.cancel()
        timer = null
    }

    private fun lap() {
        if (currentDeciSeconds == 0) return

        val container = binding.lapContainer
        TextView(this).apply {
            textSize = 20F
            gravity = Gravity.CENTER
            val totalSeconds = currentDeciSeconds.div(10)
            val minutes = totalSeconds / 60
            val seconds = totalSeconds % 60
            val deciSeconds = currentDeciSeconds % 10
            text = "${container.childCount.inc()}. " + String.format("%02d:%02d %01d", minutes, seconds, deciSeconds)
            setPadding(30)
        }.let { lapTextView ->
            container.addView(lapTextView, 0)
        }
    }

    private fun showCountdownSettingDialog() {
        AlertDialog.Builder(this).apply {
            val countdownSettingBinding = DialogCountdownSettingBinding.inflate(layoutInflater)
            with(countdownSettingBinding.countdownNumberPicker) {
                maxValue = 20
                minValue = 0
                value = countdownSeconds
            }
            setTitle("카운트다운 설정")
            setView(countdownSettingBinding.root)
            setPositiveButton("확인") { _, _ ->
                countdownSeconds = countdownSettingBinding.countdownNumberPicker.value
                currentCountdownDeciSeconds = countdownSeconds * 10
                binding.countdownValueTextView.text = String.format("%02d", countdownSeconds)
            }
            setNegativeButton("취소", null)
        }.show()
    }

    private fun showStopAlertDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("종료하시겠습니까?")
            setPositiveButton("네") { _, _ ->
                stop()
            }
            setNegativeButton("아니오", null)
        }.show()
    }

    companion object {
        private const val INITIAL_COUNTDOWN_MAX = 100
    }
}