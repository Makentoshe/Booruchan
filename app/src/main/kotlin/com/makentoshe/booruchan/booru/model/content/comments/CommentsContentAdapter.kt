package com.makentoshe.booruchan.booru.model.content.comments

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI
import org.jetbrains.anko.AnkoContext

class CommentsContentAdapter(private val dataLoader: CommentsContentDataLoader)
    : RecyclerView.Adapter<CommentsContentAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CommentsContentViewHolderUI()
                .createView(AnkoContext.create(parent.context!!, parent)))
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println(position)
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

}