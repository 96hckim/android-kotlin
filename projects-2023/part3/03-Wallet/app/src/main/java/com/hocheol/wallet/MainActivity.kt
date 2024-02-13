package com.hocheol.wallet

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import com.hocheol.wallet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.thirdCard.setOnClickListener(getCardClickListener(R.id.thirdCardOnTop))
        binding.secondCard.setOnClickListener(getCardClickListener(R.id.secondCardOnTop))
        binding.firstCard.setOnClickListener(getCardClickListener(R.id.firstCardOnTop))
    }

    private fun getCardClickListener(@IdRes endStateId: Int) = View.OnClickListener {
        with(binding) {
            when (root.currentState) {
                R.id.fanOut -> {
                    root.setTransition(R.id.fanOut, R.id.firstCardOnTop)
                    root.transitionToEnd()
                    collapsedCardCompletedListener(endStateId)
                }

                R.id.thirdCardOnTop -> {
                    if (R.id.thirdCardOnTop == endStateId) {

                    } else {
                        root.setTransition(R.id.thirdCardOnTop, endStateId)
                        root.transitionToEnd()
                    }
                }

                R.id.secondCardOnTop -> {
                    if (R.id.secondCardOnTop == endStateId) {

                    } else {
                        root.setTransition(R.id.secondCardOnTop, endStateId)
                        root.transitionToEnd()
                    }
                }

                R.id.firstCardOnTop -> {
                    if (R.id.firstCardOnTop == endStateId) {

                    } else {
                        root.setTransition(R.id.firstCardOnTop, endStateId)
                        root.transitionToEnd()
                    }
                }
            }
        }
    }

    private fun collapsedCardCompletedListener(@IdRes endStateId: Int) {
        with(binding) {
            root.setTransitionListener(object : TransitionAdapter() {
                override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                    if (currentId == R.id.firstCardOnTop) {
                        root.setTransition(R.id.firstCardOnTop, endStateId)
                        root.transitionToEnd()
                    }
                    root.setTransitionListener(null)
                }
            })
        }
    }
}