package com.hocheol.tomorrowhouse.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.hocheol.tomorrowhouse.R
import com.hocheol.tomorrowhouse.data.ArticleModel
import com.hocheol.tomorrowhouse.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var articleAdapter: HomeArticleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        setupWriteButton(view)
        setupBookmarkImageButton()
        setupRecyclerView()

        Firebase.firestore.collection("articles")
            .get()
            .addOnSuccessListener { result ->
                val articles = result.map {
                    it.toObject<ArticleModel>()
                }

                articleAdapter.submitList(articles)
            }
    }

    private fun setupWriteButton(view: View) {
        binding.writeButton.setOnClickListener {
            if (Firebase.auth.currentUser != null) {
                val action = HomeFragmentDirections.actionHomeFragmentToWriteArticleFragment()
                findNavController().navigate(action)
            } else {
                Snackbar.make(view, "로그인 후 사용해주세요.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupBookmarkImageButton() {
        binding.bookmarkImageButton.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToBookMarkArticleFragment())
        }
    }

    private fun setupRecyclerView() {
        articleAdapter = HomeArticleAdapter { article ->
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToArticleFragment(
                    articleId = article.articleId.orEmpty()
                )
            )
        }

        binding.homeRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = articleAdapter
        }
    }
}