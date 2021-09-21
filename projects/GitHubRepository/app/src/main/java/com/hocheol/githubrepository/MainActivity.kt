package com.hocheol.githubrepository

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.hocheol.githubrepository.data.database.DataBaseProvider
import com.hocheol.githubrepository.data.entity.GithubRepoEntity
import com.hocheol.githubrepository.databinding.ActivityMainBinding
import com.hocheol.githubrepository.view.RepositoryAdapter
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val repositoryDao by lazy { DataBaseProvider.provideDB(applicationContext).repositoryDao() }

    private lateinit var adapter: RepositoryAdapter

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
        initViews()
        bindViews()

    }

    override fun onResume() {
        super.onResume()

        launch(coroutineContext) {
            loadLikedRepositoryList()
        }
    }

    private suspend fun loadLikedRepositoryList() = withContext(Dispatchers.IO) {
        val repoList = repositoryDao.getRepositoryList()

        withContext(Dispatchers.Main) {
            setData(repoList)
        }
    }

    private fun setData(repoList: List<GithubRepoEntity>) = with(binding) {
        if (repoList.isEmpty()) {
            emptyResultTextView.isGone = false
            adapter.submitList(emptyList())
        } else {
            emptyResultTextView.isGone = true
            adapter.submitList(repoList)
        }
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
        recyclerView.adapter = adapter

        val decoration = DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
        recyclerView.addItemDecoration(decoration)
    }

    private fun bindViews() = with(binding) {
        searchFloatingButton.setOnClickListener {
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))
        }
    }

}