package com.makentoshe.booruchan.screen.samples.controller

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.screen.posts.page.controller.imagedownload.PreviewImageDownloadController
import com.makentoshe.booruchan.screen.posts.page.controller.postsdownload.PostsDownloadListener
import com.makentoshe.booruchan.screen.samples.model.SamplePageFragmentRouter
import com.makentoshe.booruchan.screen.samples.model.SamplePageConcreteFragmentFactory
import org.jetbrains.anko.find
import java.io.File

/**
 * Controller for the [SamplePageFragment].
 */
class SamplePageController(
    private val postsDownloadListener: PostsDownloadListener,
    private val previewImageDownloadController: PreviewImageDownloadController,
    private val fragmentFactory: SamplePageConcreteFragmentFactory,
    private val fragmentRouter: SamplePageFragmentRouter
) {

    fun bindView(view: View) {
        postsDownloadListener.onSuccess {
            bindOnSuccess(view, it[0])
        }

        postsDownloadListener.onError {
            bindOnError(view, it)
        }

        previewImageDownloadController.onSuccess {
            previewBindOnSuccess(view, it)
        }

        previewImageDownloadController.onError {
            previewBindOnError(view, it)
        }
    }

    /**
     * Calls when post was successfully downloaded.
     */
    private fun bindOnSuccess(view: View, post: Post) {
        previewImageDownloadController.start(post)

        val fragment = when (File(post.sampleUrl).extension) {
            "webm" -> fragmentFactory.buildWebmFragment(post)
            "gif" -> fragmentFactory.buildGifFragment(post)
            else -> fragmentFactory.buildImageFragment(post)
        }

        fragmentRouter.add(fragment)
    }

    /**
     * Calls on posts downloading was finished with errors.
     * Just inform user about current error.
     */
    private fun bindOnError(view: View, throwable: Throwable) {
        //show message when items is out of stock
        if (ifIsOutOfStock(view, throwable)) return
        //hide progress bar
        view.find<View>(R.id.samples_progress).visibility = View.GONE
        //and display a message
        val messageview = view.find<TextView>(R.id.samples_message)
        messageview.visibility = View.VISIBLE
        messageview.text = throwable.localizedMessage
        messageview.bringToFront()

    }

    /**
     * Returns true if [t] is an instance of the [IndexOutOfBoundsException].
     * That means the posts downloading returns 0 element (out of stock).
     */
    private fun ifIsOutOfStock(view: View, t: Throwable): Boolean {
        if (t is IndexOutOfBoundsException) {
            val message = view.context.getString(R.string.images_ran_out)
            bindOnError(view, Exception(message))
            return true
        }
        return false
    }

    /**
     * Calls when preview loading was finished successfully.
     */
    private fun previewBindOnSuccess(view: View, bitmap: Bitmap) {
        val imageview = view.find<ImageView>(R.id.samples_preview)
        imageview.visibility = View.VISIBLE
        imageview.setImageBitmap(bitmap)
    }

    /**
     * Calls when preview loading failed.
     */
    private fun previewBindOnError(view: View, throwable: Throwable) {
        val imageview = view.find<ImageView>(R.id.samples_preview)
        imageview.visibility = View.GONE
    }
}