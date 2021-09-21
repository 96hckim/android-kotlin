package com.hocheol.githubrepository.data.response

import com.hocheol.githubrepository.data.entity.GithubRepoEntity

data class GithubRepoSearchResponse(
    val totalCount: Int,
    val items: List<GithubRepoEntity>
)
