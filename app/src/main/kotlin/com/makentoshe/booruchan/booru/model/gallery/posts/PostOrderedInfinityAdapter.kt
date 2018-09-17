package com.makentoshe.booruchan.booru.model.gallery.posts

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
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
       getPostsFromContainerOrLoad(position) {

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

    class PostsContainer(private val packageSize: Int = 50) {

        private val postsPackage = ArrayDeque<Pair<Int, Posts<out Post>>>()

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

    }
}

