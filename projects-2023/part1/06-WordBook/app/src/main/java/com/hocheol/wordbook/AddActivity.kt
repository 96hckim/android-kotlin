package com.hocheol.wordbook

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import com.google.android.material.chip.Chip
import com.hocheol.wordbook.databinding.ActivityAddBinding
import kotlin.concurrent.thread

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private val appDatabase by lazy { AppDatabase.getInstance(this) }
    private val originWord by lazy {
        intent.getParcelableExtra<Word>(WordConstants.ORIGIN_WORD_KEY)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        binding.addButton.setOnClickListener {
            if (originWord == null) addWord() else editWord()
        }
    }

    private fun initViews() {
        val types = listOf("명사", "동사", "대명사", "형용사", "부사", "감탄사", "전치사", "접속사")
        binding.typeChipGroup.apply {
            types.forEach { type ->
                addView(createChip(type))
            }
        }

        originWord?.let { word ->
            binding.addButton.text = "수정"
            binding.wordTextInputEditText.setText(word.text)
            binding.meanTextInputEditText.setText(word.mean)
            val selectedChip: Chip? = binding.typeChipGroup.children.firstOrNull {
                it is Chip && it.text == word.type
            } as? Chip
            selectedChip?.isChecked = true
        }
    }

    private fun createChip(type: String): Chip {
        return Chip(this).apply {
            text = type
            isCheckable = true
            isClickable = true
        }
    }

    private fun addWord() {
        val text = binding.wordTextInputEditText.text.toString()
        val mean = binding.meanTextInputEditText.text.toString()
        val type = findViewById<Chip>(binding.typeChipGroup.checkedChipId).text.toString()
        val word = Word(text, mean, type)

        thread {
            appDatabase.wordDao().insert(word)
            runOnUiThread {
                Toast.makeText(this, "저장을 완료했습니다.", Toast.LENGTH_SHORT).show()
            }
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun editWord() {
        val text = binding.wordTextInputEditText.text.toString()
        val mean = binding.meanTextInputEditText.text.toString()
        val type = findViewById<Chip>(binding.typeChipGroup.checkedChipId).text.toString()
        val editWord = originWord?.copy(text = text, mean = mean, type = type)

        thread {
            editWord?.let { word ->
                appDatabase.wordDao().update(word)
                runOnUiThread {
                    Toast.makeText(this, "수정을 완료했습니다.", Toast.LENGTH_SHORT).show()
                }
                setResult(RESULT_OK, intent.putExtra(WordConstants.EDIT_WORD_KEY, word))
                finish()
            }
        }
    }
}