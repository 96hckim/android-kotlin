package com.hocheol.tomorrowhouse.ui.bookmark

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.hocheol.tomorrowhouse.R
import com.hocheol.tomorrowhouse.data.ArticleModel
import com.hocheol.tomorrowhouse.databinding.FragmentBookmarkBinding
import com.hocheol.tomorrowhouse.ui.home.ArticleItem

class BookMarkArticleFragment : Fragment(R.layout.fragment_bookmark) {

    private lateinit var binding: FragmentBookmarkBinding
    private lateinit var articleAdapter: BookmarkArticleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookmarkBinding.bind(view)

        binding.toolbar.setupWithNavController(findNavController())

        articleAdapter = BookmarkArticleAdapter { articleItem ->
            findNavController().navigate(
                BookMarkArticleFragmentDirections.actionBookMarkArticleFragmentToArticleFragment(
                    articleId = articleItem.articleId
                )
            )
        }

        binding.articleRecyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = articleAdapter
        }

        val uid = Firebase.auth.currentUser?.uid.orEmpty()
        Firebase.firestore.collection("bookmarks").document(uid)
            .get()
            .addOnSuccessListener {
                val bookmarkList = it.get("articleIds") as? List<*>

                if (!bookmarkList.isNullOrEmpty()) {
                    Firebase.firestore.collection("articles")
                        .whereIn("articleId", bookmarkList)
                        .get()
                        .addOnSuccessListener { result ->
                            val articleItems = result.map { snapshot ->
                                snapshot.toObject<ArticleModel>()
                            }.map { model ->
                                ArticleItem(
                                    articleId = model.articleId.orEmpty(),
                                    description = model.description.orEmpty(),
                                    imageUrl = model.imageUrl.orEmpty(),
                                    isBookMark = bookmarkList.contains(model.articleId.orEmpty())
                                )
                            }

                            articleAdapter.submitList(articleItems)
                        }
                        .addOnFailureListener { e ->
                            e.printStackTrace()
                        }
                }
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }
}