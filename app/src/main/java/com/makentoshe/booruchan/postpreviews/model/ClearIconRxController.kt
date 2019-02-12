package com.makentoshe.booruchan.postpreviews.model

import com.makentoshe.controllers.SimpleRxController
import io.reactivex.subjects.BehaviorSubject

class ClearIconRxController : SimpleRxController<Unit, Unit>(BehaviorSubject.create()) {
    override fun action(param: Unit) = observable.onNext(param)
}