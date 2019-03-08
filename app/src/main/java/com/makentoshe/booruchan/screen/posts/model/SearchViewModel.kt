package com.makentoshe.booruchan.screen.posts.model

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Tag
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

class SearchViewModel : ViewModel(), SearchController {

    private val tagsObservable = BehaviorSubject.create<Set<Tag>>()
    private val postsObservable = BehaviorSubject.create<List<Post>>()
    private val disposables = CompositeDisposable()
    private lateinit var disposable: Disposable

    override fun startSearch(tags: Set<Tag>) {
        tagsObservable.onNext(tags)
        performLoad()
    }

    private fun performLoad() {
        lateinit var disposable: Disposable
        disposable = tagsObservable.subscribe {
            //perform loading
            //...
            //return List<Post>
            postsObservable.onNext(listOf())
            disposable.dispose()
        }
    }

    override fun onSearchFinished(result: (List<Post>) -> Unit) {
        disposables.add(postsObservable.subscribe(result))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    class Factory : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = SearchViewModel()

            return viewModel as T
        }
    }

    companion object {
        fun create(fragment: Fragment): SearchViewModel {
            val factory = SearchViewModel.Factory()
            return ViewModelProviders.of(
                fragment,
                factory
            )[SearchViewModel::class.java]
        }
    }
}