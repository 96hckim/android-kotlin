package com.hocheol.movieapp.features.common.di

import com.hocheol.movieapp.features.common.network.api.IMovieAppNetworkApi
import com.hocheol.movieapp.features.common.network.api.MovieAppNetworkApi
import com.hocheol.movieapp.features.common.repository.IMovieDataSource
import com.hocheol.movieapp.features.common.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieDataModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(movieRepository: MovieRepository): IMovieDataSource

    @Binds
    @Singleton
    abstract fun bindNetwork(networkApi: MovieAppNetworkApi): IMovieAppNetworkApi

}