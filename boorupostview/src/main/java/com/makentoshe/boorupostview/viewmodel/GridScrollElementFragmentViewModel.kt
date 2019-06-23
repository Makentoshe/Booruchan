package com.makentoshe.boorupostview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.api.DefaultPostsRequest
import com.makentoshe.api.Repository
import com.makentoshe.boorulibrary.booru.entity.PostsRequest
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorupostview.model.GridElementControllerHolder
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

/**
 *  Viewmodel component performs a network executions
 */
class GridScrollElementFragmentViewModel(
    private val request: PostsRequest,
    private val repository: Repository<PostsRequest, List<Post>>,
    val controllerHolder: GridElementControllerHolder
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
        request(request)
    }

    /** Repeat network request for current items count */
    fun repeat(itemsCount: Int) {
        request(DefaultPostsRequest(itemsCount, request.tags, request.page))
    }

    /** Perform network request and returns a list of the posts or error */
    private fun request(request: PostsRequest) {
        Single.just(request).observeOn(Schedulers.io()).map(repository::get).observeOn(AndroidSchedulers.mainThread())
            .subscribe { p, e -> onPostsComplete(p, e) }.let(disposables::add)
    }

    /** Calls when posts was downloaded. Result will be posts, null or null, posts */
    private fun onPostsComplete(posts: List<Post>?, error: Throwable?) {
        if (error != null) return errorSubject.onNext(error)
        if (posts != null) return postsSubject.onNext(posts)
        errorSubject.onNext(Exception("wtf"))
    }

    /** Release disposables on cleared lifecycle event */
    override fun onCleared() {
        // remove unused controllers from the memory
        postsSubject.value.orEmpty().forEach(controllerHolder::remove)
        disposables.clear()
    }

    class Factory(
        private val postsRequest: PostsRequest,
        private val repository: Repository<PostsRequest, List<Post>>,
        private val controllerHolder: GridElementControllerHolder?
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GridScrollElementFragmentViewModel(postsRequest, repository, controllerHolder!!) as T
        }
    }
}
