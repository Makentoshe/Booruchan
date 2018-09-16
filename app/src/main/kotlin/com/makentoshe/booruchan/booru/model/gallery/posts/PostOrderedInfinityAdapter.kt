package com.makentoshe.booruchan.booru.model.gallery.posts

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.booru.view.posts.PostOrderedInfinityAdapterViewHolderUI
import org.jetbrains.anko.*

class PostOrderedInfinityAdapter(private val viewModel: PostOrderedInfinityViewModel, private val searchTerm: String)
    : RecyclerView.Adapter<PostOrderedInfinityAdapter.ViewHolder>() {

    init {
        println("Create with term $searchTerm")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                PostOrderedInfinityAdapterViewHolderUI()
                        .createView(AnkoContext.create(parent.context!!, parent)))
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //todo implement this
        println("Load row $position")
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            const val SIDE = 110
        }

    }
}

