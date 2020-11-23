package com.makentoshe.booruchan.application.android.screen.samples.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.makentoshe.booruchan.application.android.common.toBitmap
import com.makentoshe.booruchan.application.core.arena.Arena
import com.makentoshe.booruchan.core.post.Image
import com.makentoshe.booruchan.core.post.Post
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SampleFragmentViewModel(
    /** The source of preview and sample images */
    private val post: Post,
    /** Arena for displaying preview image if it is exists */
    private val previewArena: Arena<Image, ByteArray>
) : ViewModel() {

    /** Subject for displaying preview image if it exists */
    private val previewSubject = BehaviorSubject.create<Bitmap>()
    val previewObservable: Observable<Bitmap> = previewSubject

    /** Subject for displaying error messages */
    private val exceptionSubject = BehaviorSubject.create<Throwable>()
    val exceptionObservable: Observable<Throwable> = exceptionSubject

    init {
        viewModelScope.launch(Dispatchers.IO) {
            // if preview loading was failed - just ignore it (preview is not our purpose)
            val result = previewArena.suspendFetch(post.previewImage)
            result.getOrNull()?.toBitmap()?.let(previewSubject::onNext)
        }
    }

    class Factory(
        private val previewArena: Arena<Image, ByteArray>,
        private val post: Post
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SampleFragmentViewModel(post, previewArena) as T
        }
    }
}