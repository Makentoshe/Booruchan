package com.makentoshe.booruchan.booru.model.content.comments

import android.graphics.Bitmap
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI.Ids.Companion.postDataDate
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI.Ids.Companion.postDataLayout
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI.Ids.Companion.postDataScore
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI.Ids.Companion.postDataTags
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI.Ids.Companion.postDataUser
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI.Ids.Companion.postPreviewImageView
import com.makentoshe.booruchan.booru.view.content.comments.CommentsContentViewHolderUI.Ids.Companion.progressBar
import com.makentoshe.booruchan.booru.view.content.comments.ProgressBarController
import com.makentoshe.booruchan.common.api.entity.Post
import com.makentoshe.booruchan.common.runOnUi
import org.jetbrains.anko.AnkoContext
import java.lang.StringBuilder
import java.util.*

class CommentsContentAdapter(private val dataLoader: CommentsContentDataLoader,
                             private val controller: ProgressBarController)
    : RecyclerView.Adapter<CommentsContentAdapter.ViewHolder>() {

    private var postIdsList = ArrayList<Int>()

    init {
        dataLoader.getPostIds(1) {
            postIdsList.addAll(it.toList())
            runOnUi {
                notifyDataSetChanged()
                controller.hideProgressBar()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CommentsContentViewHolderUI()
                .createView(AnkoContext.create(parent.context!!, parent)))
    }

    override fun getItemCount(): Int {
        return postIdsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        println("$position ${postIdsList[position]}")
        dataLoader.getPostById(postIdsList[position]) {post ->
            setupPostData(holder, post)
            dataLoader.getPostPreview(post) { bitmap ->
                runOnUi { holder.setPostPreview(bitmap) }
            }
        }
    }

    private fun setupPostData(holder: ViewHolder, post: Post) {
        runOnUi {
            holder.showPostView()
            holder.setPostDate(post.createdAt)
            holder.setPostUser(post.creatorId.toString())
            holder.setPostScore(post.score.toString())
            holder.setPostTags(post.tags)
        }
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @JvmField
        val context = itemView.context

        fun setPostPreview(bitmap: Bitmap) {
            itemView.findViewById<ImageView>(postPreviewImageView).setImageBitmap(bitmap)
        }

        fun setPostDate(date: String) {
            setData(postDataDate, StringBuilder(context.getString(R.string.date)).append(": ").append(date))
        }

        fun setPostUser(user: String) {
            setData(postDataUser, StringBuilder(context.getString(R.string.user)).append(": ").append(user))
        }

        fun setPostScore(score: String) {
            setData(postDataScore, StringBuilder(context.getString(R.string.score)).append(": ").append(score))
        }

        fun setPostTags(tags: Array<String>) {
            val builder = StringBuilder(context.getString(R.string.tags)).append(": ")
            tags.forEach { builder.append(it).append(" ") }
            setData(postDataTags, builder)
        }

        fun setData(id: Int, text: CharSequence) {
            itemView.findViewById<TextView>(id).text = text
        }

        fun showPostView() {
            itemView.findViewById<View>(postDataLayout).visibility = View.VISIBLE
            itemView.findViewById<View>(postPreviewImageView).visibility = View.VISIBLE
            hideProgressBar()
        }

        fun hideProgressBar() {
            itemView.findViewById<View>(progressBar).visibility = View.GONE
        }

    }

}