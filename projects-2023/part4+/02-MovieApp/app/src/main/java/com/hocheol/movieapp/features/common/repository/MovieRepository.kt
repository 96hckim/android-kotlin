package com.hocheol.movieapp.features.common.repository

import com.hocheol.movieapp.features.common.network.api.IMovieAppNetworkApi
import com.hocheol.movieapp.library.network.model.ApiResponse
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieNetworkApi: IMovieAppNetworkApi
) : IMovieDataSource {
    override suspend fun getMovieList() {
        val data = movieNetworkApi.getMovies().response
        if (data is ApiResponse.Success) {
            val movieList = data.data
        }
    }
}