package tag.context

import context.BooruEntityContext
import tag.deserialize.TagDeserialize
import tag.network.TagRequest

open class TagContext<Request: TagRequest>(
    network: suspend (Request) -> Result<String>,
    deserialize: (String) -> Result<TagDeserialize<*>>
): BooruEntityContext<Request, TagDeserialize<*>>(network, deserialize)
