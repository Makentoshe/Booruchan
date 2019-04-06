package com.makentoshe.booruchan.screen.samples.model

import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.repository.Repository
import com.makentoshe.booruchan.screen.samples.SampleOptionFragment
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.find

/**
 * Calls each time when error occurs.
 *
 * @param root the main view for current fragment. It also can be the view of a parent fragment.
 * @param throwable contains an exception message.
 */
fun Fragment.onError(root: View, throwable: Throwable) {
    //show message when items is out of stock
    if (throwable is IndexOutOfBoundsException) {
        onError(root, Exception(getString(R.string.images_ran_out)))
        return
    }
    //hide progress bar
    root.find<View>(R.id.samples_progress).visibility = View.GONE
    //and display a message
    val messageview = root.find<TextView>(R.id.samples_message)
    messageview.visibility = View.VISIBLE
    messageview.text = throwable.localizedMessage
    messageview.bringToFront()
}

/**
 * Calls when need to show actions menu for post-sample image.
 */
fun Fragment.showOptionsList(booru: Booru, post: Post) {
    SampleOptionFragment.create(booru, post)
        .show(childFragmentManager, SampleOptionFragment::class.java.simpleName)
}


/* Performs loading image file from repository */
fun loadFromRepository(
    post: Post,
    repository: Repository<Post, ByteArray>,
    onSubscribe: (ByteArray?, Throwable?) -> Unit
) = Single.just(post)
    .subscribeOn(Schedulers.newThread())
    .map { repository.get(it) }
    .observeOn(AndroidSchedulers.mainThread())
    .subscribe { b, t -> onSubscribe(b, t) }
