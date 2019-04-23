package com.makentoshe.booruchan.screen.posts.controller

import com.makentoshe.booruchan.api.component.tag.Tag
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

/**
 * Class performing tags providing using reactive.
 *
 * @param compositeDisposable is a container where all disposables will be stored.
 */
class SearchRxProvider(private val compositeDisposable: CompositeDisposable) :
    SearchController {

    /**
     * Observable
     */
    private val subject = BehaviorSubject.create<Set<Tag>>()

    /**
     * Receiver
     */
    override fun onSearchStarted(action: (Set<Tag>) -> Unit) {
        compositeDisposable.add(subject.subscribe(action)!!)
    }

    /**
     * Sender
     */
    override fun startSearch(tags: Set<Tag>) = subject.onNext(tags)
}