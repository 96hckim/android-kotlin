package com.hocheol.githubrepoexplorer.network

import com.hocheol.githubrepoexplorer.model.Repo
import com.hocheol.githubrepoexplorer.model.UserDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    @Headers("Authorization: Bearer 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")
    @GET("users/{username}/repos")
    fun listRepos(@Path("username") username: String): Call<List<Repo>>

    @Headers("Authorization: Bearer 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")
    @GET("search/users")
    fun searchUsers(@Query("q") query: String): Call<UserDto>
}