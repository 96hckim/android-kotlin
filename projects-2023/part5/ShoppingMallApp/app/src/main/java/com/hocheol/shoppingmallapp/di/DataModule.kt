package com.hocheol.shoppingmallapp.di

import com.hocheol.data.repository.CategoryRepositoryImpl
import com.hocheol.data.repository.MainRepositoryImpl
import com.hocheol.data.repository.ProductDetailRepositoryImpl
import com.hocheol.domain.repository.CategoryRepository
import com.hocheol.domain.repository.MainRepository
import com.hocheol.domain.repository.ProductDetailRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindMainRepository(mainRepositoryImpl: MainRepositoryImpl): MainRepository

    @Binds
    @Singleton
    fun bindCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    @Singleton
    fun bindProductDetailRepository(productDetailRepositoryImpl: ProductDetailRepositoryImpl): ProductDetailRepository
}