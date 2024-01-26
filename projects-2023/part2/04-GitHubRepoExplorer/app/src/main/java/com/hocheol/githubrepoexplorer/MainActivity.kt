package com.hocheol.githubrepoexplorer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.hocheol.githubrepoexplorer.adapter.UserAdapter
import com.hocheol.githubrepoexplorer.databinding.ActivityMainBinding
import com.hocheol.githubrepoexplorer.model.Repo
import com.hocheol.githubrepoexplorer.model.UserDto
import com.hocheol.githubrepoexplorer.network.GitHubService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val gitHubService = retrofit.create(GitHubService::class.java)
        gitHubService.listRepos("square").enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                Log.e(TAG, "listRepos: ${response.body()?.toString()}")
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
            }
        })

        val userAdapter = UserAdapter()

        binding.userRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }

        gitHubService.searchUsers("squar").enqueue(object : Callback<UserDto> {
            override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                Log.e(TAG, "searchUsers: ${response.body()?.toString()}")

                userAdapter.submitList(response.body()?.users)
            }

            override fun onFailure(call: Call<UserDto>, t: Throwable) {
            }
        })
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}