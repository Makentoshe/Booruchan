package com.makentoshe.booruchan.posts.model

import io.reactivex.subjects.BehaviorSubject

class ViewPagerController(initialPage: Int) {

    private val pagerObservable = BehaviorSubject.create<Int>()

    init {
        gotoPage(initialPage)
    }

    val value: Int
        get() = pagerObservable.value!!

    fun gotoPage(pageNumber: Int) {
        if (pageNumber < 0) return
        pagerObservable.onNext(pageNumber)
    }

    fun onPageGoto(action: (Int) -> Unit) = pagerObservable.subscribe(action)
}