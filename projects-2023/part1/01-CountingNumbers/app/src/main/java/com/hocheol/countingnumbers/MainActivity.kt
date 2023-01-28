package com.hocheol.countingnumbers

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val numberTextView by lazy { findViewById<TextView>(R.id.numberTextView) }
    private val resetButton by lazy { findViewById<Button>(R.id.resetButton) }
    private val plusButton by lazy { findViewById<Button>(R.id.plusButton) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var number = savedInstanceState?.getInt(NUMBER_KEY) ?: 0
        setNumber(number)

        resetButton.setOnClickListener {
            number = 0
            setNumber(number)
        }

        plusButton.setOnClickListener {
            number += 1
            setNumber(number)
        }
    }

    private fun setNumber(number: Int) {
        numberTextView.text = number.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        numberTextView.text?.toString()?.toIntOrNull()?.let { number ->
            outState.putInt(NUMBER_KEY, number)
        }
    }

    companion object {
        private const val NUMBER_KEY = "number_key"
    }
}
