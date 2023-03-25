package com.hocheol.medicalinformation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.hocheol.medicalinformation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 정보 편집
        binding.editButton.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            startActivity(intent)
        }

        // 정보 삭제
        binding.deleteButton.setOnClickListener {
            deleteData()
        }

        // 전화 앱 실행
        binding.emergencyContactLayer.setOnClickListener {
            with(Intent(Intent.ACTION_VIEW)) {
                val phoneNumber = binding.emergencyContactValueTextView.text.toString()
                    .replace("-", "")
                data = Uri.parse("tel:$phoneNumber")
                startActivity(this)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        updateUIWithData()
    }

    private fun updateUIWithData() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)) {
            binding.nameValueTextView.text = getString(NAME, "미정")
            binding.birthValueTextView.text = getString(BIRTHDATE, "미정")
            binding.bloodTypeValueTextView.text = getString(BLOOD_TYPE, "미정")
            binding.emergencyContactValueTextView.text = getString(EMERGENCY_CONTACT, "미정")

            val warning = getString(WARNING, "")
            val isWarningVisible = warning.isNullOrEmpty().not()
            binding.warningTitleTextView.isVisible = isWarningVisible
            binding.warningValueTextView.isVisible = isWarningVisible
            if (isWarningVisible) {
                binding.warningValueTextView.text = warning
            }
        }
    }

    private fun deleteData() {
        with(getSharedPreferences(USER_INFORMATION, MODE_PRIVATE).edit()) {
            clear()
            apply()
        }

        updateUIWithData()

        Toast.makeText(this, "초기화를 완료했습니다.", Toast.LENGTH_SHORT).show()
    }
}
