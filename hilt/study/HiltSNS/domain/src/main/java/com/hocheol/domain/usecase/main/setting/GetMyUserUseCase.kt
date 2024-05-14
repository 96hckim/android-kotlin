package com.hocheol.domain.usecase.main.setting

import com.hocheol.domain.model.User

interface GetMyUserUseCase {

    suspend operator fun invoke(): Result<User>
}