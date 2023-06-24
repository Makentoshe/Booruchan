package com.makentoshe.booruchan.application.android.screen.posts.model.paging

import androidx.recyclerview.widget.DiffUtil

class PostEntityDiffUtilItemCallback: DiffUtil.ItemCallback<PostEntity>() {
    override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
        return if (oldItem is PostEntity.Success && newItem is PostEntity.Success) {
            oldItem.post.postId == newItem.post.postId
        } else if (oldItem is PostEntity.Failure && newItem is PostEntity.Failure) {
            oldItem == newItem
        } else false
    }

    override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean {
        return if (oldItem is PostEntity.Success && newItem is PostEntity.Success) {
            oldItem == newItem
        } else if (oldItem is PostEntity.Failure && newItem is PostEntity.Failure) {
            oldItem == newItem
        } else false
    }
}