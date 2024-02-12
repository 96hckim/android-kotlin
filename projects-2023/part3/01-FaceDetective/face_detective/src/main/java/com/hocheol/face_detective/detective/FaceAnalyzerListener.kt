package com.hocheol.face_detective.detective

import android.graphics.PointF
import android.graphics.RectF
import android.util.SizeF

interface FaceAnalyzerListener {

    fun detected()

    fun stopDetect()

    fun notDetected()

    fun detectProgress(progress: Float, message: String)

    fun faceSize(rectF: RectF, sizeF: SizeF, pointF: PointF)
}