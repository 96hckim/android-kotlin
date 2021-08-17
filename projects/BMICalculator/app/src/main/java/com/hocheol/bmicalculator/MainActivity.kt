package com.hocheol.bmicalculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edtHeight: EditText = findViewById(R.id.edt_height)
        val edtWeight = findViewById<EditText>(R.id.edt_weight)

        val btnConfirm: Button = findViewById(R.id.btn_confirm)

        btnConfirm.setOnClickListener {

            if (edtHeight.text.isEmpty() || edtWeight.text.isEmpty()) {
                Toast.makeText(this, "빈 값이 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 이 아래로는 절대 빈 값이 올 수 없음.

            val height = edtHeight.text.toString().toDouble()
            val weight = edtWeight.text.toString().toDouble()

            val intent = Intent(this, ResultActivity::class.java)

            intent.putExtra("height", height)
            intent.putExtra("weight", weight)

            startActivity(intent)

        }
    }

}