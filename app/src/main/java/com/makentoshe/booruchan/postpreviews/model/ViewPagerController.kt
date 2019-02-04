package com.makentoshe.booruchan.postpreviews.model

import com.makentoshe.booruchan.Controller
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class ViewPagerController(initialPage: Int) : Controller<Int> {

    private val observable = BehaviorSubject.create<Int>()
    private val disposables = CompositeDisposable()

    init {
        action(initialPage)
    }

    override fun subscribe(action: (Int) -> Unit) {
        disposables.add(observable.subscribe(action))
    }

    fun action(page: Int) {
        if (page < 0) return
        observable.onNext(page)
    }

    fun nextPage() = action(observable.value!! + 1)

    fun prevPage() = action(observable.value!! - 1)

    override fun clear() = disposables.clear()
}