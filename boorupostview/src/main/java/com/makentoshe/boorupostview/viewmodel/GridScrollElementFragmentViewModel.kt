package com.makentoshe.boorupostview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.api.Repository
import com.makentoshe.boorulibrary.booru.entity.PostsRequest
import com.makentoshe.boorulibrary.entitiy.Post
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

/** Viewmodel component for performing a network executions */
class GridScrollElementFragmentViewModel(
    request: PostsRequest, repository: Repository<PostsRequest, List<Post>>
) : ViewModel() {

    /** Contains disposables */
    private val disposables = CompositeDisposable()

    /** Emitter for successful network request events */
    private val postsSubject = BehaviorSubject.create<List<Post>>()

    /** Observable for successful network request events */
    val postsObservable: Observable<List<Post>>
        get() = postsSubject

    /** Emitter for unsuccessful network request events */
    private val errorSubject = BehaviorSubject.create<Throwable>()

    /** Observable for unsuccessful network request events */
    val errorObservable: Observable<Throwable>
        get() = errorSubject

    init {
        // perform network request and returns a list of the posts or error
        Single.just(request).observeOn(Schedulers.io()).map(repository::get).observeOn(AndroidSchedulers.mainThread())
            .subscribe { p, e -> onPostsComplete(p, e) }.let(disposables::add)
    }

    /** Calls when posts was downloaded. Result will be posts, null or null, posts */
    private fun onPostsComplete(posts: List<Post>?, error: Throwable?) {
        if (error != null) return errorSubject.onNext(error)
        if (posts != null) return postsSubject.onNext(posts)
        errorSubject.onNext(Exception("wtf"))
    }

    override fun onCleared() = disposables.clear()

    class Factory(
        private val postsRequest: PostsRequest,
        private val repository: Repository<PostsRequest, List<Post>>
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GridScrollElementFragmentViewModel(postsRequest, repository) as T
        }
    }
}
