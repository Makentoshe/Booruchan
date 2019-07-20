package com.makentoshe.webmview

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.ExoPlayerFactory

/** Holds player to avoid recreations */
class WebmFragmentViewModel(application: Application) : AndroidViewModel(application) {

    /** Player instance */
    val player = ExoPlayerWrapper(
        ExoPlayerFactory.newSimpleInstance(getApplication()),
        getApplication())

    /** Release player on destroy */
    override fun onCleared() = player.release()

    class Factory(
        private val application: Application, private val onCreate: WebmFragmentViewModel.() -> Unit
    ): ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return WebmFragmentViewModel(application).apply(onCreate) as T
        }
    }
}