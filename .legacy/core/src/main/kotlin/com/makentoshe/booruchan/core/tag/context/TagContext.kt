package com.makentoshe.booruchan.core.tag.context

import com.makentoshe.booruchan.core.context.BooruEntityContext
import com.makentoshe.booruchan.core.tag.deserialize.TagDeserialize
import com.makentoshe.booruchan.core.tag.network.TagFilter
import com.makentoshe.booruchan.core.tag.network.TagRequest

abstract class TagContext<Request: TagRequest, Filter: TagFilter>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<TagDeserialize<*>>
): BooruEntityContext<Request, TagDeserialize<*>>(network, deserialize) {

    abstract fun buildRequest(filter: Filter): Request
}
