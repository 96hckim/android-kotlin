package com.hocheol.secretdiary

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.NumberPicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    private val numberPicker1: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker1).apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val numberPicker2: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker2).apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val numberPicker3: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker3).apply {
            minValue = 0
            maxValue = 9
        }
    }

    private val openButton: AppCompatButton by lazy {
        findViewById(R.id.btn_open)
    }

    private val changePasswordButton: AppCompatButton by lazy {
        findViewById(R.id.btn_change_password)
    }

    private var changePasswordMode = false
    private lateinit var passwordPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker1
        numberPicker2
        numberPicker3

        passwordPreferences = getSharedPreferences("password", Context.MODE_PRIVATE) // 앱 간 공유 X

        initOpenButton()
        initChangePasswordButton()

    }

    private fun initOpenButton() {
        openButton.setOnClickListener {

            if (changePasswordMode) {
                Toast.makeText(this, "비밀번호 변경 중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {
                startActivity(Intent(this, DiaryActivity::class.java))
            } else {
                showErrorAlertDialog()
            }

        }
    }

    private fun initChangePasswordButton() {
        changePasswordButton.setOnClickListener {

            val passwordFromUser = "${numberPicker1.value}${numberPicker2.value}${numberPicker3.value}"

            if (changePasswordMode) {
                // 번호 저장 기능

                passwordPreferences.edit(true) {
                    putString("password", passwordFromUser)
                }

                changePasswordMode = false
                changePasswordButton.setBackgroundColor(Color.BLACK)

            } else {
                // changePasswordMode 가 활성화 :: 비밀번호가 맞는지를 체크

                if (passwordPreferences.getString("password", "000").equals(passwordFromUser)) {

                    changePasswordMode = true
                    Toast.makeText(this, "변경할 패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show()
                    changePasswordButton.setBackgroundColor(Color.RED)

                } else {
                    showErrorAlertDialog()
                }

            }

        }
    }

    private fun showErrorAlertDialog() {
        AlertDialog.Builder(this)
            .setTitle("실패!!")
            .setMessage("비밀번호가 잘못되었습니다.")
            .setPositiveButton("확인", null) // { _, _ -> }
            .create()
            .show()
    }

}