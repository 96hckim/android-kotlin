package com.hocheol.data.di

import com.hocheol.data.usecase.login.ClearTokenUseCaseImpl
import com.hocheol.data.usecase.login.GetTokenUseCaseImpl
import com.hocheol.data.usecase.login.LoginUseCaseImpl
import com.hocheol.data.usecase.login.SetTokenUseCaseImpl
import com.hocheol.data.usecase.login.SignUpUseCaseImpl
import com.hocheol.data.usecase.main.setting.GetMyUserCaseImpl
import com.hocheol.data.usecase.main.setting.SetMyUserUseCaseImpl
import com.hocheol.data.usecase.main.setting.SetProfileImageUseCaseImpl
import com.hocheol.domain.usecase.login.ClearTokenUseCase
import com.hocheol.domain.usecase.login.GetTokenUseCase
import com.hocheol.domain.usecase.login.LoginUseCase
import com.hocheol.domain.usecase.login.SetTokenUseCase
import com.hocheol.domain.usecase.login.SignUpUseCase
import com.hocheol.domain.usecase.main.setting.GetMyUserUseCase
import com.hocheol.domain.usecase.main.setting.SetMyUserUseCase
import com.hocheol.domain.usecase.main.setting.SetProfileImageUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UserModule {

    @Binds
    abstract fun bindLoginUseCase(loginUseCaseImpl: LoginUseCaseImpl): LoginUseCase

    @Binds
    abstract fun bindSignUpUseCase(signUpUseCaseImpl: SignUpUseCaseImpl): SignUpUseCase

    @Binds
    abstract fun bindSetTokenUseCase(setTokenUseCaseImpl: SetTokenUseCaseImpl): SetTokenUseCase

    @Binds
    abstract fun bindGetTokenUseCase(getTokenUseCaseImpl: GetTokenUseCaseImpl): GetTokenUseCase

    @Binds
    abstract fun bindClearTokenUseCase(clearTokenUseCaseImpl: ClearTokenUseCaseImpl): ClearTokenUseCase

    @Binds
    abstract fun bindGetMyUserUseCase(getMyUserCaseImpl: GetMyUserCaseImpl): GetMyUserUseCase

    @Binds
    abstract fun bindSetMyUserUseCase(setMyUserUseCaseImpl: SetMyUserUseCaseImpl): SetMyUserUseCase

    @Binds
    abstract fun bindSetProfileImageUseCase(setProfileImageUseCaseImpl: SetProfileImageUseCaseImpl): SetProfileImageUseCase
}