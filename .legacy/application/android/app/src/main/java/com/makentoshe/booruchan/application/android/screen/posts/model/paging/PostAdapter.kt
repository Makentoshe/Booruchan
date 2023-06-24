package com.makentoshe.booruchan.application.android.screen.posts.model.paging

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.screen.posts.navigation.PostsNavigation
import com.makentoshe.booruchan.application.android.screen.posts.view.PostViewHolder
import com.makentoshe.booruchan.application.core.arena.Arena
import com.makentoshe.booruchan.core.post.Content
import com.makentoshe.booruchan.core.post.Type
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream

class PostAdapter(
    /** Arena allows to request image in ByteArray using Content instance as a key */
    private val previewArena: Arena<Content, ByteArray>,
    /** For performing some network operations with the ViewHolders */
    private val coroutineScope: CoroutineScope,
    /** For navigating to another screens */
    private val navigation: PostsNavigation
) : PagingDataAdapter<PostEntity, PostViewHolder>(
    PostEntityDiffUtilItemCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PostViewHolder(inflater.inflate(R.layout.fragment_posts_item, parent, false))
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.preview.setImageResource(R.color.dimmed)
        holder.shimmer.showShimmer(true)

        when (val item = getItem(position)) {
            is PostEntity.Success -> onBindViewHolderSuccess(holder, item)
            is PostEntity.Failure -> onBindViewHolderFailure(holder, item.exception)
        }
    }

    private fun onBindViewHolderSuccess(holder: PostViewHolder, item: PostEntity.Success) {
        holder.text.text = null

        holder.itemView.setOnClickListener {
            navigation.navigateToPostScreen(item.post)
        }

        val full = item.post.fullContent
        holder.itemView.backgroundTintList = resolveContentColor(full, holder.itemView.context)

        val preview = item.post.previewContent
        coroutineScope.launch(Dispatchers.IO) {
            previewArena.suspendFetch(preview).fold({
                onBindViewHolderPreview(holder, it)
            }, {
                onBindViewHolderFailure(holder, it)
            })
        }
    }

    private fun onBindViewHolderPreview(holder: PostViewHolder, preview: ByteArray) {
        val bitmap = BitmapFactory.decodeStream(ByteArrayInputStream(preview))
        Handler(Looper.getMainLooper()).post {
            holder.preview.setImageBitmap(bitmap)
            holder.shimmer.hideShimmer()
        }
    }

    private fun onBindViewHolderFailure(holder: PostViewHolder, throwable: Throwable?) {
        Handler(Looper.getMainLooper()).post { holder.shimmer.hideShimmer() }
    }

    private fun resolveContentColor(image: Content, context: Context) = when (image.type) {
        Type.VIDEO -> ColorStateList.valueOf(ContextCompat.getColor(context, R.color.content_video))
        Type.ANIMATION -> ColorStateList.valueOf(ContextCompat.getColor(context, R.color.content_animation))
        Type.IMAGE -> ColorStateList.valueOf(ContextCompat.getColor(context, R.color.content_image))
    }
}

