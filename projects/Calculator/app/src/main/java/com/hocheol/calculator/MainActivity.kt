package com.hocheol.calculator

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val tvExpression: TextView by lazy {
        findViewById(R.id.tv_expression)
    }

    private val tvResult: TextView by lazy {
        findViewById(R.id.tv_result)
    }

    private var isOperator = false
    private var hasOperator = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buttonClicked(v: View) {
        when (v.id) {
            R.id.btn_0 -> numberButtonClicked("0")
            R.id.btn_1 -> numberButtonClicked("1")
            R.id.btn_2 -> numberButtonClicked("2")
            R.id.btn_3 -> numberButtonClicked("3")
            R.id.btn_4 -> numberButtonClicked("4")
            R.id.btn_5 -> numberButtonClicked("5")
            R.id.btn_6 -> numberButtonClicked("6")
            R.id.btn_7 -> numberButtonClicked("7")
            R.id.btn_8 -> numberButtonClicked("8")
            R.id.btn_9 -> numberButtonClicked("9")
            R.id.btn_plus -> operatorButtonClicked("+")
            R.id.btn_minus -> operatorButtonClicked("-")
            R.id.btn_multi -> operatorButtonClicked("*")
            R.id.btn_divider -> operatorButtonClicked("/")
            R.id.btn_modulo -> operatorButtonClicked("%")
        }
    }

    private fun numberButtonClicked(number: String) {

        if (isOperator) {
            tvExpression.append(" ")
        }

        isOperator = false

        val expressionText = tvExpression.text.split(" ")
        if (expressionText.isNotEmpty() && expressionText.last().length >= 15) {
            Toast.makeText(this, "15자리 까지만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
            return
        } else if (expressionText.last().isEmpty() && number == "0") {
            Toast.makeText(this, "0은 제일 앞에 올 수 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        tvExpression.append(number)
        // TODO tvReuslt 실시간으로 계산 결과를 넣어야 하는 기능

    }

    private fun operatorButtonClicked(operator: String) {

        if (tvExpression.text.isEmpty()) {
            return
        }

        when {
            isOperator -> {
                val text = tvExpression.text.toString()
                tvExpression.text = text.dropLast(1) + operator
            }
            hasOperator -> {
                Toast.makeText(this, "연산자는 한 번만 사용할 수 있습니다.", Toast.LENGTH_SHORT).show()
                return
            }
            else -> {
                tvExpression.append(" $operator")
            }
        }

        val ssb = SpannableStringBuilder(tvExpression.text)
        ssb.setSpan(
            ForegroundColorSpan(getColor(R.color.green)),
            tvExpression.text.length - 1,
            tvExpression.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        tvExpression.text = ssb

        isOperator = true
        hasOperator = true

    }

    fun clearButtonClicked(v: View) {

    }

    fun historyButtonClicked(v: View) {

    }

    fun resultButtonClicked(v: View) {

    }

}