package com.makentoshe.booruchan.postpreview.model

import com.makentoshe.booruapi.Posts
import com.makentoshe.booruchan.Controller
import com.makentoshe.booruchan.DownloadResult
import com.makentoshe.repository.PostsRepository
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class PostsDownloadController(
    private val coroutineScope: CoroutineScope,
    private val repository: PostsRepository
) : Controller<DownloadResult<Posts>> {

    private val observable = BehaviorSubject.create<DownloadResult<Posts>>()
    private val disposables = CompositeDisposable()

    override fun subscribe(action: (DownloadResult<Posts>) -> Unit) {
        disposables.add(observable.subscribe(action))
    }

    fun action(page: Int) = coroutineScope.launch {
        if (page < 0) observable.onNext(
            DownloadResult(
                exception = IllegalArgumentException(
                    "Page num can't be less 0"
                )
            )
        )
        else observable.onNext(performDownload(page))
    }

    private fun performDownload(page: Int): DownloadResult<Posts> {
        return try {
            DownloadResult(repository.get(page))
        } catch (e: Exception) {
            DownloadResult(exception = e)
        }
    }

    override fun clear() = disposables.clear()
}