package com.makentoshe.booruchan.application.android.screen.posts.viewmodel

import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import com.makentoshe.booruchan.application.android.screen.posts.model.PostItemCallbackDiffUtil
import com.makentoshe.booruchan.application.android.screen.posts.view.PostsViewHolder
import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.deserialize.PostDeserialize

class PostsPagedAdapter : PagedListAdapter<Result<PostDeserialize<Post>>, PostsViewHolder>(PostItemCallbackDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        return PostsViewHolder(TextView(parent.context))
    }

    @Suppress("IfThenToElvis") //Main cause is the unstable Result class
    override fun getItem(position: Int): Result<PostDeserialize<Post>> {
        val result = super.getItem(position)
        return if (result != null) result else Result.failure(Exception("${this.javaClass}: Null Result in getItem method"))
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        getItem(position).fold({ success ->
            onBindViewHolderSuccess(holder, position, success)
        }, { throwable ->
            onBindViewHolderException(holder, position, throwable)
        })
    }

    private fun onBindViewHolderSuccess(holder: PostsViewHolder, position: Int, success: PostDeserialize<Post>) {
        (holder.itemView as TextView).text = success.post.postId.toString()
    }

    private fun onBindViewHolderException(holder: PostsViewHolder, position: Int, throwable: Throwable? = null) {
        (holder.itemView as TextView).text = throwable?.toString()

        println(throwable)
        println(throwable?.cause)
    }

}

