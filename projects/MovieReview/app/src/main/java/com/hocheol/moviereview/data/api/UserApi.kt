package com.hocheol.moviereview.data.api

import com.hocheol.moviereview.domain.model.User

interface UserApi {

    suspend fun saveUser(user: User): User

}
