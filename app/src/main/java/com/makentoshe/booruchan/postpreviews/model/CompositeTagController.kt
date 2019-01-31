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

    fun clear() = disposables.clear()
}

class CompositeTagController(
    private val addController: TagController,
    private val removeController: TagController,
    initTagSet: Set<Tag> = setOf()
) {

    val tagSet = HashSet<Tag>().apply { addAll(initTagSet) }

    init {
        for (tag in initTagSet) addTag(tag)
    }

    fun subscribeOnRemove(action: (Tag) -> Unit) =
        removeController.subscribe(action)

    fun subscribeOnAdd(action: (Tag) -> Unit) =
        addController.subscribe(action)

    fun addTag(tag: Tag) {
        addController.action(tag)
        tagSet.add(tag)
    }

    fun removeTag(tag: Tag) {
        removeController.action(tag)
        tagSet.remove(tag)
    }

    fun clear() {
        removeController.clear()
        addController.clear()
    }

}