package com.hocheol.shoppingmallapp.di

import com.hocheol.data.repository.AccountRepositoryImpl
import com.hocheol.data.repository.BasketRepositoryImpl
import com.hocheol.data.repository.CategoryRepositoryImpl
import com.hocheol.data.repository.LikeRepositoryImpl
import com.hocheol.data.repository.MainRepositoryImpl
import com.hocheol.data.repository.ProductDetailRepositoryImpl
import com.hocheol.data.repository.PurchaseHistoryRepositoryImpl
import com.hocheol.data.repository.SearchRepositoryImpl
import com.hocheol.domain.repository.AccountRepository
import com.hocheol.domain.repository.BasketRepository
import com.hocheol.domain.repository.CategoryRepository
import com.hocheol.domain.repository.LikeRepository
import com.hocheol.domain.repository.MainRepository
import com.hocheol.domain.repository.ProductDetailRepository
import com.hocheol.domain.repository.PurchaseHistoryRepository
import com.hocheol.domain.repository.SearchRepository
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

    @Binds
    @Singleton
    fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository

    @Binds
    @Singleton
    fun bindAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    fun bindLikeRepository(likeRepositoryImpl: LikeRepositoryImpl): LikeRepository

    @Binds
    @Singleton
    fun bindBasketRepository(basketRepositoryImpl: BasketRepositoryImpl): BasketRepository

    @Binds
    @Singleton
    fun bindPurchaseHistoryRepository(purchaseHistoryRepositoryImpl: PurchaseHistoryRepositoryImpl): PurchaseHistoryRepository
}