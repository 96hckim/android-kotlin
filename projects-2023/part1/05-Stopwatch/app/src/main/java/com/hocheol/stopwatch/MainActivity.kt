package com.hocheol.stopwatch

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.hocheol.stopwatch.databinding.ActivityMainBinding
import com.hocheol.stopwatch.databinding.DialogCountdownSettingBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var countdownSeconds: Int = 10

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
    }

    private fun start() {

    }

    private fun stop() {
        binding.startButton.visibility = View.VISIBLE
        binding.stopButton.visibility = View.VISIBLE
        binding.pauseButton.visibility = View.GONE
        binding.lapButton.visibility = View.GONE
    }

    private fun pause() {

    }

    private fun lap() {

    }

    private fun showCountdownSettingDialog() {
        AlertDialog.Builder(this).apply {
            val countdownSettingBinding = DialogCountdownSettingBinding.inflate(layoutInflater)
            with(countdownSettingBinding.countdownNumberPicker) {
                maxValue = 20
                minValue = 0
                value = countdownSeconds
            }
            setView(countdownSettingBinding.root)
            setPositiveButton("확인") { _, _ ->
                countdownSeconds = countdownSettingBinding.countdownNumberPicker.value
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
}