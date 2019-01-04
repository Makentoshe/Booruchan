package com.makentoshe.booruchan.postpage.model

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import com.makentoshe.booruchan.posts.model.PreviewsRepository
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.ReplaySubject
import java.io.Serializable
import java.lang.Exception

class PreviewsDownloadController(private val previewsRepository: PreviewsRepository) : Serializable {

    private var previewsObservable = ReplaySubject.create<Pair<Int, ByteArray>>()

    fun loadPreview(position: Int, url: String) {
        previewsObservable.onNext(Pair(position, previewsRepository.get(url)))
    }

    @SuppressLint("CheckResult")
    fun subscribe(action: (Pair<Int, ByteArray>) -> Unit) {
        previewsObservable.subscribe {
            Handler(Looper.getMainLooper()).post { action(it) }
        }
    }
}