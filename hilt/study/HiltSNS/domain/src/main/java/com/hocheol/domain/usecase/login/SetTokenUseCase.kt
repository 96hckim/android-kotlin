package com.hocheol.domain.usecase.login

interface SetTokenUseCase {

    suspend operator fun invoke(token: String)
}