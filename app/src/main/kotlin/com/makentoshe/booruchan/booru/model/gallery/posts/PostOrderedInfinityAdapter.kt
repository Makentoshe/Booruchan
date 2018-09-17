package com.makentoshe.booruchan.booru.model.gallery.posts

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.booru.view.posts.PostOrderedInfinityAdapterViewHolderUI
import kotlinx.coroutines.experimental.*
import org.jetbrains.anko.*
import java.util.*

class PostOrderedInfinityAdapter(private val viewModel: PostOrderedInfinityViewModel, private val searchTerm: String)
    : RecyclerView.Adapter<PostOrderedInfinityAdapter.ViewHolder>() {

    private val jobScheduler = JobScheduler(10)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                PostOrderedInfinityAdapterViewHolderUI()
                        .createView(AnkoContext.create(parent.context!!, parent)))
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = runBlocking {
        val job = GlobalScope.launch(Dispatchers.Default) {
            //todo implement post loading here
            delay(1000)
            println("Load row $position")
        }
        jobScheduler.addJob(job)
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
}

