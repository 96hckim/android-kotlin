package com.hocheol.financial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hocheol.financial.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.view = this
    }

    fun openShuffle() {

    }

    fun openVerifyOtp() {

    }
}