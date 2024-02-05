package com.hocheol.tomorrowhouse.ui.article

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hocheol.tomorrowhouse.R
import com.hocheol.tomorrowhouse.data.ArticleModel
import com.hocheol.tomorrowhouse.databinding.FragmentArticleBinding

class ArticleFragment : Fragment(R.layout.fragment_article) {

    private lateinit var binding: FragmentArticleBinding
    private val args: ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticleBinding.bind(view)

        val articleId = args.articleId

        binding.toolbar.setupWithNavController(findNavController())

        Firebase.firestore.collection("articles").document(articleId)
            .get()
            .addOnSuccessListener {
                val article = it.toObject(ArticleModel::class.java)

                Glide.with(binding.thumbnailImageView)
                    .load(article?.imageUrl)
                    .into(binding.thumbnailImageView)

                binding.descriptionTextView.text = article?.description
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }
}