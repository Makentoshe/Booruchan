package com.makentoshe.booruchan.posts.model

import android.annotation.SuppressLint
import com.makentoshe.booruapi.Tag
import io.reactivex.subjects.ReplaySubject

class SelectedTagSetController(initialTagsSet: Set<Tag>) {

    private val setAddObservable = ReplaySubject.create<Tag>()
    private val setRemoveObservable = ReplaySubject.create<Tag>()

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
        if (tags.contains(tag)|| tag.name.isBlank()) return
        setAddObservable.onNext(tag)
    }

    @SuppressLint("CheckResult")
    fun subscribeOnAdd(onClick: (Tag) -> Unit) {
        setAddObservable.subscribe(onClick)
    }

    @SuppressLint("CheckResult")
    fun subscribeOnRemove(onClick: (Tag) -> Unit) {
        setRemoveObservable.subscribe(onClick)
    }

    fun removeTag(tag: Tag) = setRemoveObservable.onNext(tag)
}