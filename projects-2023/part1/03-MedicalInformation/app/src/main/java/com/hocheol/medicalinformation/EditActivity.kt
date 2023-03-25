package com.hocheol.medicalinformation

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.hocheol.medicalinformation.databinding.ActivityEditBinding

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding

    private val birthDatePickerDialog by lazy {
        val listener = OnDateSetListener { _, year, month, dayOfMonth ->
            binding.birthValueTextView.text = "$year-${month.inc()}-$dayOfMonth"
        }
        DatePickerDialog(
            this,
            listener,
            2000,
            1,
            1
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 생년월일
        binding.birthLayer.setOnClickListener {
            birthDatePickerDialog.show()
        }

        // 혈핵형
        binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
            this,
            R.array.blood_types,
            android.R.layout.simple_list_item_1
        )

        // 주의사항
        binding.warningCheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.warningValueEditText.isVisible = isChecked
        }
        binding.warningValueEditText.isVisible = binding.warningCheckBox.isChecked

        // 저장
        binding.saveButton.setOnClickListener {
            saveData()
            finish()
        }
    }

    private fun saveData() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE).edit()) {
            putString(NAME, binding.nameValueEditText.text.toString())
            putString(BLOOD_TYPE, getBloodType())
            putString(EMERGENCY_CONTACT, binding.emergencyContactValueEditText.text.toString())
            putString(BIRTHDATE, binding.birthValueTextView.text.toString())
            putString(WARNING, getWarning())
            apply()
        }

        Toast.makeText(this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun getBloodType(): String {
        val bloodAlphabet = binding.bloodTypeSpinner.selectedItem.toString()
        val bloodSign = if (binding.bloodTypePlus.isChecked) "+" else "-"
        return "$bloodSign$bloodAlphabet"
    }

    private fun getWarning(): String {
        return if (binding.warningCheckBox.isChecked) binding.warningValueEditText.text.toString() else ""
    }
}
