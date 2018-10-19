package com.makentoshe.booruchan.booru.content.comments.vertical.pager.model

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.content.model.PreviewLoader
import com.makentoshe.booruchan.booru.content.comments.vertical.pager.view.ViewHolderUI
import com.makentoshe.booruchan.common.api.entity.Comment
import com.makentoshe.booruchan.common.api.entity.Post
import com.makentoshe.booruchan.common.runOnUi
import org.jetbrains.anko.AnkoContext
import java.lang.StringBuilder

class CommentsRecycleViewAdapter(
        private val list: List<Pair<Post, List<Comment>>>,
        private val previewLoader: PreviewLoader)
    : RecyclerView.Adapter<CommentsRecycleViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ViewHolderUI()
                .createView(AnkoContext.create(parent.context, parent))
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.clear()
        previewLoader.getPostPreview(list[position].first) {
            if (it != null) {
                runOnUi { holder.setPreviewToView(it) }
            }
        }
        holder.setTagsToView(list[position].first.tags)
        holder.setScoreToView(list[position].first.score)
        holder.setTimeCreationToView(list[position].first.createdAt)
        for (comment in list[position].second) {
            holder.addCommentToView(comment)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun setTagsToView(tags: Array<String>) {
            val builder = StringBuilder()
            tags.forEach { builder.append(it).append(" ") }
            itemView.findViewById<TextView>(R.id.booru_content_comment_viewholder_postview_tags)
                    .text = builder
        }

        fun setScoreToView(score: Int) {
            itemView.findViewById<TextView>(R.id.booru_content_comment_viewholder_postview_score)
                    .text = score.toString()
        }

        fun setTimeCreationToView(time: String) {
            itemView.findViewById<TextView>(R.id.booru_content_comment_viewholder_postview_date)
                    .text = time
        }

        fun setPreviewToView(bitmap: Bitmap) {
            itemView.findViewById<ImageView>(R.id.booru_content_comment_viewholder_postview_preview)
                    .setImageBitmap(bitmap)
        }

        fun addCommentToView(comment: Comment) {
            val layout = itemView
                    .findViewById<ViewGroup>(R.id.booru_content_comment_viewholder_commentsview)

            val view = ViewHolderUI
                    .CommentUIBuilder()
                    .createView(AnkoContext.create(itemView.context, layout))
            layout.addView(view)

            view.findViewById<TextView>(R.id.booru_content_comment_viewholder_commentsview_comment_body)
                    .text = comment.body
            view.findViewById<TextView>(R.id.booru_content_comment_viewholder_commentsview_comment_creator)
                    .text = StringBuilder(comment.creator)
//                    .append(">>").append(comment.id)
            view.findViewById<TextView>(R.id.booru_content_comment_viewholder_commentsview_comment_createdAt)
                    .text = comment.created_at
        }

        fun clear() {
            itemView.findViewById<ImageView>(R.id.booru_content_comment_viewholder_postview_preview)
                    .setImageBitmap(null)
            itemView.findViewById<ViewGroup>(R.id.booru_content_comment_viewholder_commentsview)
                    .removeAllViews()
        }
    }
}