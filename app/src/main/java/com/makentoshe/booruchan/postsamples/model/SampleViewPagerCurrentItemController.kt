package com.makentoshe.booruchan.postsamples.model

import com.makentoshe.booruchan.Controller
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class SampleViewPagerCurrentItemController(private val startPosition: Int) : Controller<Int> {

    private val observable = BehaviorSubject.create<Int>().apply { onNext(startPosition) }
    private val disposables = CompositeDisposable()

    fun getPage() = observable.value ?: startPosition

    override fun subscribe(action: (Int) -> Unit) {
        disposables.add(observable.subscribe(action))
    }

    fun action(page: Int) = observable.onNext(page)

    override fun clear() = disposables.clear()

}