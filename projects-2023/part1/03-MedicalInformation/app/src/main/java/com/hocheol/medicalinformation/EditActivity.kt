package com.hocheol.medicalinformation

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.widget.ArrayAdapter
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
    }
}