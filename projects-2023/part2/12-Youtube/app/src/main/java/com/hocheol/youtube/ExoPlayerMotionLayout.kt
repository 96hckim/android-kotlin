package com.hocheol.youtube

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout

class ExoPlayerMotionLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MotionLayout(context, attrs, defStyleAttr) {

    var targetView: View? = null
    private val gestureDetector by lazy {
        GestureDetector(context, object : SimpleOnGestureListener() {
            override fun onScroll(e1: MotionEvent?, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
                val view = targetView
                return if (view != null && e1 != null) {
                    view.containTouchArea(e1.x.toInt(), e1.y.toInt())
                } else {
                    false
                }
            }
        })
    }

    override fun onInterceptTouchEvent(event: MotionEvent?): Boolean {
        return if (event != null) {
            gestureDetector.onTouchEvent(event)
        } else {
            false
        }
    }

    private fun View.containTouchArea(x: Int, y: Int): Boolean {
        return x in this.left..this.right && y in this.top..this.bottom
    }
}