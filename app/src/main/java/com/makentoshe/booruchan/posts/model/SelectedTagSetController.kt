package com.makentoshe.booruchan.posts.model

import com.makentoshe.booruapi.Tag
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.ReplaySubject

class SelectedTagSetController(initialTagsSet: Set<Tag>) {

    private val setAddObservable = ReplaySubject.create<Tag>()
    private val setRemoveObservable = ReplaySubject.create<Tag>()

    private val disposables = CompositeDisposable()

    init {
        initialTagsSet.forEach {
            setAddObservable.onNext(it)
        }
    }

    val tags: Set<Tag>
        get() {
            val result = mutableSetOf<Tag>()
            setAddObservable.values.forEach { result.add(it as Tag) }
            setRemoveObservable.values.forEach { result.remove(it as Tag) }
            return result
        }

    fun addTag(tag: Tag) {
        if (tags.contains(tag) || tag.name.isBlank()) return
        setAddObservable.onNext(tag)
    }

    fun subscribeOnAdd(onClick: (Tag) -> Unit) {
        disposables.add(setAddObservable.subscribe(onClick))
    }

    fun subscribeOnRemove(onClick: (Tag) -> Unit) {
        disposables.add(setRemoveObservable.subscribe(onClick))
    }

    fun removeTag(tag: Tag) = setRemoveObservable.onNext(tag)

    fun clear() {
        disposables.clear()
    }
}