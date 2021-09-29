package com.hocheol.moviereview.data.api

import com.hocheol.moviereview.domain.model.Movie

interface MovieApi {

    suspend fun getAllMovies(): List<Movie>

//    suspend fun getMovies(movieIds: List<String>): List<Movie>

}
