package com.hocheol.tomorrowhouse

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.hocheol.tomorrowhouse.data.ArticleModel
import com.hocheol.tomorrowhouse.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        val db = Firebase.firestore

        db.collection("articles").document("EQf3HZ4twBA7QLV4Pd6J")
            .get()
            .addOnSuccessListener { result ->
                val article = result.toObject<ArticleModel>()
                Log.d("HomeFragment", "article: $article")
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }
}