package com.hocheol.data.usecase.login

import com.hocheol.data.UserDataStore
import com.hocheol.domain.usecase.login.GetTokenUseCase
import javax.inject.Inject

class GetTokenUseCaseImpl @Inject constructor(
    private val userDataStore: UserDataStore
) : GetTokenUseCase {

    override suspend fun invoke(): String? {
        return userDataStore.getToken()
    }
}