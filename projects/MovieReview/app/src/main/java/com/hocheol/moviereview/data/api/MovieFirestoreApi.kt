package com.hocheol.moviereview.data.api

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.hocheol.moviereview.domain.model.Movie
import kotlinx.coroutines.tasks.await

class MovieFirestoreApi(
    private val firestore: FirebaseFirestore
) : MovieApi {

    override suspend fun getAllMovies(): List<Movie> =
        firestore.collection("movies")
            .get()
            .await()
            .map { it.toObject() }

//    override suspend fun getMovies(movieIds: List<String>): List<Movie> =
//        firestore.collection("movies")
//            .whereIn(FieldPath.documentId(), movieIds)
//            .get()
//            .await()
//            .map { it.toObject<Movie>() }

}
