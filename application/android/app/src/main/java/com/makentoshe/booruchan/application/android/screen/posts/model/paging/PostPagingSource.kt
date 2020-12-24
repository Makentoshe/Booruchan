package com.makentoshe.booruchan.application.android.screen.posts.model.paging

import android.util.Log
import androidx.paging.PagingSource
import com.makentoshe.booruchan.application.android.BuildConfig
import com.makentoshe.booruchan.application.core.arena.Arena
import com.makentoshe.booruchan.core.network.filter.TagsFilterEntry
import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.deserialize.PostsDeserialize
import com.makentoshe.booruchan.core.post.network.PostsFilter

class PostPagingSource(
    private val arena: Arena<PostsFilter, PostsDeserialize<Post>>,
    private val filterBuilder: PostsFilter.Builder,
    private val tagsFilter: TagsFilterEntry
) : PagingSource<Int, PostEntity>() {

    companion object {
        fun capture(level: Int, message: () -> String) {
            if (!BuildConfig.DEBUG) return
            Log.println(level, "PostPagingSource", message())
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostEntity> {
        val page = params.key ?: 0
        val nextPage = if (params.key == null) 3 else page + 1
        capture(Log.INFO) { "page=${page}, size=${params.loadSize}, nextPage=$nextPage" }

        val countFilter = filterBuilder.count.build(params.loadSize)
        val pageFilter = filterBuilder.page.build(page)
        val filter = filterBuilder.build(countFilter, pageFilter, tagsFilter)

        arena.suspendFetch(filter).fold({
            val deserializes = it.deserializes.map {
                it.fold({ PostEntity.Success(it.post) }, { PostEntity.Failure(it) })
            }
            return LoadResult.Page(deserializes, null, nextPage)
        }, {
            return LoadResult.Error(it)
        })
    }
}