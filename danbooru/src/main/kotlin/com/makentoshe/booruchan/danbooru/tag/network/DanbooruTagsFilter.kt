package com.makentoshe.booruchan.danbooru.tag.network

import com.makentoshe.booruchan.core.tag.network.TagsFilter

class DanbooruTagsFilter(params: Map<String, Any>) : TagsFilter(params) {

    constructor(count: Int?) : this(buildMap(count))

    companion object {
        private const val COUNT = "limit"

        private fun buildMap(count: Int?): Map<String, Any> {
            val params = HashMap<String, Any>()
            if (count != null) params[COUNT] = count
            return params
        }
    }
}