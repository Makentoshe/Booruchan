package com.makentoshe.booruchan.screen.posts.controller

import com.makentoshe.booruchan.api.component.tag.Tag
import io.reactivex.subjects.BehaviorSubject

class SearchController {

    private val subject =
        BehaviorSubject.create<Set<Tag>>()

    fun onSearchStart(action: (Set<Tag>) -> Unit) = subject.subscribe(action)!!

    fun startSearch(tags: Set<Tag>) = subject.onNext(tags)
}