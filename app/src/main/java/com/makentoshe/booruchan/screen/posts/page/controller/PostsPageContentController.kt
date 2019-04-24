package com.makentoshe.booruchan.screen.posts.page.controller

import android.view.View
import android.widget.GridView
import android.widget.ProgressBar
import android.widget.TextView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.model.BooruHolder
import com.makentoshe.booruchan.model.PositionHolder
import com.makentoshe.booruchan.model.TagsHolder
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.booruchan.screen.posts.container.model.getItemsCountInRequest
import com.makentoshe.booruchan.screen.posts.page.controller.postsdownload.PostsDownloadEventListener
import com.makentoshe.booruchan.screen.posts.page.model.PostPageGridAdapterFactory
import com.makentoshe.booruchan.screen.samples.SampleScreen
import org.jetbrains.anko.find

/**
 * Perform controlling page's root-view.
 *
 * @param postsDownloadEventListener is a listener for posts loading status.
 * @param adapterFactory is a factory which creates GridAdapter instances.
 */
class PostsPageContentController(
    private val postsDownloadEventListener: PostsDownloadEventListener,
    private val adapterFactory: PostPageGridAdapterFactory,
    private val positionHolder: PositionHolder,
    private val postPageContentRouter: PostPageContentRouter
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

        val adapter = adapterFactory.build(posts)
        gridview.adapter = adapter
    }

    private fun setOnGridElementClickListener(view: View) {
        val gridview = view.find<GridView>(R.id.posts_page_gridview)
        gridview.setOnItemClickListener { _, _, itempos, _ ->
            val position = positionHolder.position * getItemsCountInRequest(view.context) + itempos
            postPageContentRouter.navigateToSampleScreen(position)
        }
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

class PostPageContentRouter(
    private val router: Router,
    private val sampleScreenBuilder: SampleScreenBuilder
) {
    fun navigateToSampleScreen(position: Int) {
        val screen = sampleScreenBuilder.build(position)
        router.navigateTo(screen)
    }
}

class SampleScreenBuilder(
    private val booruHolder: BooruHolder,
    private val tagsHolder: TagsHolder
) {
    fun build(position: Int): Screen {
        return SampleScreen(position, booruHolder.booru, tagsHolder.set)
    }
}