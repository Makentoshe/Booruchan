package com.makentoshe.boorusamplesview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView
import com.makentoshe.boorusamplesview.presenter.ImageViewPagerElementFragmentPresenter
import com.makentoshe.boorusamplesview.view.ImageViewPagerElementFragmentUi
import com.makentoshe.boorusamplesview.viewmodel.ImageViewPagerElementFragmentViewModel
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.style.CircularProgressBar
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

/**
 * [androidx.viewpager.widget.ViewPager] page element fragment
 */
class ImageViewPagerElementFragment : Fragment() {

    /** Current [Booru] API instance used for requests */
    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable("Booru", value)
        get() = arguments!!.get("Booru") as Booru

    /** Set of a tags might used in requests */
    private var tags: Set<Tag>
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable("Tags", value as Serializable)
        get() = arguments!!.get("Tags") as Set<Tag>

    /** Current [androidx.viewpager.widget.ViewPager] page index starts from 0 */
    private var position: Int
        set(value) = (arguments ?: Bundle().also { arguments = it }).putInt("Position", value)
        get() = arguments!!.getInt("Position")

    /** Container for [io.reactivex.disposables.Disposable] objects will be released on destroy lifecycle event */
    private val disposables = CompositeDisposable()

    /** Create user interface */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ImageViewPagerElementFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    /** Setup user interface logic and binds it with the viewmodel */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // viewmodel factory performs viewmodel creation if needed
        val factory = ImageViewPagerElementFragmentViewModel.Factory(booru, tags, position, requireContext())
        // viewmodel instance performs a data receiving from repository
        val viewmodel = ViewModelProviders.of(this, factory)[ImageViewPagerElementFragmentViewModel::class.java]
        // presenter component binds a viewmodel and view
        val presenter = ImageViewPagerElementFragmentPresenter(disposables, viewmodel)
        // bind image view
        val imageview = view.findViewById<SubsamplingScaleImageView>(com.makentoshe.boorusamplesview.R.id.imageview)
        presenter.bindImageView(imageview)
        // bind indeterminate bar
        val indeterminateBar = view.findViewById<ProgressBar>(com.makentoshe.boorusamplesview.R.id.indeterminateprogress)
        presenter.bindIndeterminateBar(indeterminateBar)
        // bind progress bar
        val progressBar = view.findViewById<CircularProgressBar>(com.makentoshe.boorusamplesview.R.id.circularprogress)
        presenter.bindProgressBar(progressBar)
    }

    /** Release disposables */
    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        /** Factory method creates an [ImageViewPagerElementFragment] instance */
        fun build(position: Int, booru: Booru, tags: Set<Tag>): ImageViewPagerElementFragment {
            val fragment = ImageViewPagerElementFragment()
            fragment.booru = booru
            fragment.position = position
            fragment.tags = tags
            return fragment
        }
    }
}
