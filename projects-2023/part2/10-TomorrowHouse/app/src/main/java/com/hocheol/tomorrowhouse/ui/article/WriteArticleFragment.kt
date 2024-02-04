package com.hocheol.tomorrowhouse.ui.article

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.hocheol.tomorrowhouse.R
import com.hocheol.tomorrowhouse.data.ArticleModel
import com.hocheol.tomorrowhouse.databinding.FragmentWriteArticleBinding
import java.util.UUID

class WriteArticleFragment : Fragment(R.layout.fragment_write_article) {

    private lateinit var binding: FragmentWriteArticleBinding

    private var selectedImageUri: Uri? = null
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            selectedImageUri = uri
            binding.photoImageView.setImageURI(uri)
            binding.addButton.isVisible = false
            binding.deleteButton.isVisible = true
        } else {
            showSnackbar("이미지가 선택되지 않았습니다.")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWriteArticleBinding.bind(view)

        startPicker()

        setupBackButton()
        setupSubmitButton()
        setupPhotoImageView()
        setupDeleteButton()
    }

    private fun startPicker() {
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun setupBackButton() {
        binding.backButton.setOnClickListener {
            findNavController().navigate(WriteArticleFragmentDirections.actionBack())
        }
    }

    private fun setupSubmitButton() {
        binding.submitButton.setOnClickListener {
            selectedImageUri?.let { uri ->
                showProgress()

                uploadImage(
                    uri = uri,
                    successHandler = {
                        uploadArticle(it, binding.descriptionEditText.text.toString())
                    },
                    errorHandler = {
                        it?.printStackTrace()
                        showSnackbar("이미지 업로드에 실패했습니다.")
                        hideProgress()
                    }
                )
            } ?: run {
                showSnackbar("이미지가 선택되지 않았습니다.")
            }
        }
    }

    private fun uploadImage(
        uri: Uri,
        successHandler: (String) -> Unit,
        errorHandler: (Throwable?) -> Unit
    ) {
        val fileName = "${UUID.randomUUID()}.png"
        Firebase.storage.reference.child("articles/photo").child(fileName)
            .putFile(uri)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Firebase.storage.reference.child("articles/photo").child(fileName)
                        .downloadUrl
                        .addOnSuccessListener { downloadUrl ->
                            successHandler(downloadUrl.toString())
                        }
                        .addOnFailureListener {
                            errorHandler(it)
                        }
                } else {
                    errorHandler(task.exception)
                }
            }
    }

    private fun uploadArticle(photoUrl: String, description: String) {
        val articleId = UUID.randomUUID().toString()
        val articleModel = ArticleModel(
            articleId = articleId,
            createdAt = System.currentTimeMillis(),
            description = description,
            imageUrl = photoUrl
        )

        Firebase.firestore.collection("articles").document(articleId)
            .set(articleModel)
            .addOnSuccessListener {
                findNavController().navigate(WriteArticleFragmentDirections.actionWriteArticleFragmentToHomeFragment())
                hideProgress()
            }
            .addOnFailureListener {
                it.printStackTrace()
                showSnackbar("글 작성에 실패했습니다.")
                hideProgress()
            }
    }

    private fun setupPhotoImageView() {
        binding.photoImageView.setOnClickListener {
            if (selectedImageUri == null) {
                startPicker()
            }
        }
    }

    private fun setupDeleteButton() {
        binding.deleteButton.setOnClickListener {
            binding.photoImageView.setImageURI(null)
            selectedImageUri = null
            binding.addButton.isVisible = true
            binding.deleteButton.isVisible = false
        }
    }

    private fun showProgress() {
        binding.progressBarLayout.isVisible = true
    }

    private fun hideProgress() {
        binding.progressBarLayout.isVisible = false
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}