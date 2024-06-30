package com.hocheol.assistedinjection

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class GetUserInfoUseCase @AssistedInject constructor(
    @Assisted private val userNo: Long,
    private val userService: UserService
) {

    fun getUser(): User {
        // 네트워크 작업 (UserService)
        return User()
    }
}

@AssistedFactory
interface GetUserInfoUseCaseFactory {
    fun create(userNo: Long): GetUserInfoUseCase
}