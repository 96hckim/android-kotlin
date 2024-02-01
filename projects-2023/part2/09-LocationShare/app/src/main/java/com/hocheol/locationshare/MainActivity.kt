package com.hocheol.locationshare

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hocheol.locationshare.databinding.ActivityMainBinding
import com.kakao.sdk.common.util.Utility

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var keyHash = Utility.getKeyHash(this)
        Log.d(TAG, "keyHash: $keyHash")
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}