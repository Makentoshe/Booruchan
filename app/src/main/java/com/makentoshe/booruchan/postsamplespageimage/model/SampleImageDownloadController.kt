package com.makentoshe.booruchan.postsamplespageimage.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.makentoshe.booruapi.Post
import com.makentoshe.booruchan.Controller
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.booruchan.ImageRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.Exception

class SampleImageDownloadController(
    private val coroutineScope: CoroutineScope,
    private val sampleImageRepository: ImageRepository
) : Controller<DownloadResult<Bitmap>> {

    private val observable = BehaviorSubject.create<DownloadResult<Bitmap>>()
    private val disposables = CompositeDisposable()

    override fun subscribe(action: (DownloadResult<Bitmap>) -> Unit) {
        disposables.add(observable.subscribe(action))
    }

    fun action(post: DownloadResult<Post>) = coroutineScope.launch {
        if (post.data == null) {
            observable.onNext(DownloadResult(exception = post.exception))
            return@launch
        }
        try {
            val bytearray = sampleImageRepository.get(post.data.sampleUrl)!!
            val bitmap = BitmapFactory.decodeByteArray(bytearray, 0, bytearray.size)
            observable.onNext(DownloadResult(bitmap))
        } catch (e: Exception) {
            observable.onNext(DownloadResult(exception = e))
        }
    }

    fun clear() = disposables.clear()
}