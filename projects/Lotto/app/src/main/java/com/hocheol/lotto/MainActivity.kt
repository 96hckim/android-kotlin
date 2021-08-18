package com.hocheol.lotto

import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val btnClear: Button by lazy {
        findViewById(R.id.btn_clear)
    }

    private val btnAdd: Button by lazy {
        findViewById(R.id.btn_add)
    }

    private val btnRun: Button by lazy {
        findViewById(R.id.btn_run)
    }

    private val numberPicker: NumberPicker by lazy {
        findViewById(R.id.picker)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
    }

    private fun initRunButton() {

    }

}