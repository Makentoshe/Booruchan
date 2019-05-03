package com.makentoshe.booruchan.screen.webmplayer

import androidx.lifecycle.ViewModel
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.DownloadListener
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WebmPlayerViewModel(
    private val booru: Booru, post: Post, private val disposables: CompositeDisposable
) : ViewModel(), DownloadListener<String> {

    private val observable = Single.just(post)
        .subscribeOn(Schedulers.io())
        .map { booru.headCustom().request(it.sampleUrl) }
        .map { it.url.toURI().toString() }
        .observeOn(AndroidSchedulers.mainThread())

    override fun onSuccess(action: (String) -> Unit) {
        disposables.add(observable.subscribe { u, _ -> action(u) })
    }

    override fun onError(action: (Throwable) -> Unit) {
        disposables.add(observable.subscribe { _, t -> if (t != null) action(t) })
    }

}