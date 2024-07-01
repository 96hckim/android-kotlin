package com.hocheol.hiltsns

import com.hocheol.data.UserDataStore
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FooEntryPoint {

    fun foo(): Foo

    fun userDataStore(): UserDataStore
}