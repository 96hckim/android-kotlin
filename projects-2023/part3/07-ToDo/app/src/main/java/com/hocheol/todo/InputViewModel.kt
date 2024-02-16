package com.hocheol.todo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hocheol.todo.model.ContentEntity
import com.hocheol.todo.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InputViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {

    private val _doneEvent = MutableLiveData<Unit>()
    val doneEvent: LiveData<Unit> = _doneEvent

    var item: ContentEntity? = null

    fun initData(item: ContentEntity) {
        this.item = item
    }

    fun insertData(content: String, memo: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            contentRepository.insert(
                item?.copy(
                    content = content,
                    memo = memo
                ) ?: ContentEntity(content = content, memo = memo)
            )
            _doneEvent.postValue(Unit)
        }
    }
}