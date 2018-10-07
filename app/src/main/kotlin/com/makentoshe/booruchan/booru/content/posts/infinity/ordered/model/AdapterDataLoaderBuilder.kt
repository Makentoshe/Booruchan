package com.makentoshe.booruchan.booru.content.posts.infinity.ordered.model

import com.makentoshe.booruchan.booru.content.model.Downloader
import com.makentoshe.booruchan.common.api.Boor

class AdapterDataLoaderBuilder(private val downloader: Downloader, private val booru: Boor) {

    fun build(searchTerm: String): AdapterDataLoader {
        return AdapterDataLoader(searchTerm, downloader, booru)
    }

}