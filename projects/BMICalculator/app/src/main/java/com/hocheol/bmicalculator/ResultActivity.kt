package com.hocheol.bmicalculator

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class ResultActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "ResultActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val height = intent.getDoubleExtra("height", 0.0)
        val weight = intent.getDoubleExtra("weight", 0.0)

        Log.d(TAG, "height : $height , weight : $weight")

        val bmi = String.format("%.2f", weight / (height / 100).pow(2)).toFloat() // 소수점 2자리
        val resultText = when {
            bmi >= 35.0 -> "고도 비만"
            bmi >= 30.0 -> "중정도 비만"
            bmi >= 25.0 -> "경도 비만"
            bmi >= 23.0 -> "과체중"
            bmi >= 18.5 -> "정상체중"
            else -> "저체중"
        }

        val tvResultNumber = findViewById<TextView>(R.id.tv_result_number)
        val tvResultText = findViewById<TextView>(R.id.tv_result_text)

        tvResultNumber.text = bmi.toString()
        tvResultText.text = resultText

    }

}