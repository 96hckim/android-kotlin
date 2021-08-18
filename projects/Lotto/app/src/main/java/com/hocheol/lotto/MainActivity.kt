package com.hocheol.lotto

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

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

    private val tvNumberList: List<TextView> by lazy {
        listOf<TextView>(
            findViewById(R.id.tv_num_1),
            findViewById(R.id.tv_num_2),
            findViewById(R.id.tv_num_3),
            findViewById(R.id.tv_num_4),
            findViewById(R.id.tv_num_5),
            findViewById(R.id.tv_num_6)
        )
    }

    private var didRun = false

    private val pickNumberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
        initClearButton()
    }

    private fun initRunButton() {
        btnRun.setOnClickListener {
            val list = getRandomNumber()

            didRun = true

            list.forEachIndexed { index, i ->
                val tvNumber = tvNumberList[index]

                tvNumber.isVisible = true
                tvNumber.text = i.toString()


                tvNumber.background = setNumberBackground(i)
            }
        }
    }

    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>().apply {
            for (i in 1..45) {
                if (pickNumberSet.contains(i)) {
                    continue
                }

                this.add(i)
            }
        }

        numberList.shuffle()

        val newList = pickNumberSet.toList() + numberList.subList(0, 6 - pickNumberSet.size)

        return newList.sorted()
    }

    private fun initAddButton() {
        btnAdd.setOnClickListener {

            if (didRun) {
                Toast.makeText(this, "초기화 후에 시도해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pickNumberSet.size >= 5) {
                Toast.makeText(this, "번호는 5개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pickNumberSet.contains(numberPicker.value)) {
                Toast.makeText(this, "이미 선택한 번호입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val tvNumber = tvNumberList[pickNumberSet.size]
            tvNumber.isVisible = true
            tvNumber.text = numberPicker.value.toString()

            tvNumber.background = setNumberBackground(numberPicker.value)

            pickNumberSet.add(numberPicker.value)

        }
    }

    private fun setNumberBackground(number: Int): Drawable? {
        return when (number) {
            in 1..10 -> ContextCompat.getDrawable(this, R.drawable.circle_yellow)
            in 11..20 -> ContextCompat.getDrawable(this, R.drawable.circle_blue)
            in 21..30 -> ContextCompat.getDrawable(this, R.drawable.circle_red)
            in 31..40 -> ContextCompat.getDrawable(this, R.drawable.circle_gray)
            else -> ContextCompat.getDrawable(this, R.drawable.circle_green)
        }
    }

    private fun initClearButton() {
        btnClear.setOnClickListener {
            pickNumberSet.clear()
            tvNumberList.forEach {
                it.isVisible = false
            }

            didRun = false
        }
    }

}