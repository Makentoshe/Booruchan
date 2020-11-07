package com.makentoshe.booruchan.application.core.arena

import com.makentoshe.booruchan.application.core.PostsNetworkManager
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.context.PostsContext
import com.makentoshe.booruchan.core.post.deserialize.PostsDeserialize
import com.makentoshe.booruchan.core.post.network.PostsFilter
import com.makentoshe.booruchan.core.post.network.PostsRequest
import io.ktor.client.*

/** Performs posts networking and caching */
class PostsArena(
    private val postsContext: PostsContext<PostsRequest, PostsFilter>,
    postsArenaStorage: ArenaStorage<PostsFilter, PostsDeserialize<Post>>
) : SourceFirstArena<PostsFilter, PostsDeserialize<Post>>(postsArenaStorage) {

    override suspend fun internalSuspendFetch(filter: PostsFilter): Result<PostsDeserialize<Post>> {
        return postsContext.get(postsContext.buildRequest(filter))
    }

    /** Performs easily [PostsArena] object constructing */
    class Builder(private val booruContext: BooruContext) {

        /** Mandatory param for caching and should be always defined */
        var arenaStorage: ArenaStorage<PostsFilter, PostsDeserialize<Post>>? = null

        /** Build [PostsArena] with custom networking */
        fun build(network: (suspend (PostsRequest) -> Result<String>)): PostsArena {
            val arenaStorage = this.arenaStorage ?: throw IllegalStateException("Arena storage should be defined")
            val postsContext = booruContext.posts(network) as PostsContext<PostsRequest, PostsFilter>
            return PostsArena(postsContext, arenaStorage)
        }

        /** Build [PostsArena] with default networking */
        fun build(client: HttpClient): PostsArena {
            val arenaStorage = this.arenaStorage ?: throw IllegalStateException("Arena storage should be defined")
            val networkManager = PostsNetworkManager(client)
            val postsContext = booruContext.posts(networkManager::getPosts) as PostsContext<PostsRequest, PostsFilter>
            return PostsArena(postsContext, arenaStorage)
        }
    }
}