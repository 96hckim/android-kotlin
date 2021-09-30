package com.hocheol.moviereview.data.api

import com.google.firebase.firestore.FirebaseFirestore
import com.hocheol.moviereview.domain.model.User
import kotlinx.coroutines.tasks.await

class UserFirestoreApi(
    private val firestore: FirebaseFirestore
) : UserApi {

    override suspend fun saveUser(user: User): User =
        firestore.collection("users")
            .add(user)
            .await()
            .let { User(it.id) }

}
