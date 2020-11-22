package com.makentoshe.booruchan.application.android.screen.posts.model

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.screen.posts.navigation.PostsNavigation
import com.makentoshe.booruchan.application.android.screen.posts.view.FooterViewHolder
import com.makentoshe.booruchan.application.android.screen.posts.view.PostsViewHolder
import com.makentoshe.booruchan.application.core.arena.Arena
import com.makentoshe.booruchan.core.post.Image
import com.makentoshe.booruchan.core.post.Post
import com.makentoshe.booruchan.core.post.deserialize.PostDeserialize
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayInputStream

/**
 * Performs loading previews with [coroutineScope] and [previewArena], displaying them
 * and controlling next pages loading with [postsDataSourceAfter] instance.
 */
class PostsPagedAdapter(
    /** Arena allows to request image in ByteArray using Image instance as a key */
    private val previewArena: Arena<Image, ByteArray>,
    /** For performing some network operations with the ViewHolders */
    private val coroutineScope: CoroutineScope,
    /** For observing loading results and retrying in the footer */
    private val postsDataSourceAfter: PostsDataSourceAfter,
    /** For navigating to another screens */
    private val navigation: PostsNavigation
) : PagedListAdapter<Result<PostDeserialize<Post>>, RecyclerView.ViewHolder>(PostItemCallbackDiffUtil()) {

    companion object {
        /** For the [getItemViewType] */
        private const val FOOTER_TYPE = 1
    }

    /** Disposable stores footer disposables for the disposing time when footer view will be recycled */
    private val compositeFooterDisposable = CompositeDisposable()

    @Suppress("IfThenToElvis") //Main cause is the unstable Result class
    override fun getItem(position: Int): Result<PostDeserialize<Post>> {
        val result = super.getItem(position)
        return if (result != null) result else Result.failure(Exception("${this.javaClass}: Null Result in getItem method"))
    }

    /** Current item count + footer. Footer counts only if there are some items in the adapter */
    override fun getItemCount(): Int {
        val count = super.getItemCount()
        return if (count != 0) count + 1 else count
    }

    /** Footer should be the last item with most high position */
    override fun getItemViewType(position: Int) = if (position == currentList?.size) {
        FOOTER_TYPE
    } else {
        super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            FOOTER_TYPE -> FooterViewHolder(inflater.inflate(R.layout.fragment_posts_footer, parent, false))
            else -> PostsViewHolder(inflater.inflate(R.layout.fragment_posts_item, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when (getItemViewType(position)) {
        FOOTER_TYPE -> onBindViewHolderFooter(holder as FooterViewHolder, position)
        else -> onBindViewHolderItem(holder as PostsViewHolder, position)
    }

    /** Defines [holder] state and subscribes on future changes */
    private fun onBindViewHolderFooter(holder: FooterViewHolder, position: Int) {
        val result = postsDataSourceAfter.afterSignal.value
        // define footer state based on current value
        onBindViewHolderFooter(holder, result)
        // changes footer state on actual value
        postsDataSourceAfter.afterSignal.observeOn(AndroidSchedulers.mainThread()).subscribe {
            onBindViewHolderFooter(holder, it)
        }.let(compositeFooterDisposable::add)
    }

    /** Defines current [holder] state based on [result] */
    private fun onBindViewHolderFooter(holder: FooterViewHolder, result: Result<*>?) {
        // if result is null - there is a second batch load in process
        if (result != null) {
            // result was already loaded
            if (result.isSuccess) {
                // result was success - continue loading
                holder.loading()
            } else {
                // result was failed - show retry button
                holder.error(onRetryClickListener = {
                    // show loading state and retry load
                    holder.loading()
                    postsDataSourceAfter.retryLoadAfter()
                })
            }
        } else {
            // result may not be received yet - so continue loading
            holder.loading()
        }
    }

    private fun onBindViewHolderItem(holder: PostsViewHolder, position: Int) {
        holder.preview.setImageResource(R.color.dimmed)
        holder.shimmer.showShimmer(true)

        getItem(position).fold({ success ->
            onBindViewHolderItemSuccess(holder, position, success)
        }, { throwable ->
            onBindViewHolderItemException(holder, position, throwable)
        })
    }

    private fun onBindViewHolderItemSuccess(holder: PostsViewHolder, position: Int, success: PostDeserialize<Post>) {
        val image = success.post.previewImage
        holder.itemView.setOnClickListener {
            navigation.navigateToPostScreen(success.post)
        }

        coroutineScope.launch(Dispatchers.IO) {
            val result = previewArena.suspendFetch(image)
            if (result.isSuccess) {
                val bitmap = BitmapFactory.decodeStream(ByteArrayInputStream(result.getOrNull()!!))
                coroutineScope.launch(Dispatchers.Main) {
                    holder.preview.setImageBitmap(bitmap)
                    holder.shimmer.hideShimmer()
                }
            } else {
                onBindViewHolderItemException(holder, position, result.exceptionOrNull())
            }
        }
    }

    private fun onBindViewHolderItemException(holder: PostsViewHolder, position: Int, throwable: Throwable? = null) {
        holder.text.text = throwable?.toString()
//        throwable?.printStackTrace()
    }

    /** Dispose footer disposable to avoid memory leaks */
    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is FooterViewHolder) compositeFooterDisposable.clear()
    }
}


