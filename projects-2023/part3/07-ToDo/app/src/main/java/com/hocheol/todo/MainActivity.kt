package com.hocheol.todo

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.hocheol.todo.databinding.ActivityMainBinding
import com.hocheol.todo.model.ContentEntity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    private val adapter by lazy { ContentListAdapter(Handler()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.view = this

        binding.recyclerView.adapter = adapter
        val decoration = DividerItemDecoration(this, LinearLayout.VERTICAL)
        binding.recyclerView.addItemDecoration(decoration)

        lifecycleScope.launch {
            viewModel.contentList
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collectLatest {
                    binding.emptyTextView.isVisible = it.isEmpty()
                    binding.recyclerView.isVisible = it.isNotEmpty()

                    adapter.submitList(it)
                }
        }
    }

    fun onClickAdd() {
        InputActivity.launch(this)
    }

    inner class Handler {
        fun onClickItem(item: ContentEntity) {
            InputActivity.launch(this@MainActivity, item)
        }

        fun onLongClickItem(item: ContentEntity): Boolean {
            viewModel.deleteItem(item)
            Toast.makeText(this@MainActivity, "삭제 완료", Toast.LENGTH_SHORT).show()
            return false
        }

        fun onCheckedItem(item: ContentEntity, isChecked: Boolean) {
            viewModel.updateItem(item.copy(isDone = isChecked))
        }
    }
}