package com.hocheol.domain.usecase.login

interface ClearTokenUseCase {

    suspend operator fun invoke(): Result<Unit>
}