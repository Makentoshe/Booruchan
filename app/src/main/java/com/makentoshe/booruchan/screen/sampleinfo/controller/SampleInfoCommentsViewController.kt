package com.makentoshe.booruchan.screen.sampleinfo.controller

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.component.comment.Comment
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.common.download.DownloadListener
import com.makentoshe.booruchan.screen.sampleinfo.model.CommentsRecyclerAdapter
import org.jetbrains.anko.find

class SampleInfoCommentsViewController(
    private val root: View,
    private val post: Post,
    private val commentsDownloadListener: DownloadListener<List<Comment>>
) {

    private val progressBar by lazy { root.find<ProgressBar>(R.id.sampleinfo_comments_progressbar) }

    private val recyclerview by lazy { root.find<RecyclerView>(R.id.sampleinfo_comments_recycleview) }

    private val messageview by lazy { root.find<TextView>(R.id.sampleinfo_comments_textview) }

    fun bind(fragment: Fragment) = if (post.hasComments) receiveComments() else displayNoCommentsMessage()

    private fun receiveComments() {
        commentsDownloadListener.onSuccess(::handleComments)
        commentsDownloadListener.onError(::displayErrorMessage)
    }

    private fun handleComments(comments: List<Comment>) {
        if (comments.isEmpty()) return displayNoCommentsMessage()

        recyclerview.layoutManager = LinearLayoutManager(root.context)
        recyclerview.adapter = CommentsRecyclerAdapter(comments)
        recyclerview.visibility = View.VISIBLE

        hideProgressBar()
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    private fun displayErrorMessage(throwable: Throwable) {
        messageDisplay(throwable.localizedMessage)
    }

    private fun displayNoCommentsMessage() {
        messageDisplay(root.context.getString(R.string.no_comments))
    }

    private fun messageDisplay(message: String) {
        hideProgressBar()

        messageview.text = message
        messageview.visibility = View.VISIBLE
    }
}