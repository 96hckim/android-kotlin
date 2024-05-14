package com.hocheol.data.usecase.login

import com.hocheol.data.UserDataStore
import com.hocheol.domain.usecase.login.ClearTokenUseCase
import javax.inject.Inject

class ClearTokenUseCaseImpl @Inject constructor(
    private val userDataStore: UserDataStore
) : ClearTokenUseCase {

    override suspend fun invoke(): Result<Unit> = runCatching {
        userDataStore.clear()
    }
}