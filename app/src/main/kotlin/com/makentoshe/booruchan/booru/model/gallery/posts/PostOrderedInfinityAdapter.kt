package com.makentoshe.booruchan.booru.model.gallery.posts

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.makentoshe.booruchan.booru.view.posts.PostOrderedInfinityAdapterViewHolderUI
import com.makentoshe.booruchan.common.api.HttpClient
import com.makentoshe.booruchan.common.api.Posts
import com.makentoshe.booruchan.common.api.entity.Post
import kotlinx.coroutines.experimental.*
import org.jetbrains.anko.*
import java.util.*
import kotlin.NoSuchElementException

class PostOrderedInfinityAdapter(private val viewModel: PostOrderedInfinityViewModel, private val searchTerm: String)
    : RecyclerView.Adapter<PostOrderedInfinityAdapter.ViewHolder>() {

    private val jobScheduler = JobScheduler(10)
    private val postsContainer = PostsContainer()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                PostOrderedInfinityAdapterViewHolderUI()
                        .createView(AnkoContext.create(parent.context!!, parent)))
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = runBlocking {
        getPostsFromContainerOrLoad(position) { posts ->
            posts.forEachWithIndex { postIndex, post ->
                val index = position * posts.count() + postIndex
                getPostPreviewFromContainerOrLoad(index, post) {
                    //todo set bitmap to image view in ui thread
                }
            }
        }
    }

    private fun getPostsFromContainerOrLoad(position: Int, doNext: (Posts<out Post>) -> (Unit)) {
        try {
            doNext.invoke(postsContainer.getPostsPackage(position))
        } catch (e: NoSuchElementException) {
            val job = GlobalScope.launch(Dispatchers.Default) {
                viewModel.booru.getPostsByTags(3, searchTerm, position, HttpClient()) {
                    postsContainer.addPostsPackage(position, it)
                    doNext.invoke(it)
                }
            }
            jobScheduler.addJob(job)
        }
    }

    private fun getPostPreviewFromContainerOrLoad(index: Int, post: Post, `do`: (Bitmap) -> (Unit)) {
        try {
            val bitmap = postsContainer.getPostPreview(index)
            `do`.invoke(bitmap)
            println("get bitmap from container in position $index")
        } catch (e: NoSuchElementException) {
            PostPreviewDownload(post.previewUrl).download {
                postsContainer.addPostPreview(index, it)
                `do`.invoke(it)
                println("load bitmap in position $index")
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            const val SIDE = 110
        }

    }

    class JobScheduler(private val maxSize: Int) {

        private val jobDeque = ArrayDeque<Job>()

        fun addJob(job: Job) {
            if (jobDeque.size >= maxSize) {
                jobDeque.removeFirst().cancel()
            }
            jobDeque.addLast(job)
        }

    }

    class PostsContainer(private val packageSize: Int = 50, private val previewsSize: Int = 50) {

        private val postsPackage = ArrayDeque<Pair<Int, Posts<out Post>>>()
        private val postsPreview = ArrayDeque<Pair<Int, Bitmap>>()

        fun addPostsPackage(index: Int, posts: Posts<out Post>) {
            val pair = Pair(index, posts)
            if (postsPackage.size >= packageSize) {
                postsPackage.removeFirst()
            }
            postsPackage.addLast(pair)
        }

        fun getPostsPackage(index: Int): Posts<out Post> {
            postsPackage.forEach {
                if (it.first == index) return it.second
            }
            throw NoSuchElementException()
        }

        fun addPostPreview(index: Int, bitmap: Bitmap) {
            val pair = Pair(index, bitmap)
            if (postsPreview.size >= previewsSize) {
                postsPreview.removeFirst().second.recycle()
            }
            postsPreview.addLast(pair)
        }

        fun getPostPreview(index: Int): Bitmap {
            postsPreview.forEach {
                if (it.first == index) return it.second
            }
            throw NoSuchElementException()
        }

    }

    class PostPreviewDownload(private val url: String) {

        fun download(`do`: (Bitmap) -> (Unit)): Job {
            return GlobalScope.launch {
                val bitmap = BitmapFactory.decodeStream(HttpClient().get(url).stream())
                `do`.invoke(bitmap)
            }
        }

    }
}

