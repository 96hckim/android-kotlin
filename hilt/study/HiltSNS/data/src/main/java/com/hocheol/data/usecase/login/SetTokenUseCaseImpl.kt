package com.hocheol.data.usecase.login

import com.hocheol.data.UserDataStore
import com.hocheol.domain.usecase.login.SetTokenUseCase
import javax.inject.Inject

class SetTokenUseCaseImpl @Inject constructor(
    private val userDataStore: UserDataStore
) : SetTokenUseCase {

    override suspend fun invoke(token: String) {
        userDataStore.setToken(token)
    }
}