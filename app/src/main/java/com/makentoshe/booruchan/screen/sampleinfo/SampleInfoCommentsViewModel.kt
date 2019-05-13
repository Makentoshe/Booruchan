package com.makentoshe.booruchan.screen.sampleinfo

import androidx.lifecycle.ViewModel
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.comment.Comment
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.repository.stream.StreamRepositoryFactory
import com.makentoshe.booruchan.common.download.DownloadListener
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class SampleInfoCommentsViewModel(
    private val booru: Booru,
    private val post: Post,
    private val disposables: CompositeDisposable
) : ViewModel(), DownloadListener<List<Comment>>, KoinComponent {

    private val repositoryFactory by inject<StreamRepositoryFactory> { parametersOf(booru, null) }

    private val observable = Single.just(post)
        .subscribeOn(Schedulers.io())
        .map { repositoryFactory.buildCommentaryRepository().get(it) }
        .observeOn(AndroidSchedulers.mainThread())

    override fun onSuccess(action: (List<Comment>) -> Unit) {
        val disposable = observable.subscribe { comments, _ ->
            if (comments != null) action(comments)
        }
        disposables.add(disposable)
    }

    override fun onError(action: (Throwable) -> Unit) {
        val disposable = observable.subscribe { _, throwable ->
            if (throwable != null) return@subscribe action(throwable)
        }
        disposables.add(disposable)
    }
}