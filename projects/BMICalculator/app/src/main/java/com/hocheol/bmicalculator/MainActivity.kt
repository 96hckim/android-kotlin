package com.hocheol.bmicalculator

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtHeight: EditText = findViewById(R.id.edt_height)
        val edtWeight = findViewById<EditText>(R.id.edt_weight)

        val btnConfirm: Button = findViewById(R.id.btn_confirm)

        btnConfirm.setOnClickListener {
            Log.d(TAG, "확인 버튼이 클릭되었습니다.")

            if (edtHeight.text.isEmpty() || edtWeight.text.isEmpty()) {
                Toast.makeText(this, "빈 값이 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 이 아래로는 절대 빈 값이 올 수 없음.

            val height: Int = edtHeight.text.toString().toInt()
            val weight: Int = edtWeight.text.toString().toInt()

            Log.d(TAG, "height : $height , weight : $weight")
        }
    }

}