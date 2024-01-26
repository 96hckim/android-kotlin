package com.hocheol.githubrepoexplorer

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hocheol.githubrepoexplorer.databinding.ActivityMainBinding
import com.hocheol.githubrepoexplorer.model.Repo
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
        gitHubService.listRepos("96hckim").enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                Log.e(TAG, "onResponse: ${response.body()?.toString()}")
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
            }
        })
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}