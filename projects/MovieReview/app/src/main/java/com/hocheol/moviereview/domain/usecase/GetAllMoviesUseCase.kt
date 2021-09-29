package com.hocheol.moviereview.domain.usecase

import com.hocheol.moviereview.data.repository.MovieRepository
import com.hocheol.moviereview.domain.model.Movie

class GetAllMoviesUseCase(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(): List<Movie> = movieRepository.getAllMovies()

}
