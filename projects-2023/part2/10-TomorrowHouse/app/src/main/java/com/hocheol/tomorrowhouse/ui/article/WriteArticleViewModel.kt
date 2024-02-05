package com.hocheol.tomorrowhouse.ui.article

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WriteArticleViewModel : ViewModel() {

    private var _selectedImageUri = MutableLiveData<Uri?>()
    var selectedImageUri: LiveData<Uri?> = _selectedImageUri

    fun updateSelectedImageUri(uri: Uri?) {
        _selectedImageUri.value = uri
    }
}