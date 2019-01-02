package com.makentoshe.booruchan.posts.model

import com.makentoshe.booruapi.Tag
import io.reactivex.subjects.ReplaySubject

class SelectedTagSetController(initialTagsSet: Set<Tag>) {

    private val setObservable = ReplaySubject.create<Tag>()
    init {
        initialTagsSet.forEach {
            setObservable.onNext(it)
        }
    }

    val tags: Set<Tag>
        get() {
            val result = mutableSetOf<Tag>()
            setObservable.values.forEach { result.add(it as Tag) }
            return result
        }

    fun addTag(tag: Tag) = setObservable.onNext(tag)

    fun subscribe(onClick: (Tag) -> Unit) = setObservable.subscribe(onClick)
}