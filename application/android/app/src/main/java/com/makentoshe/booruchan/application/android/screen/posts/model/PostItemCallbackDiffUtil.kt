package com.makentoshe.booruchan.application.android.screen.posts.model

import androidx.recyclerview.widget.DiffUtil
import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.deserialize.PostDeserialize

class PostItemCallbackDiffUtil : DiffUtil.ItemCallback<Result<PostDeserialize<Post>>>() {
    override fun areItemsTheSame(oldItem: Result<PostDeserialize<Post>>, newItem: Result<PostDeserialize<Post>>): Boolean {
        val old = oldItem.getOrNull()?.post
        val new = newItem.getOrNull()?.post
        if (old == null || new == null) return false
        return old.postId == new.postId
    }

    override fun areContentsTheSame(oldItem: Result<PostDeserialize<Post>>, newItem: Result<PostDeserialize<Post>>): Boolean {
        val old = oldItem.getOrNull()
        val new = newItem.getOrNull()
        return old?.post?.equals(new?.post) ?: false
    }
}