package com.makentoshe.booruchan.screen.posts.model

import com.makentoshe.booruapi.Tag
import java.io.Serializable

interface SearchController : Serializable {
    fun startSearch(tags: Set<Tag>)
    fun onSearchStarted(result: (Set<Tag>) -> Unit)
}