package tag.context

import context.BooruEntityContext
import tag.deserialize.TagDeserialize
import tag.network.TagFilter
import tag.network.TagRequest

abstract class TagContext<Request: TagRequest, Filter: TagFilter>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<TagDeserialize<*>>
): BooruEntityContext<Request, TagDeserialize<*>>(network, deserialize) {

    abstract fun buildRequest(filter: Filter): Request
}
