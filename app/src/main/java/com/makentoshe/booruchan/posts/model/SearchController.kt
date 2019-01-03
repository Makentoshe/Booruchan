package com.makentoshe.booruchan.posts.model

import android.annotation.SuppressLint
import com.makentoshe.booruapi.Tag
import io.reactivex.subjects.BehaviorSubject
import java.io.Serializable

class SearchController : Serializable {

    private val searchObservable = BehaviorSubject.create<Set<Tag>>()

    val value: Set<Tag>?
        get() = searchObservable.value

    fun newSearch(setOfTags: Set<Tag>) {
        searchObservable.onNext(setOfTags)
    }

    @SuppressLint("CheckResult")
    fun subscribe(action: (Set<Tag>) -> Unit) {
        searchObservable.subscribe(action)
    }
}