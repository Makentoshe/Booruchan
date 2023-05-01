package com.makentoshe.booruchan.feature

data class BooruSearchSettings(
    val requestedPostsPerPageCount: Int = 30,
    val initialPageNumber: Int = 0,
) {
    init {
        require(requestedPostsPerPageCount > 0) { "requestedPostsPerPage must be > 0" }
        require(initialPageNumber >= 0) { "requestedPostsPerPage must be >= 0" }
    }
}