package com.makentoshe.booruchan.posts.model

import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Controller
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

class SearchController: Controller<Set<Tag>>, Serializable {

    private val searchObservable = BehaviorSubject.create<Set<Tag>>()
    private val disposables = CompositeDisposable()

    val tags: Set<Tag>?
        get() = searchObservable.value

    fun newSearch(setOfTags: Set<Tag>) = searchObservable.onNext(setOfTags)

    override fun subscribe(action: (Set<Tag>) -> Unit) {
        disposables.add(searchObservable.subscribe(action))
    }

    fun clear() = disposables.clear()

    fun update(tags: Set<Tag>) {
        clear()
        if (searchObservable.hasValue()) {
            //search already was started
            newSearch(searchObservable.value!!)
        } else {
            //search was not started yet
            //start new search with given tags
            newSearch(tags)
        }
    }
}