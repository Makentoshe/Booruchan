package com.makentoshe.booruchan.feature

data class BooruSearchSettings(
    val requestedPostsPerPageCount: Int,
) {
    init {
        require(requestedPostsPerPageCount > 0) { "requestedPostsPerPage must be > 0" }
    }
}