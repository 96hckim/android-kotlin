package com.hocheol.wordbook

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hocheol.wordbook.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), WordAdapter.ItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var wordAdapter: WordAdapter
    private var selectedWord: Word? = null
    private val appDatabase by lazy { AppDatabase.getInstance(this) }
    private val addWordActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            updateLatestWord()
        }
    }

    private val editWordActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val editWord = result.data?.getParcelableExtra<Word>(WordConstants.EDIT_WORD_KEY)
        if (result.resultCode == RESULT_OK && editWord != null) {
            updateEditWord(editWord)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            addWordActivityResult.launch(intent)
        }

        binding.deleteImageView.setOnClickListener {
            deleteSelectedWord()
        }

        binding.editImageView.setOnClickListener {
            editSelectedWord()
        }
    }

    private fun initRecyclerView() {
        wordAdapter = WordAdapter(mutableListOf(), this)
        binding.wordRecyclerView.apply {
            adapter = wordAdapter
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            val dividerItemDecoration = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }

        thread {
            val list = appDatabase.wordDao().getAll()
            wordAdapter.list.addAll(list)
            runOnUiThread {
                wordAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun updateLatestWord() {
        thread {
            val latestWord = appDatabase.wordDao().getLatestWord()
            wordAdapter.list.add(0, latestWord)
            runOnUiThread {
                wordAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun updateEditWord(word: Word) {
        val index = wordAdapter.list.indexOfFirst { it.id == word.id }
        wordAdapter.list[index] = word
        runOnUiThread {
            selectedWord = word
            wordAdapter.notifyItemChanged(index)
            binding.wordTextView.text = word.text
            binding.meanTextView.text = word.mean
        }
    }

    private fun deleteSelectedWord() {
        selectedWord ?: return

        thread {
            selectedWord?.let { word ->
                appDatabase.wordDao().delete(word)
                runOnUiThread {
                    wordAdapter.list.remove(word)
                    wordAdapter.notifyDataSetChanged()
                    binding.wordTextView.text = ""
                    binding.meanTextView.text = ""
                    Toast.makeText(this, "삭제가 완료됐습니다.", Toast.LENGTH_SHORT).show()
                }
                selectedWord = null
            }
        }
    }

    private fun editSelectedWord() {
        selectedWord ?: return

        val intent = Intent(this, AddActivity::class.java).apply {
            putExtra(WordConstants.ORIGIN_WORD_KEY, selectedWord)
        }
        editWordActivityResult.launch(intent)
    }

    override fun onClick(word: Word) {
        selectedWord = word
        binding.wordTextView.text = word.text
        binding.meanTextView.text = word.mean
    }
}