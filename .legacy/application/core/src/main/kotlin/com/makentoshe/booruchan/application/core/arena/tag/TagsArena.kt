package com.makentoshe.booruchan.application.core.arena.tag

import com.makentoshe.booruchan.application.core.arena.ArenaStorage
import com.makentoshe.booruchan.application.core.arena.StorageFirstArena
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.tag.Tag
import com.makentoshe.booruchan.core.tag.context.TagsContext
import com.makentoshe.booruchan.core.tag.deserialize.TagsDeserialize
import com.makentoshe.booruchan.core.tag.network.TagsFilter
import com.makentoshe.booruchan.core.tag.network.TagsRequest
import io.ktor.client.*

class TagsArena(
    private val tagsContext: TagsContext<TagsRequest, TagsFilter>,
    tagsStorage: ArenaStorage<TagsFilter, TagsDeserialize<Tag>>
) : StorageFirstArena<TagsFilter, TagsDeserialize<Tag>>(tagsStorage) {

    override suspend fun internalSuspendFetch(key: TagsFilter): Result<TagsDeserialize<Tag>> {
        return tagsContext.get(tagsContext.buildRequest(key))
    }

    /** Performs easily [TagsArena] object constructing */
    class Builder(private val booruContext: BooruContext) {

        /** Mandatory param for caching and should be always defined */
        var arenaStorage: ArenaStorage<TagsFilter, TagsDeserialize<Tag>>? = null

        /** Build [TagsArena] with custom networking */
        fun build(network: (suspend (TagsRequest) -> Result<String>)): TagsArena {
            val arenaStorage = this.arenaStorage ?: throw IllegalStateException("Arena storage should be defined")
            val postsContext = booruContext.tags(network) as TagsContext<TagsRequest, TagsFilter>
            return TagsArena(postsContext, arenaStorage)
        }

        /** Build [TagsArena] with default networking */
        fun build(client: HttpClient): TagsArena {
            val arenaStorage = this.arenaStorage ?: throw IllegalStateException("Arena storage should be defined")
            val networkManager = com.makentoshe.booruchan.application.core.network.TagsNetworkManager(client)
            val postsContext = booruContext.tags(networkManager::getTags) as TagsContext<TagsRequest, TagsFilter>
            return TagsArena(postsContext, arenaStorage)
        }
    }
}