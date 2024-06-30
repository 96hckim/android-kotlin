package com.hocheol.hiltextensionapp

import com.hocheol.annotations.InstallBinding
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

@InstallBinding(component = SingletonComponent::class)
class AuthenticatorImpl @Inject constructor() : Authenticator