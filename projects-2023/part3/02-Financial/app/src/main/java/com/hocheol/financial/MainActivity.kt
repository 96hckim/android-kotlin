package com.hocheol.financial

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hocheol.financial.databinding.ActivityMainBinding
import com.hocheol.financial.util.AppSignatureHelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.view = this
        AppSignatureHelper(this).apply {
            Log.d("hash", "hash : $appSignature")
        }
    }

    fun openShuffle() {
        startActivity(Intent(this, PinActivity::class.java))
    }

    fun openVerifyOtp() {
        startActivity(Intent(this, IdentityInputActivity::class.java))
    }
}