package com.hocheol.wallet

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.hocheol.wallet.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val adapter = DetailListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.cardTitleTextView.text = intent.getStringExtra(CARD_NAME)
        binding.cardLayout.backgroundTintList = intent.getParcelableExtra(CARD_COLOR) as? ColorStateList
        binding.historyRecyclerView.adapter = adapter
    }

    companion object {
        private const val CARD_NAME = "CARD_NAME"
        private const val CARD_COLOR = "CARD_COLOR"

        fun launch(
            context: Context,
            cardName: String,
            cardColor: ColorStateList?,
            optionsCompat: ActivityOptionsCompat
        ) {
            Intent(context, DetailActivity::class.java).apply {
                putExtra(CARD_NAME, cardName)
                putExtra(CARD_COLOR, cardColor)
            }.run {
                context.startActivity(this, optionsCompat.toBundle())
            }
        }
    }
}