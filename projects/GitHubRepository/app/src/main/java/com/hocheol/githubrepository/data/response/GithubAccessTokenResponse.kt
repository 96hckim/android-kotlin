package com.hocheol.githubrepository.data.response

data class GithubAccessTokenResponse(
    val accessToken: String,
    val scope: String,
    val tokenType: String
)
