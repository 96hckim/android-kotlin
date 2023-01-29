package com.hocheol.unitconverter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.hocheol.unitconverter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var inputNumber: Int = 0
    private var cmToM = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.inputEditText.addTextChangedListener { text ->
            inputNumber = text?.toString()?.toIntOrNull() ?: 0
            swapUnitData()
        }

        binding.swapImageButton.setOnClickListener {
            cmToM = cmToM.not()
            swapUnitData()
        }
    }

    private fun swapUnitData() = with(binding) {
        if (cmToM) {
            inputUnitTextView.text = "cm"
            outputUnitTextView.text = "m"
            outputTextView.text = inputNumber.times(0.01).toString()
        } else {
            inputUnitTextView.text = "m"
            outputUnitTextView.text = "cm"
            outputTextView.text = inputNumber.times(100).toString()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(STATE_CM_TO_M, cmToM)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        cmToM = savedInstanceState.getBoolean(STATE_CM_TO_M)
        super.onRestoreInstanceState(savedInstanceState)
    }

    companion object {
        const val STATE_CM_TO_M = "cmToM"
    }
}