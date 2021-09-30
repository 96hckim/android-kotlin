package com.hocheol.moviereview.data.repository

import com.hocheol.moviereview.domain.model.Movie

interface MovieRepository {

    suspend fun getAllMovies(): List<Movie>

    suspend fun getMovies(movieIds: List<String>): List<Movie>

}
