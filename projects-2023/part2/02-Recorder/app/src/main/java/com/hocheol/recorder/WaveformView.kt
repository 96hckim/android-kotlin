package com.hocheol.recorder

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class WaveformView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val ampList = mutableListOf<Float>()
    private val rectList = mutableListOf<RectF>()

    private val rectWidth = 15f
    private val rectHeightOffset = 3f
    private var tick = 0

    private val redPaint = Paint().apply {
        color = Color.RED
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rectList.forEach { canvas.drawRect(it, redPaint) }
    }

    private fun createRectF(amp: Float, index: Int): RectF {
        val rectF = RectF()
        val centerY = (this.height / 2).toFloat()
        rectF.top = centerY - amp / 2 - rectHeightOffset
        rectF.bottom = rectF.top + amp + rectHeightOffset
        rectF.left = index * rectWidth
        rectF.right = rectF.left + rectWidth - 5f // 여백을 위해 5를 더 줌
        return rectF
    }

    private fun updateRectList(amps: List<Float>) {
        rectList.clear()
        rectList.addAll(amps.mapIndexed { i, amp -> createRectF(amp, i) })
    }

    fun addAmplitude(maxAmplitude: Float) {
        val amplitude = (maxAmplitude / Short.MAX_VALUE) * this.height * 0.8f
        ampList.add(amplitude)
        updateRectList(ampList.takeLast((width / rectWidth).toInt()))
        invalidate()
    }

    fun replayAmplitude() {
        updateRectList(ampList.take(tick).takeLast((width / rectWidth).toInt()))
        tick++
        invalidate()
    }

    fun clearData() {
        ampList.clear()
        rectList.clear()
        invalidate()
    }

    fun clearWave() {
        rectList.clear()
        tick = 0
        invalidate()
    }
}