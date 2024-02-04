package com.hocheol.tomorrowhouse.ui.article

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.hocheol.tomorrowhouse.R
import com.hocheol.tomorrowhouse.databinding.FragmentWriteArticleBinding
import java.util.UUID

class WriteArticleFragment : Fragment(R.layout.fragment_write_article) {

    private lateinit var binding: FragmentWriteArticleBinding

    private var selectedUri: Uri? = null
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            selectedUri = uri
            binding.photoImageView.setImageURI(uri)
        } else {
            showSnackbar("이미지가 선택되지 않았습니다.")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWriteArticleBinding.bind(view)

        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        binding.backButton.setOnClickListener {
            findNavController().navigate(WriteArticleFragmentDirections.actionBack())
        }

        binding.submitButton.setOnClickListener {
            selectedUri?.let { uri ->
                val fileName = "${UUID.randomUUID()}.png"
                Firebase.storage.reference.child("articles/photo").child(fileName)
                    .putFile(uri)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Firebase.storage.reference.child("articles/photo").child(fileName)
                                .downloadUrl
                                .addOnSuccessListener { downloadUrl ->
                                    Log.d("WriteArticleFragment", "downloadUrl: $downloadUrl")
                                }
                                .addOnFailureListener {
                                    it.printStackTrace()
                                }
                            showSnackbar("사진 올리기가 성공하였습니다.")
                        } else {
                            task.exception?.printStackTrace()
                            showSnackbar("사진 올리기가 실패하였습니다.")
                        }
                    }
            } ?: run {
                showSnackbar("이미지가 선택되지 않았습니다.")
            }
        }

        binding.addButton.setOnClickListener {

        }

        binding.deleteButton.setOnClickListener {

        }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}