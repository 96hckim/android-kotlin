package com.hocheol.imageextraction.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hocheol.imageextraction.mvvm.model.Image
import com.hocheol.imageextraction.mvvm.repository.ImageRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable

class MvvmViewModel(
    private val imageRepository: ImageRepository
) : ViewModel() {

    private val _countLiveData = MutableLiveData<Int>()
    val countLiveData: LiveData<Int> by lazy { _countLiveData }

    private val _imageLiveData = MutableLiveData<Image>()
    val imageLiveData: LiveData<Image> by lazy { _imageLiveData }

    private var disposable: CompositeDisposable? = CompositeDisposable()
    private var count = 0

    fun loadRandomImage() {
        disposable?.add(
            imageRepository.getRandomImage()
                .doOnSuccess {
                    count++
                }
                .subscribe { image ->
                    _imageLiveData.value = image
                    _countLiveData.value = count
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable = null
    }

    class MvvmViewModelFactory(
        private val imageRepository: ImageRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MvvmViewModel(imageRepository) as T
        }
    }
}