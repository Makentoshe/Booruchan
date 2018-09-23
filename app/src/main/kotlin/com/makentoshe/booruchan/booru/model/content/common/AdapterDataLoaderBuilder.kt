package com.makentoshe.booruchan.booru.model.content.common

import com.makentoshe.booruchan.common.api.Boor

class AdapterDataLoaderBuilder(private val downloader: Downloader, private val booru: Boor) {

    fun build(searchTerm: String): AdapterDataLoader {
        return AdapterDataLoader(searchTerm, downloader, booru)
    }

}