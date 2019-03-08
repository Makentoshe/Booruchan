package com.makentoshe.booruchan.screen.posts.model

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.booruapi.Tag
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class SearchViewModel : ViewModel(), SearchController {

    private val tagsObservable = BehaviorSubject.create<Set<Tag>>()
    private val disposables = CompositeDisposable()

    override fun startSearch(tags: Set<Tag>) {
        tagsObservable.onNext(tags)
    }

    override fun onSearchStarted(result: (Set<Tag>) -> Unit) {
        disposables.add(tagsObservable.subscribe(result))
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    class Factory : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = SearchViewModel()
            //todo put here default tags from the settings
            viewModel.startSearch(setOf())

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