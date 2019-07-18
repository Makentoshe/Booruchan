package com.makentoshe.boorusamplesview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.makentoshe.api.DefaultPostsRequest
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.boorusamplesview.presenter.PageFragmentPresenter
import com.makentoshe.boorusamplesview.model.TypeFragmentBuilder
import com.makentoshe.boorusamplesview.view.PageFragmentUi
import com.makentoshe.boorusamplesview.viewmodel.PageFragmentViewModel
import com.makentoshe.style.CircularProgressBar
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

/**
 * [androidx.viewpager.widget.ViewPager] page element fragment
 */
class PageFragment : Fragment() {

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
        return PageFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    /** Setup user interface logic and binds it with the viewmodel */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val fragmentBuilder = TypeFragmentBuilder(childFragmentManager, booru, position)
        // viewmodel factory performs viewmodel creation if needed
        val factory = PageFragmentViewModel.Factory(booru, requireContext())
        // viewmodel instance performs a data receiving from repository
        val viewmodel = ViewModelProviders.of(this, factory)[PageFragmentViewModel::class.java]
        // start post fetching from repository
        viewmodel.execute(DefaultPostsRequest(1, tags, position))
        // presenter component binds a viewmodel and view
        val presenter = PageFragmentPresenter(disposables, viewmodel, fragmentBuilder)
        // bind container view
        val containerview = view.findViewById<View>(com.makentoshe.boorusamplesview.R.id.container)
        presenter.bindContainerView(containerview)
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
        /** Factory method creates an [PageFragment] instance */
        fun build(position: Int, booru: Booru, tags: Set<Tag>): PageFragment {
            val fragment = PageFragment()
            fragment.booru = booru
            fragment.position = position
            fragment.tags = tags
            return fragment
        }
    }
}
