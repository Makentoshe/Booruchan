package com.makentoshe.booruchan.screen.sampleinfo.model

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.component.comment.Comment
import com.makentoshe.booruchan.screen.sampleinfo.view.CommentUi
import com.makentoshe.booruchan.screen.sampleinfo.view.CommentsViewHolder
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class CommentsRecyclerAdapter(private val comments: List<Comment>) : RecyclerView.Adapter<CommentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        val context = parent.context
        val view = CommentUi().createView(AnkoContext.create(context, context))
        return CommentsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        val comment = comments[position]

        val bodyview = holder.itemView.find<TextView>(R.id.comment_body)
        bodyview.text = comment.body

        val authorview = holder.itemView.find<TextView>(R.id.comment_author)
        authorview.text = comment.creator

        val dateview = holder.itemView.find<TextView>(R.id.comment_date)
        dateview.text = comment.createdAt
    }

    override fun getItemCount() = comments.size
}