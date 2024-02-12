package com.hocheol.financial.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.view.children
import com.hocheol.financial.databinding.WidgetShuffleNumberKeyboardBinding
import kotlin.random.Random

class ShuffleNumberKeyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr) {

    private var _binding: WidgetShuffleNumberKeyboardBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = WidgetShuffleNumberKeyboardBinding.inflate(LayoutInflater.from(context), this, true)
        shuffle()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        _binding = null
    }

    fun shuffle() {
        val keyNumbers = (0..9).toMutableList()

        binding.gridLayout.children.forEach { view ->
            if (view is TextView && view.tag != null) {
                val randomIndex = Random.nextInt(keyNumbers.size)
                view.text = keyNumbers[randomIndex].toString()
                keyNumbers.removeAt(randomIndex)
            }
        }
    }
}