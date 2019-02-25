package com.makentoshe.booruchan.postsamples

import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Booru
import com.makentoshe.viewmodel.ViewModel

class PostSamplesContentViewModel : ViewModel() {
    var position = 0
        private set
    private lateinit var booru: Booru

    class Factory(
        private val position: Int,
        private val booru: Booru
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = PostSamplesContentViewModel()
            viewModel.position = position
            viewModel.booru = booru

            return viewModel as T
        }
    }
}