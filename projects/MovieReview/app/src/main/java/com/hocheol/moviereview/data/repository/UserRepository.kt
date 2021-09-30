package com.hocheol.moviereview.data.repository

import com.hocheol.moviereview.domain.model.User

interface UserRepository {

    suspend fun getUser(): User?

    suspend fun saveUser(user: User)

}
