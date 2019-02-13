package com.makentoshe.booruchan.postpreviews.model

import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Controller
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class TagController : Controller<Tag> {

    private val observable = BehaviorSubject.create<Tag>()
    private val disposables = CompositeDisposable()

    override fun subscribe(action: (Tag) -> Unit) {
        disposables.add(observable.subscribe(action))
    }

    fun action(tag: Tag) = observable.onNext(tag)

    override fun clear() = disposables.clear()
}