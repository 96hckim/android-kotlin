package com.hocheol.moviereview.data.repository

import com.hocheol.moviereview.data.api.MovieApi
import com.hocheol.moviereview.domain.model.Movie
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val movieApi: MovieApi,
    private val dispatchers: CoroutineDispatcher
) : MovieRepository {

    override suspend fun getAllMovies(): List<Movie> = withContext(dispatchers) {
        movieApi.getAllMovies()
    }

//    override suspend fun getMovies(movieIds: List<String>): List<Movie> = withContext(dispatchers) {
//        movieApi.getMovies(movieIds)
//    }

}
