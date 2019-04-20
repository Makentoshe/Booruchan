package com.makentoshe.booruchan.screen.posts.viewmodel

import com.makentoshe.booruchan.api.component.tag.Tag
import io.reactivex.disposables.Disposable

interface SearchState {

    fun clearDisposables()

    fun startSearch(tags: Set<Tag>)

    fun onSearchStarted(action: (Set<Tag>) -> Unit): Disposable
}