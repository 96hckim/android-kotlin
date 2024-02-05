package com.hocheol.tomorrowhouse.ui.bookmark

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hocheol.tomorrowhouse.R
import com.hocheol.tomorrowhouse.data.ArticleModel
import com.hocheol.tomorrowhouse.databinding.FragmentBookmarkBinding

class BookMarkArticleFragment : Fragment(R.layout.fragment_bookmark) {

    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var articleAdapter: BookmarkArticleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookmarkBinding.bind(view)

        articleAdapter = BookmarkArticleAdapter { article ->

        }

        binding.articleRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = articleAdapter
        }

        val uid = Firebase.auth.currentUser?.uid.orEmpty()
        Firebase.firestore.collection("bookmark").document(uid)
            .get()
            .addOnSuccessListener {
                val articleIds = it.get("articleIds") as List<*>

                if (articleIds.isNotEmpty()) {
                    Firebase.firestore.collection("articles")
                        .whereIn("articleId", articleIds)
                        .get()
                        .addOnSuccessListener { result ->
                            val articles = result.map { article ->
                                article.toObject(ArticleModel::class.java)
                            }
                            articleAdapter.submitList(articles)
                        }
                        .addOnFailureListener {
                            it.printStackTrace()
                        }
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }
}