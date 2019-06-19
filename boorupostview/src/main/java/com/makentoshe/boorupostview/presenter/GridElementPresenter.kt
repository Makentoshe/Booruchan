package com.makentoshe.boorupostview.presenter

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.makentoshe.boorupostview.PostSelectBroadcastReceiver
import io.reactivex.disposables.CompositeDisposable
import java.io.Serializable

/**
 * Presenter component for a grid element.
 *
 * @param position is a post position.
 */
class GridElementPresenter(
    override val disposables: CompositeDisposable, private val position: Int
): RxPresenter(), Serializable {

    /** Binds a grid element view */
    fun bindView(view: View) = view.setOnClickListener {
        PostSelectBroadcastReceiver.sendBroadcast(view.context, position)
    }

    /** Binds a preview image view */
    fun bindImageView(view: ImageView) {

    }

    /** Binds a progress bar view*/
    fun bindProgressbar(view: ProgressBar) {

    }
}