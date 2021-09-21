package com.hocheol.githubrepository

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hocheol.githubrepository.databinding.ActivitySearchBinding
import com.hocheol.githubrepository.utillity.RetrofitUtil
import com.hocheol.githubrepository.view.RepositoryAdapter
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SearchActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var binding: ActivitySearchBinding
    private lateinit var adapter: RepositoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initViews()
        bindViews()

    }

    private fun initAdapter() {
        adapter = RepositoryAdapter(
            searchResultItemClickListener = {
                startActivity(
                    Intent(this, RepositoryActivity::class.java).apply {
                        putExtra(RepositoryActivity.REPOSITORY_OWNER_KEY, it.owner.login)
                        putExtra(RepositoryActivity.REPOSITORY_NAME_KEY, it.name)
                    }
                )
            }
        )
    }

    private fun initViews() = with(binding) {
        emptyResultTextView.isGone = true
        recyclerView.adapter = adapter

        val decoration = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(decoration)
    }

    private fun bindViews() = with(binding) {
        searchButton.setOnClickListener {
            val searchKeyword = searchEditText.text?.toString() ?: ""

            if (searchKeyword.isEmpty()) {
                Toast.makeText(this@SearchActivity, "검색어를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            searchKeyword(searchKeyword)
        }
    }

    private fun searchKeyword(keyword: String) = launch {
        withContext(Dispatchers.IO) {
            val response = RetrofitUtil.githubApiService.searchRepositories(keyword)

            if (response.isSuccessful) {
                val body = response.body()

                withContext(Dispatchers.Main) {
                    body?.let {
                        adapter.submitList(it.items)
                        if (it.totalCount == 0) {
                            binding.emptyResultTextView.isVisible = true
                        }
                    }
                }
            }
        }
    }

}