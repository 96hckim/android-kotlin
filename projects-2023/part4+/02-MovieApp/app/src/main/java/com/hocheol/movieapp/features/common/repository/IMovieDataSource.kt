package com.hocheol.movieapp.features.common.repository

interface IMovieDataSource {
    suspend fun getMovieList()
}