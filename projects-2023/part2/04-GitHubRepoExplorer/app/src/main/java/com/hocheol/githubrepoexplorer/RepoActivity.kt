package com.hocheol.githubrepoexplorer

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hocheol.githubrepoexplorer.adapter.RepoAdapter
import com.hocheol.githubrepoexplorer.databinding.ActivityRepoBinding
import com.hocheol.githubrepoexplorer.model.Repo
import com.hocheol.githubrepoexplorer.network.GitHubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepoBinding
    private lateinit var repoAdapter: RepoAdapter

    private var page = 0
    private var hasMore = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra("name") ?: return

        binding.userNameTextView.text = userName

        repoAdapter = RepoAdapter {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.htmlUrl))
            startActivity(intent)
        }
        val linearLayoutManager = LinearLayoutManager(this@RepoActivity)

        binding.repoRecyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = repoAdapter
        }

        binding.repoRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalCount = linearLayoutManager.itemCount
                val lastVisiblePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()

                if (lastVisiblePosition >= totalCount - 1 && hasMore) {
                    listRepos(userName, ++page)
                }
            }
        })

        listRepos(userName, 0)
    }

    private fun listRepos(userName: String, page: Int) {
        val gitHubService = APIClient.retrofit.create(GitHubService::class.java)
        gitHubService.listRepos(userName, page).enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                Log.e(TAG, "listRepos: ${response.body()?.toString()}")
                hasMore = response.body()?.count() == 30
                repoAdapter.submitList(repoAdapter.currentList + response.body().orEmpty())
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
            }
        })
    }

    companion object {
        private const val TAG = "RepoActivity"
    }
}