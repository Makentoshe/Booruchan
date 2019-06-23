package com.makentoshe.boorupostview.presenter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.makentoshe.boorupostview.PostSelectBroadcastReceiver
import com.makentoshe.boorupostview.model.GridElementController
import com.makentoshe.boorupostview.viewmodel.GridElementViewModel
import io.reactivex.disposables.CompositeDisposable
import java.io.Serializable

/**
 * Presenter component for a grid element.
 *
 * @param disposables contains a disposables must be released on a fragment lifecycle onDestroy event.
 * @param position is a post position.
 * @param viewmodel a viewmodel component associated with the [position].
 */
class GridElementPresenter(
    override val disposables: CompositeDisposable,
    private val position: Int,
    private val viewmodel: GridElementController
) : RxPresenter(), Serializable {

    /** Binds a grid element view */
    fun bindView(view: View) = view.setOnClickListener {
        PostSelectBroadcastReceiver.sendBroadcast(view.context, position)
    }

    /** Binds a preview image view */
    fun bindImageView(view: ImageView) {
        // setup a preview image on success
        viewmodel.successObservable.subscribe(view::setImageBitmap).let(disposables::add)
        // setup a thumbnail image on error
        viewmodel.errorObservable.subscribe {
            //todo replace by style color
            view.imageTintList = ColorStateList.valueOf(Color.DKGRAY)
            view.setImageResource(com.makentoshe.style.R.drawable.ic_alert_octagon_outline)
        }.let(disposables::add)
    }

    /** Binds a progress bar view*/
    fun bindProgressbar(view: ProgressBar) {
        viewmodel.errorObservable.subscribe {
            view.visibility = View.GONE
        }.let(disposables::add)
        viewmodel.successObservable.subscribe {
            view.visibility = View.GONE
        }.let(disposables::add)
    }
}