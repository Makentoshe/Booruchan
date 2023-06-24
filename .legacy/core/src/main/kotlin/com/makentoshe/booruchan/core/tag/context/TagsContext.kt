package com.makentoshe.booruchan.core.tag.context

import com.makentoshe.booruchan.core.context.BooruEntityContext
import com.makentoshe.booruchan.core.tag.deserialize.TagsDeserialize
import com.makentoshe.booruchan.core.tag.network.TagsFilter
import com.makentoshe.booruchan.core.tag.network.TagsRequest

abstract class TagsContext<Request : TagsRequest, Filter : TagsFilter>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<TagsDeserialize<*>>
) : BooruEntityContext<Request, TagsDeserialize<*>>(network, deserialize) {

    abstract fun buildRequest(filter: Filter): Request

    abstract fun filterBuilder(): TagsFilter.Builder
}