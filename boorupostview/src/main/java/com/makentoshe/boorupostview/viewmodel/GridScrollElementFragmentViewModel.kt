package com.makentoshe.boorupostview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.api.Repository
import com.makentoshe.boorulibrary.booru.entity.PostsRequest
import com.makentoshe.boorulibrary.entitiy.Post
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

    /** Successful network request */
    val postsObservable = BehaviorSubject.create<List<Post>>()

    /** Unsuccessful network request */
    val errorObservable = BehaviorSubject.create<Throwable>()

    init {
        // perform network request and returns a list of the posts or error
        Single.just(request).observeOn(Schedulers.io()).map(repository::get).observeOn(AndroidSchedulers.mainThread())
            .subscribe { p, e -> onPostsComplete(p, e) }.let(disposables::add)
    }

    private fun onPostsComplete(posts: List<Post>?, error: Throwable?) {
        if (error != null) return errorObservable.onNext(error)
        if (posts != null) return postsObservable.onNext(posts)
        errorObservable.onNext(Exception("wtf"))
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
