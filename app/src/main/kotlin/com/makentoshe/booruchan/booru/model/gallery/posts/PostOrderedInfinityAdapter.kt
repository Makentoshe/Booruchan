package com.makentoshe.booruchan.booru.model.gallery.posts

import android.graphics.Bitmap
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.model.gallery.common.AdapterDataLoader
import com.makentoshe.booruchan.booru.view.posts.PostOrderedInfinityAdapterViewHolderUI
import kotlinx.coroutines.experimental.*
import org.jetbrains.anko.*
import kotlin.collections.ArrayList

class PostOrderedInfinityAdapter(private val dataLoader: AdapterDataLoader)
    : RecyclerView.Adapter<PostOrderedInfinityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                PostOrderedInfinityAdapterViewHolderUI()
                        .createView(AnkoContext.create(parent.context!!, parent)))
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = runBlocking {
        dataLoader.getPostsData(position) { posts ->
            for (postIndex in 0 until posts.count() step 1) {
                dataLoader.getPostPreview(posts.getPost(postIndex)) { bitmap ->
                    setBitmapToImageView(bitmap, holder.getPostPreviewView(postIndex))
                }
            }
        }
    }

    private fun setBitmapToImageView(bitmap: Bitmap?, imageView: ImageView) {
        (imageView.context as FragmentActivity).runOnUiThread {
            imageView.setImageBitmap(bitmap)
        }
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

        companion object {
            const val SIDE = 110
        }

    }

}

