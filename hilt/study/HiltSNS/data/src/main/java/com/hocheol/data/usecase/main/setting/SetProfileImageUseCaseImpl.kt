package com.hocheol.data.usecase.main.setting

import com.hocheol.domain.usecase.file.GetImageUseCase
import com.hocheol.domain.usecase.file.UploadImageUseCase
import com.hocheol.domain.usecase.main.setting.SetMyUserUseCase
import com.hocheol.domain.usecase.main.setting.SetProfileImageUseCase
import javax.inject.Inject

class SetProfileImageUseCaseImpl @Inject constructor(
    private val getImageUseCase: GetImageUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val setMyUserUseCase: SetMyUserUseCase
) : SetProfileImageUseCase {

    override suspend fun invoke(contentUri: String): Result<Unit> = runCatching {
        // 1. 이미지 정보 가져오기
        val image = getImageUseCase(contentUri) ?: throw NullPointerException("이미지를 찾을 수 없습니다.")

        // 2. 이미지 업로드 하기
        val imagePath = uploadImageUseCase(image).getOrThrow()

        // 3. 내 정보 업데이트 하기
        setMyUserUseCase(profileImageUrl = imagePath).getOrThrow()
    }
}