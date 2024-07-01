package com.hocheol.hiltsns

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FooModule {

    @Provides
    fun provideFoo(): Foo {
        return Foo(tag = "FooModule")
    }
}

data class Foo(val tag: String)