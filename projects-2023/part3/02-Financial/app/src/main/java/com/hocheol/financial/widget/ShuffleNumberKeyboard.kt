package com.hocheol.financial.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.core.view.children
import com.hocheol.financial.databinding.WidgetShuffleNumberKeyboardBinding
import kotlin.random.Random

class ShuffleNumberKeyboard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private var _binding: WidgetShuffleNumberKeyboardBinding? = null
    private val binding get() = _binding!!

    private var listener: KeyPadListener? = null

    init {
        _binding = WidgetShuffleNumberKeyboardBinding.inflate(LayoutInflater.from(context), this, true)
        binding.view = this
        binding.clickListener = this
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

    fun setKeyPadListener(keyPadListener: KeyPadListener) {
        this.listener = keyPadListener
    }

    fun onClickDelete() {
        listener?.onClickDelete()
    }

    fun onClickDone() {
        listener?.onClickDone()
    }

    interface KeyPadListener {
        fun onClickNumber(num: String)
        fun onClickDelete()
        fun onClickDone()
    }

    override fun onClick(v: View?) {
        v ?: return
        if (v is TextView && v.tag != null) {
            listener?.onClickNumber(v.text.toString())
        }
    }
}