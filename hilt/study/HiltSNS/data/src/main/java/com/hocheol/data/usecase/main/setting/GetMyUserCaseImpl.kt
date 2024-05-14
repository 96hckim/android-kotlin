package com.hocheol.data.usecase.main.setting

import com.hocheol.data.model.toDomainModel
import com.hocheol.data.retrofit.UserService
import com.hocheol.domain.model.User
import com.hocheol.domain.usecase.main.setting.GetMyUserUseCase
import javax.inject.Inject

class GetMyUserCaseImpl @Inject constructor(
    private val userService: UserService
) : GetMyUserUseCase {

    override suspend fun invoke(): Result<User> = runCatching {
        userService.myPage().data.toDomainModel()
    }
}