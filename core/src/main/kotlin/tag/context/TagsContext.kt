package tag.context

import context.BooruEntityContext
import tag.deserialize.TagsDeserialize
import tag.network.TagsRequest

open class TagsContext<in Request: TagsRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<TagsDeserialize<*>>
) : BooruEntityContext<Request, TagsDeserialize<*>>(network, deserialize)