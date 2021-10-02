package com.hocheol.usedtrade.home

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.hocheol.usedtrade.DBKey.Companion.DB_ARTICLES
import com.hocheol.usedtrade.databinding.ActivityAddArticleBinding
import com.hocheol.usedtrade.gallery.GalleryActivity
import com.hocheol.usedtrade.photo.CameraActivity
import com.hocheol.usedtrade.photo.ImageListActivity
import com.hocheol.usedtrade.photo.PhotoListAdapter
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class AddArticleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddArticleBinding

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }
    private val storage: FirebaseStorage by lazy {
        Firebase.storage
    }
    private val articleDB: DatabaseReference by lazy {
        Firebase.database.reference.child(DB_ARTICLES)
    }

    private val photoListAdapter = PhotoListAdapter { uri -> removePhoto(uri) }

    private var imageUriList: ArrayList<Uri> = arrayListOf()

    private var galleryLauncher = registerForActivityResult(StartActivityForResult()) { result ->

        if (result.resultCode == Activity.RESULT_OK) {
            // 변경 전
//            val uri = result.data?.data
//            if (uri != null) {
//                imageUriList.add(uri)
//                photoListAdapter.setPhotoList(imageUriList)
//            } else {
//                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
//            }

            // 변경 후
            result.data?.let {
                val uriList = it.getParcelableArrayListExtra<Uri>(ImageListActivity.URI_LIST_KEY)
                uriList?.let { list ->
                    imageUriList.addAll(list)
                    photoListAdapter.setPhotoList(imageUriList)
                }
            } ?: kotlin.run {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private val cameraLauncher = registerForActivityResult(StartActivityForResult()) { result ->

        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.let {
                val uriList = it.getParcelableArrayListExtra<Uri>(ImageListActivity.URI_LIST_KEY)
                uriList?.let { list ->
                    imageUriList.addAll(list)
                    photoListAdapter.setPhotoList(imageUriList)
                }
            } ?: kotlin.run {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
    }

    private fun initViews() {
        binding.photoRecyclerView.adapter = photoListAdapter

        binding.imageAddButton.setOnClickListener {
            showPictureUploadDialog()
        }

        binding.submitButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()
            val content = binding.contentEditText.text.toString()

            if (TextUtils.isEmpty(title) && TextUtils.isEmpty(content)) {
                Toast.makeText(this, "제목과 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val userId = auth.currentUser?.uid.orEmpty()

            showProgress()

            if (imageUriList.isNotEmpty()) {
                lifecycleScope.launch {
                    val results = uploadPhoto(imageUriList)
                    afterUploadPhoto(results, title, content, userId)
                }
            } else {
                uploadArticle(userId, title, content, listOf())
            }
        }
    }

    private suspend fun uploadPhoto(uriList: List<Uri>) = withContext(Dispatchers.IO) {
        val uploadDeferred: List<Deferred<Any>> = uriList.mapIndexed { index, uri ->
            lifecycleScope.async {
                try {
                    val fileName = "${System.currentTimeMillis()}_${index}.png"
                    return@async storage
                        .reference
                        .child("article/photo")
                        .child(fileName)
                        .putFile(uri)
                        .await()
                        .storage
                        .downloadUrl
                        .await()
                        .toString()
                } catch (e: Exception) {
                    e.printStackTrace()
                    return@async Pair(uri, e)
                }
            }
        }

        return@withContext uploadDeferred.awaitAll()
    }

    private fun afterUploadPhoto(results: List<Any>, title: String, content: String, userId: String) {
        val errorResults = results.filterIsInstance<Pair<Uri, Exception>>()
        val successResults = results.filterIsInstance<String>()

        when {
            errorResults.isNotEmpty() && successResults.isNotEmpty() -> {
                photoUploadErrorButContinueDialog(errorResults, successResults, title, content, userId)
            }
            errorResults.isNotEmpty() && successResults.isEmpty() -> {
                uploadError()
            }
            else -> {
                uploadArticle(userId, title, content, results.filterIsInstance<String>())
            }
        }
    }

    private fun uploadArticle(userId: String, title: String, content: String, imageUrlList: List<String>) {
        val model = ArticleModel(userId, title, System.currentTimeMillis(), content, imageUrlList)
        articleDB.push().setValue(model)

        hideProgress()
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_CODE ->
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startGalleryScreen()
                } else {
                    Toast.makeText(this, "권한을 거부하셨습니다.", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun startGalleryScreen() {
        // 변경 전
//        val intent = Intent(Intent.ACTION_GET_CONTENT)
//        intent.type = "image/*"
//        galleryLauncher.launch(intent)

        // 변경 후
        val intent = Intent(this, GalleryActivity::class.java)
        galleryLauncher.launch(intent)
    }

    private fun startCameraScreen() {
        val intent = Intent(this, CameraActivity::class.java)
        cameraLauncher.launch(intent)
    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다.")
            .setMessage("사진을 가져오기 위해 필요합니다.")
            .setPositiveButton("동의") { _, _ ->
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
            }
            .create()
            .show()
    }

    private fun showPictureUploadDialog() {
        AlertDialog.Builder(this)
            .setTitle("사진첨부")
            .setMessage("사진첨부할 방식을 선택하세요")
            .setPositiveButton("카메라") { _, _ ->
                startCameraScreen()
            }
            .setNegativeButton("갤러리") { _, _ ->
                checkExternalStoragePermission {
                    startGalleryScreen()
                }
            }
            .create()
            .show()
    }

    private fun photoUploadErrorButContinueDialog(
        errorResults: List<Pair<Uri, Exception>>,
        successResults: List<String>,
        title: String,
        content: String,
        userId: String
    ) {
        AlertDialog.Builder(this)
            .setTitle("특정 이미지 업로드 실패")
            .setMessage("업로드에 실패한 이미지가 있습니다." + errorResults.map { (uri, _) ->
                "$uri\n"
            } + "그럼에도 불구하고 업로드 하시겠습니까?")
            .setPositiveButton("업로드") { _, _ ->
                uploadArticle(userId, title, content, successResults)
            }
            .create()
            .show()
    }

    private fun uploadError() {
        Toast.makeText(this, "사진 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show()
        hideProgress()

    }

    private fun checkExternalStoragePermission(uploadFunction: () -> Unit) {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
                uploadFunction()
            }
            shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                showPermissionContextPopup()
            }
            else -> {
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun removePhoto(uri: Uri) {
        imageUriList.remove(uri)
        photoListAdapter.setPhotoList(imageUriList)
    }

    private fun showProgress() {
        binding.progressBar.isVisible = true
    }

    private fun hideProgress() {
        binding.progressBar.isVisible = false
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1010
    }

}