package com.makentoshe.booruchan.postpreviews.model

import com.makentoshe.controllers.SimpleRxController
import io.reactivex.subjects.BehaviorSubject

interface ViewPagerController {
    fun nextPage()
    fun prevPage()
    fun gotoPage(page: Int)
    fun onPageChangedListener(action: (Int) -> Unit)
}

class ViewPagerRxController : SimpleRxController<Int, Int>(BehaviorSubject.create()) {

    override fun action(param: Int) {
        if (param < 0) return
        observable.onNext(param)
    }
}