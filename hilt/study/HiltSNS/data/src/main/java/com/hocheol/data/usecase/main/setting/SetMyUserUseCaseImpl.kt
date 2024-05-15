package com.hocheol.data.usecase.main.setting

import com.hocheol.data.model.UpdateMyInfoParam
import com.hocheol.data.retrofit.UserService
import com.hocheol.domain.usecase.main.setting.GetMyUserUseCase
import com.hocheol.domain.usecase.main.setting.SetMyUserUseCase
import javax.inject.Inject

class SetMyUserUseCaseImpl @Inject constructor(
    private val userService: UserService,
    private val getMyUserUseCase: GetMyUserUseCase
) : SetMyUserUseCase {

    override suspend fun invoke(username: String?, profileImageUrl: String?): Result<Unit> = runCatching {
        val user = getMyUserUseCase().getOrThrow()
        val requestBody = UpdateMyInfoParam(
            userName = username ?: user.username,
            extraUserInfo = "",
            profileFilePath = profileImageUrl ?: user.profileImageUrl.orEmpty()
        ).toRequestBody()
        userService.patchMyPage(requestBody)
    }
}