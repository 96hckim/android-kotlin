package com.hocheol.githubrepoexplorer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hocheol.githubrepoexplorer.adapter.RepoAdapter
import com.hocheol.githubrepoexplorer.databinding.ActivityRepoBinding
import com.hocheol.githubrepoexplorer.model.Repo
import com.hocheol.githubrepoexplorer.network.GitHubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepoBinding
    private lateinit var repoAdapter: RepoAdapter

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent.getStringExtra("name") ?: return

        binding.userNameTextView.text = userName

        repoAdapter = RepoAdapter()

        binding.repoRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = repoAdapter
        }

        listRepos(userName)
    }

    private fun listRepos(userName: String) {
        val gitHubService = retrofit.create(GitHubService::class.java)
        gitHubService.listRepos(userName).enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                Log.e(TAG, "listRepos: ${response.body()?.toString()}")

                repoAdapter.submitList(response.body())
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
            }
        })
    }

    companion object {
        private const val TAG = "RepoActivity"
    }
}