package com.hocheol.data.usecase.login

import com.hocheol.data.model.LoginParam
import com.hocheol.data.retrofit.UserService
import com.hocheol.domain.usecase.login.LoginUseCase
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val userService: UserService
) : LoginUseCase {

    override suspend fun invoke(id: String, password: String): Result<String> = runCatching {
        val requestBody = LoginParam(loginId = id, password = password).toRequestBody()
        userService.login(requestBody = requestBody).data
    }
}