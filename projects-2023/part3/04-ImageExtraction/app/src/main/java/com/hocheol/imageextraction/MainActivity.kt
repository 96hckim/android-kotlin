package com.hocheol.imageextraction

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hocheol.imageextraction.databinding.ActivityMainBinding
import com.hocheol.imageextraction.mvc.MvcActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.view = this
    }

    fun openMVC() {
        startActivity(Intent(this, MvcActivity::class.java))
    }

    fun openMVP() {
    }

    fun openMVVM() {
    }

    fun openMVI() {
    }
}