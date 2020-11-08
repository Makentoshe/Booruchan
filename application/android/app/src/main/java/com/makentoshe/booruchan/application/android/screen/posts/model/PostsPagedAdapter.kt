package com.makentoshe.booruchan.application.android.screen.posts.model

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.screen.posts.view.PostsViewHolder
import com.makentoshe.booruchan.application.core.arena.Arena
import com.makentoshe.booruchan.core.post.Image
import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.deserialize.PostDeserialize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream

class PostsPagedAdapter(
    private val previewArena: Arena<Image, ByteArray>,
    private val coroutineScope: CoroutineScope
) : PagedListAdapter<Result<PostDeserialize<Post>>, PostsViewHolder>(PostItemCallbackDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PostsViewHolder(inflater.inflate(R.layout.fragment_posts_item, parent, false))
    }

    @Suppress("IfThenToElvis") //Main cause is the unstable Result class
    override fun getItem(position: Int): Result<PostDeserialize<Post>> {
        val result = super.getItem(position)
        return if (result != null) result else Result.failure(Exception("${this.javaClass}: Null Result in getItem method"))
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.preview.setImageResource(R.color.dimmed)
        holder.shimmer.showShimmer(true)

        getItem(position).fold({ success ->
            onBindViewHolderSuccess(holder, position, success)
        }, { throwable ->
            onBindViewHolderException(holder, position, throwable)
        })
    }

    private fun onBindViewHolderSuccess(holder: PostsViewHolder, position: Int, success: PostDeserialize<Post>) {
        val image = success.post.previewImage
        coroutineScope.launch(Dispatchers.IO) {
            Log.d(javaClass.simpleName, "$position Loading preview: ${image.url}")
            val result = previewArena.suspendFetch(image)
            Log.d(javaClass.simpleName, "$position Finish preview: $result")
            if (result.isSuccess) {
                val bitmap = BitmapFactory.decodeStream(ByteArrayInputStream(result.getOrNull()!!))
                coroutineScope.launch(Dispatchers.Main) {
                    holder.preview.setImageBitmap(bitmap)
                    holder.shimmer.hideShimmer()
                }
            } else {
                onBindViewHolderException(holder, position, result.exceptionOrNull())
            }
        }
    }

    private fun onBindViewHolderException(holder: PostsViewHolder, position: Int, throwable: Throwable? = null) {
        holder.text.text = throwable?.toString()
        throwable?.printStackTrace()
    }

}


