package com.hocheol.imageextraction

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hocheol.imageextraction.databinding.ActivityMainBinding
import com.hocheol.imageextraction.mvc.MvcActivity
import com.hocheol.imageextraction.mvi.MviActivity
import com.hocheol.imageextraction.mvp.MvpActivity
import com.hocheol.imageextraction.mvvm.MvvmActivity

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
        startActivity(Intent(this, MvpActivity::class.java))
    }

    fun openMVVM() {
        startActivity(Intent(this, MvvmActivity::class.java))
    }

    fun openMVI() {
        startActivity(Intent(this, MviActivity::class.java))
    }
}