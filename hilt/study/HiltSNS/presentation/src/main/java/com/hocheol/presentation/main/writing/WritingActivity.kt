package com.hocheol.presentation.main.writing

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.hocheol.hiltsns.ui.theme.ConnectedTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WritingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConnectedTheme {
                WritingNavHost(
                    onFinish = { finish() }
                )
            }
        }
    }
}