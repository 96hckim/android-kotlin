package com.hocheol.todo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.hocheol.todo.databinding.ActivityInputBinding
import com.hocheol.todo.model.ContentEntity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputBinding

    private val viewModel: InputViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputBinding.inflate(layoutInflater).apply {
            setContentView(root)
            lifecycleOwner = this@InputActivity
            view = this@InputActivity
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.contentEditText.addTextChangedListener {
            binding.confirmButton.isEnabled = !it?.toString().isNullOrEmpty()
        }

        (intent.getSerializableExtra(ITEM) as? ContentEntity)?.let {
            viewModel.initData(it)
            binding.contentEditText.setText(it.content)
            binding.memoEditText.setText(it.memo)
        }

        viewModel.doneEvent.observe(this) {
            Toast.makeText(this, "완료!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    fun onClickConfirm() {
        val content = binding.contentEditText.text?.toString().orEmpty()
        val memo = binding.memoEditText.text?.toString()
        viewModel.insertData(content, memo)
    }

    companion object {
        private const val ITEM = "item"

        fun launch(context: Context, item: ContentEntity? = null) {
            Intent(context, InputActivity::class.java).apply {
                putExtra(ITEM, item)
            }.run {
                context.startActivity(this)
            }
        }
    }
}