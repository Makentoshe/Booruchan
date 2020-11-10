package com.makentoshe.booruchan.application.android.screen.posts.model

import io.reactivex.rxjava3.subjects.BehaviorSubject

/** Interface for controlling last batch loading */
interface PostsDataSourceAfter {

    /** Signal indicates last batch load was finished */
    val afterSignal: BehaviorSubject<Result<*>>

    /** Method retries last batch load */
    fun retryLoadAfter()
}