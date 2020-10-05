package tag.context

import context.BooruEntityContext
import tag.deserialize.TagsDeserialize
import tag.network.TagsFilter
import tag.network.TagsRequest

abstract class TagsContext<Request : TagsRequest, Filter : TagsFilter>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<TagsDeserialize<*>>
) : BooruEntityContext<Request, TagsDeserialize<*>>(network, deserialize) {

    abstract fun buildRequest(filter: Filter): Request
}