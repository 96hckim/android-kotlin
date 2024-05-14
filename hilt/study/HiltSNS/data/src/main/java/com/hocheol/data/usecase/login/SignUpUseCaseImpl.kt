package com.hocheol.data.usecase.login

import com.hocheol.data.model.SignUpParam
import com.hocheol.data.retrofit.UserService
import com.hocheol.domain.usecase.login.SignUpUseCase
import javax.inject.Inject

class SignUpUseCaseImpl @Inject constructor(
    private val userService: UserService
) : SignUpUseCase {

    override suspend fun invoke(id: String, username: String, password: String): Result<Boolean> = runCatching {
        val requestBody = SignUpParam(
            loginId = id,
            name = username,
            password = password,
            extraUserInfo = "",
            profileFilePath = ""
        ).toRequestBody()
        userService.signUp(requestBody = requestBody).result == "SUCCESS"
    }
}