package com.makentoshe.booruchan.application.android.screen.samples.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.makentoshe.booruchan.application.android.common.toBitmap
import com.makentoshe.booruchan.application.core.arena.Arena
import com.makentoshe.booruchan.core.post.Content
import com.makentoshe.booruchan.core.post.Post
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifIOException

class SampleAnimationFragmentViewModel(
    /** The source of preview and sample images */
    private val post: Post,
    /** Arena for displaying preview image */
    private val previewArena: Arena<Content, ByteArray>,
    /** Arena for displaying sample image */
    private val sampleArena: Arena<Content, ByteArray>
) : ViewModel() {

    /** Subject for displaying preview image if it exists */
    private val previewSubject = BehaviorSubject.create<Bitmap>()
    val previewObservable: Observable<Bitmap> = previewSubject

    /** Subject for displaying sample image */
    private val sampleSubject = BehaviorSubject.create<GifDrawable>()
    val sampleObservable: Observable<GifDrawable> = sampleSubject

    /** Subject for displaying error messages */
    private val exceptionSubject = BehaviorSubject.create<Throwable>()
    val exceptionObservable: Observable<Throwable> = exceptionSubject

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = sampleArena.suspendFetch(post.sampleContent)
            result.fold({
                try {
                    sampleSubject.onNext(GifDrawable(it))
                } catch (gioe: GifIOException) { // GifError 103: Data is not in GIF format
                    exceptionSubject.onNext(gioe)
                }
            }, {
                exceptionSubject.onNext(it)
            })
        }

        viewModelScope.launch(Dispatchers.IO) {
            // if preview loading was failed - just ignore it (preview is not our purpose)
            val result = previewArena.suspendFetch(post.previewContent)
            result.getOrNull()?.toBitmap()?.let(previewSubject::onNext)
        }
    }

    class Factory(
        private val post: Post,
        private val previewArena: Arena<Content, ByteArray>,
        private val sampleArena: Arena<Content, ByteArray>
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return SampleAnimationFragmentViewModel(post, previewArena, sampleArena) as T
        }
    }
}
