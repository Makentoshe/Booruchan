package com.makentoshe.booruchan.postsample.model

import com.makentoshe.controllers.SimpleRxController
import io.reactivex.subjects.BehaviorSubject

class DownloadingCompleteRxController : SimpleRxController<Unit, Unit>(BehaviorSubject.create()) {
    override fun action(param: Unit) = observable.onNext(param)
}