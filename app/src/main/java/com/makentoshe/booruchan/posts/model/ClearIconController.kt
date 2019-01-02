package com.makentoshe.booruchan.posts.model

import io.reactivex.subjects.BehaviorSubject

class ClearIconController {
    private val clearIconObservable = BehaviorSubject.create<Unit>()

    fun addOnClickListener(onClick: (Unit) -> Unit) {
        clearIconObservable.subscribe(onClick)
    }

    fun click() = clearIconObservable.onNext(Unit)
}