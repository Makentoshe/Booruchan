package com.makentoshe.booruchan.booru.model.gallery.posts

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.view.posts.PostOrderedInfinityAdapterViewHolderUI
import com.makentoshe.booruchan.common.api.HttpClient
import com.makentoshe.booruchan.common.api.Posts
import com.makentoshe.booruchan.common.api.entity.Post
import kotlinx.coroutines.experimental.*
import org.jetbrains.anko.*
import java.util.*
import kotlin.collections.ArrayList

class PostOrderedInfinityAdapter(private val viewModel: PostOrderedInfinityViewModel, val searchTerm: String)
    : RecyclerView.Adapter<PostOrderedInfinityAdapter.ViewHolder>() {

    private val previewLoadScheduler = JobScheduler(21)
    private val postDataLoadScheduler = JobScheduler(10)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                PostOrderedInfinityAdapterViewHolderUI()
                        .createView(AnkoContext.create(parent.context!!, parent)))
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = runBlocking {
        loadPostsData(position) { posts ->
            posts.forEachWithIndex { postIndex, post ->
                val job = PostPreviewDownload(post.previewUrl).download {
                    setBitmapToImageView(it, holder.getPostPreviewView(postIndex))
                }
                previewLoadScheduler.addJob(job)
            }
        }
    }

    private fun loadPostsData(position: Int, doNext: (Posts<out Post>) -> (Unit)) {
        val job = GlobalScope.launch(Dispatchers.Default) {
            viewModel.booru.getPostsByTags(3, searchTerm, position, HttpClient()) {
                doNext.invoke(it)
            }
        }
        postDataLoadScheduler.addJob(job)
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

    class JobScheduler(private val maxSize: Int) {

        private val jobDeque = ArrayDeque<Job>()

        fun addJob(job: Job) {
            if (jobDeque.size >= maxSize) {
                jobDeque.pollFirst()?.cancel()
                println("Remove job")
            }
            jobDeque.addLast(job)
        }

    }

    class PostPreviewDownload(private val url: String) {

        fun download(`do`: (Bitmap?) -> (Unit)): Job {
            return GlobalScope.launch {
                val stream = HttpClient().get(url).stream()
                val bitmap = BitmapFactory.decodeStream(stream)
                `do`.invoke(bitmap)
            }
        }

    }
}

