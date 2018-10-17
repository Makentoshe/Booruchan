package com.makentoshe.booruchan.booru.content.posts.infinity.ordered.model

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.content.posts.ViewModel
import com.makentoshe.booruchan.booru.content.posts.infinity.ordered.view.ViewHolderUI
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.experimental.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.lang.Exception
import kotlin.collections.ArrayList

class RecycleViewAdapter(private val dataLoader: AdapterDataLoader,
                         private val viewModel: ViewModel)
    : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ViewHolderUI()
                .createView(AnkoContext.create(parent.context!!, parent)))
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = runBlocking {
        holder.clear()
        try {
            dataLoader.getPostsData2(position) { posts ->
                for (postIndex in 0 until posts.count() step 1) {
                    dataLoader.getPostPreview(posts.getPost(postIndex)) { bitmap ->
                        setBitmapToImageView(bitmap, holder.getPostPreviewView(postIndex))
                        holder.onClick(position, viewModel)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            callMessageWhichDisplayingAnError(holder.itemView.context)
        }
    }

    private fun callMessageWhichDisplayingAnError(context: Context) {
        (context as FragmentActivity).runOnUiThread {
            Toasty.error(context, context.getString(R.string.network_error)).show()
        }
    }

    private fun setBitmapToImageView(bitmap: Bitmap?, imageView: ImageView) {
        (imageView.context as FragmentActivity).runOnUiThread {
            imageView.setImageBitmap(bitmap)
        }
    }

    fun clear() {
        dataLoader.clearScheduler()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val postsMainView = ArrayList<CardView>()
        private val postsPreviewView = ArrayList<ImageView>()

        init {
            view.childrenSequence().forEach { child ->
                postsMainView.add(child as CardView)
                postsPreviewView.add(child.findViewById(R.id.booru_content_gallery_post_preview))
            }
        }

        fun getPostMainView(index: Int): CardView {
            return postsMainView[index]
        }

        fun getPostPreviewView(index: Int): ImageView {
            return postsPreviewView[index]
        }

        fun onClick(position: Int, viewModel: ViewModel) {
            postsMainView.forEachIndexed { index, cardView ->
                cardView.onClick {
                    viewModel.startSampleActivity(itemView.context as FragmentActivity, position * 3 + index)
                }
            }

        }

        fun clear() {
            postsPreviewView.forEach { it.setImageDrawable(null) }
            postsMainView.forEach { it.setOnClickListener(null) }
        }

        companion object {
            const val SIDE = 110
        }

    }

}
