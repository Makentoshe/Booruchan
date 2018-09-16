package com.makentoshe.booruchan.booru.model.gallery.posts

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.booru.view.posts.PostOrderedInfinityAdapterViewHolderUI
import org.jetbrains.anko.*

class PostOrderedInfinityAdapter : RecyclerView.Adapter<PostOrderedInfinityAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                PostOrderedInfinityAdapterViewHolderUI()
                        .createView(AnkoContext.create(parent.context!!, parent)))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}



    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        companion object {
            const val SIDE = 110
        }

    }
}

