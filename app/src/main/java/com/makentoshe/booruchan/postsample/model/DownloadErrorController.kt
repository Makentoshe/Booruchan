package com.makentoshe.booruchan.postsample.model

import com.makentoshe.controllers.SimpleRxController
import io.reactivex.subjects.BehaviorSubject

class DownloadErrorController : SimpleRxController<Exception, Exception>(BehaviorSubject.create()) {

    override fun action(param: Exception) = observable.onNext(param)
}