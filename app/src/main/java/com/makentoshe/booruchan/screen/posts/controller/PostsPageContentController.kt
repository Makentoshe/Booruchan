package com.makentoshe.booruchan.screen.posts.controller

import android.view.View
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.TextView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.screen.posts.PostPageGridAdapterFactory
import com.makentoshe.booruchan.screen.posts.PostsDownloadEventListener
import org.jetbrains.anko.find

class PostsPageContentController(
    private val postsDownloadEventListener: PostsDownloadEventListener,
    private val adapterFactory: PostPageGridAdapterFactory
) {

    fun bindView(view: View) {
        //change view on posts loading failed
        postsDownloadEventListener.onError {
            bindViewOnError(view, it)
        }
        //change view on posts loading success
        postsDownloadEventListener.onSuccess {
            bindViewOnSuccess(view, it)
        }
    }

    private fun bindViewOnSuccess(view: View, posts: List<Post>) {
        if (posts.isEmpty()) {
            return bindViewOnError(view, Exception(view.context.getString(R.string.posts_ran_out)))
        }

        hideProgressBar(view)
        showGridElements(view, posts)
        setOnGridElementClickListener(view)
    }

    private fun hideProgressBar(view: View) {
        val progress = view.find<ProgressBar>(R.id.posts_page_progress)
        progress.visibility = View.GONE
    }

    private fun showGridElements(view: View, posts: List<Post>) {
        val gridview = view.find<GridView>(R.id.posts_page_gridview)
        gridview.visibility = View.VISIBLE

        println("Create adapter")
        val adapter = adapterFactory.build(posts)
        gridview.adapter = adapter
    }

    private fun setOnGridElementClickListener(view: View) {
//        val gridview = view.find<GridView>(R.id.posts_page_gridview)
//        gridview.setOnItemClickListener { _, _, itempos, _ ->
//            val position = this.position * getItemsCountInRequest(requireContext()) + itempos
//            val screen = SampleScreen(position, booru, tags)
//            router.navigateTo(screen)
//        }
    }

    private fun bindViewOnError(view: View, throwable: Throwable) {
        val messagetext = StringBuilder(throwable.localizedMessage).append("\n")
            .append(R.string.tap_for_retry).toString()

        displayMessage(view, messagetext)
        hideProgressBar(view)

//        view.setOnClickListener {
//            progress.visibility = View.VISIBLE
//            message.text = ""
//            message.visibility = View.GONE
//            view.setOnClickListener(null)
//            onViewCreated(view, null)
//        }
    }

    private fun displayMessage(view: View, messagetext: String) {
        val messageview = view.find<TextView>(R.id.posts_page_textview)
        messageview.text = messagetext
        messageview.visibility = View.VISIBLE
    }

    private fun showProgressBar(view: View) {
        val progress = view.find<ProgressBar>(R.id.posts_page_progress)
        progress.visibility = View.VISIBLE
    }
}