package com.makentoshe.booruchan.booru.model.content.comments

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI.CommentUIBuilder.Ids.Companion.body
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI.CommentUIBuilder.Ids.Companion.createdAt
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI.CommentUIBuilder.Ids.Companion.creator
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI.Id.commentsLayout
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI.Id.postDataDate
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI.Id.postDataLayout
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI.Id.postDataScore
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI.Id.postDataTags
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI.Id.postPreviewImageView
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI.Id.progressBar
import com.makentoshe.booruchan.common.api.entity.Comment
import com.makentoshe.booruchan.common.api.entity.Post
import com.makentoshe.booruchan.common.runOnUi
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.collections.forEachByIndex
import java.lang.StringBuilder

class CommentsContentAdapter(private val dataLoader: CommentsContentDataLoader,
                             @JvmField val postIdsList: IntArray)
    : RecyclerView.Adapter<CommentsContentAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CommentsContentViewHolderUI()
                .createView(AnkoContext.create(parent.context!!, parent)))
    }

    override fun getItemCount(): Int {
        return postIdsList.size
    }

    override fun getItemId(position: Int): Long {
        return postIdsList[position].toLong()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.clear()
        dataLoader.getPostById(postIdsList[position]) { post ->
            setupPostData(holder, post)
            dataLoader.getPostPreview(post) { bitmap ->
                runOnUi { holder.setPostPreview(bitmap) }
                dataLoader.getCommentsByPost(post) { comments ->
                    runOnUi {
                        holder.showCommentsLayout()
                        comments.forEachByIndex { holder.addCommentView(it) }
                    }
                }
            }
        }
    }

    private fun setupPostData(holder: ViewHolder, post: Post) {
        runOnUi {
            holder.showPostView()
            holder.setPostDate(dataLoader.convertTime(post.createdAt))
            holder.setPostScore(post.score.toString())
            holder.setPostTags(post.tags)
            holder.hideProgressBar()
        }
    }

    fun clear() {
        dataLoader.clearSchedulers()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @JvmField
        val context = itemView.context

        fun setPostPreview(bitmap: Bitmap) {
            itemView.findViewById<ImageView>(postPreviewImageView).setImageBitmap(bitmap)
        }

        fun setPostDate(date: String) {
            itemView.findViewById<TextView>(postDataDate).text = date
        }

        fun setPostScore(score: String) {
            itemView.findViewById<TextView>(postDataScore).text = score
        }

        fun setPostTags(tags: Array<String>) {
            val builder = StringBuilder()
            tags.forEach { builder.append(it).append(" ") }
            itemView.findViewById<TextView>(postDataTags).hint = builder
        }

        fun showPostView() {
            itemView.findViewById<View>(postDataLayout).visibility = View.VISIBLE
            itemView.findViewById<View>(postPreviewImageView).visibility = View.VISIBLE
        }

        fun hideProgressBar() {
            itemView.findViewById<View>(progressBar).visibility = View.GONE
        }

        fun showCommentsLayout() {
            itemView.findViewById<View>(commentsLayout).visibility = View.VISIBLE
        }

        fun addCommentView(comment: Comment) {
            val commentView = createAndSetupCommentView()
            fillViewByData(commentView, comment)
        }

        private fun createAndSetupCommentView(): View {
            val layout = itemView.findViewById<ViewGroup>(commentsLayout)
            val view = CommentsContentViewHolderUI.CommentUIBuilder()
                    .createView(AnkoContext.create(context, layout))
            layout.addView(view)
            return view
        }

        private fun fillViewByData(view: View, comment: Comment) {
            view.findViewById<TextView>(body).text = comment.body
            view.findViewById<TextView>(creator).text = comment.creator
            view.findViewById<TextView>(createdAt).text = comment.createdAt
        }

        fun clear() {
            val layout = itemView.findViewById<ViewGroup>(commentsLayout)
            layout.visibility = View.GONE
            layout.removeAllViews()
            itemView.findViewById<ImageView>(postPreviewImageView).setImageBitmap(null)
        }
    }

}