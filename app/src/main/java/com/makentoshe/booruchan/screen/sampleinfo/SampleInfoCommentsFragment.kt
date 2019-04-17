package com.makentoshe.booruchan.screen.sampleinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.comment.Comment
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.arguments
import com.makentoshe.booruchan.screen.sampleinfo.model.CommentsRecyclerAdapter
import com.makentoshe.booruchan.screen.sampleinfo.view.SampleInfoCommentsUi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class SampleInfoCommentsFragment : Fragment() {

    private var booru: Booru
        get() = arguments!!.get(BOORU) as Booru
        set(value) = arguments().putSerializable(BOORU, value)

    private var post: Post
        get() = arguments!!.get(POST) as Post
        set(value) = arguments().putSerializable(POST, value)

    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return SampleInfoCommentsUi()
            .createView(AnkoContext.Companion.create(requireContext(), this))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (post.hasComments) receiveComments(view) else displayNoCommentsMessage(view)
    }

    private fun receiveComments(root: View) {
        val disposable = Single.just(post)
            .subscribeOn(Schedulers.io())
            .map { booru.getComments().request(post.id) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { comments, throwable ->
                if (comments != null) {
                    handleComments(root, comments)
                } else {
                    displayErrorMessage(root, throwable)
                }
            }
        disposables.add(disposable)
    }

    private fun handleComments(root: View, comments: List<Comment>) {
        if (comments.isEmpty()) return displayNoCommentsMessage(root)

        hideProgressBar(root)

        val recyclerview = root.find<RecyclerView>(R.id.sampleinfo_comments_recycleview)
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        recyclerview.adapter = CommentsRecyclerAdapter(comments)
        recyclerview.visibility = View.VISIBLE
    }

    private fun hideProgressBar(root: View) {
        val progressbar = root.find<ProgressBar>(R.id.sampleinfo_comments_progressbar)
        progressbar.visibility = View.GONE
    }

    private fun displayErrorMessage(root: View, throwable: Throwable) {
        messageDisplay(root, throwable.localizedMessage)
    }

    private fun displayNoCommentsMessage(root: View) {
        messageDisplay(root, requireContext().getString(R.string.no_comments))
    }

    private fun messageDisplay(root: View, message: String) {
        hideProgressBar(root)

        val textview = root.find<TextView>(R.id.sampleinfo_comments_textview)
        textview.text = message
        textview.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        private const val BOORU = "Booru"
        private const val POST = "Post"
        fun create(booru: Booru, post: Post) = SampleInfoCommentsFragment().apply {
            this.booru = booru
            this.post = post
        }
    }
}