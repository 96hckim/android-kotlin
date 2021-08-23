package com.hocheol.calculator

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.room.Room
import com.hocheol.calculator.model.History

class MainActivity : AppCompatActivity() {

    private val tvExpression: TextView by lazy {
        findViewById(R.id.tv_expression)
    }

    private val tvResult: TextView by lazy {
        findViewById(R.id.tv_result)
    }

    private val layoutHistory: View by lazy {
        findViewById(R.id.layout_history)
    }

    private val linearLayoutHistory: LinearLayout by lazy {
        findViewById(R.id.linear_layout_history)
    }

    lateinit var db: AppDatabase

    private var isOperator = false
    private var hasOperator = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "historyDB"
        ).build()
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
        tvResult.text = calculateExpression()

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
        tvExpression.text = ""
        tvResult.text = ""
        isOperator = false
        hasOperator = false
    }

    fun historyButtonClicked(v: View) {
        layoutHistory.isVisible = true
        linearLayoutHistory.removeAllViews()

        Thread {
            db.historyDao().getAll().reversed().forEach {
                runOnUiThread {
                    val historyView = LayoutInflater.from(this).inflate(R.layout.history_row, null, false)
                    historyView.findViewById<TextView>(R.id.tv_expression).text = it.expression
                    historyView.findViewById<TextView>(R.id.tv_result).text = "= ${it.result}"

                    linearLayoutHistory.addView(historyView)
                }
            }
        }.start()
    }

    fun closeHistoryButtonClicked(v: View) {
        layoutHistory.isVisible = false
    }

    fun clearHistoryButtonClicked(v: View) {
        linearLayoutHistory.removeAllViews()

        Thread {
            db.historyDao().deleteAll()
        }.start()
    }

    fun resultButtonClicked(v: View) {
        val expressionTexts = tvExpression.text.split(" ")

        if (tvExpression.text.isEmpty() || expressionTexts.size == 1) {
            return
        }

        if (expressionTexts.size != 3 && hasOperator) {
            Toast.makeText(this, "아직 완성되지 않은 수식입니다.", Toast.LENGTH_SHORT).show()
            return
        }

        if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        val expressionText = tvExpression.text.toString()
        val resultText = calculateExpression()

        Thread {
            db.historyDao().insertHistory(History(null, expressionText, resultText))
        }.start()

        tvResult.text = ""
        tvExpression.text = resultText

        isOperator = false
        hasOperator = false

    }

    private fun calculateExpression(): String {
        val expressionTexts = tvExpression.text.split(" ")

        if (hasOperator.not() || expressionTexts.size != 3) {
            return ""
        } else if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()) {
            return ""
        }

        val exp1 = expressionTexts[0].toBigInteger()
        val exp2 = expressionTexts[2].toBigInteger()
        val op = expressionTexts[1]

        return when (op) {
            "+" -> (exp1 + exp2).toString()
            "-" -> (exp1 - exp2).toString()
            "*" -> (exp1 * exp2).toString()
            "/" -> (exp1 / exp2).toString()
            "%" -> (exp1 % exp2).toString()
            else -> ""
        }
    }

}

fun String.isNumber(): Boolean {
    return try {
        this.toBigInteger()
        true
    } catch (e: NumberFormatException) {
        false
    }
}