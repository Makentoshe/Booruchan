package com.makentoshe.booruchan.screen.posts.model

import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Tag
import java.io.Serializable

interface SearchController : Serializable {
    fun startSearch(tags: Set<Tag>)
    fun onSearchFinished(result: (List<Post>) -> Unit)
}