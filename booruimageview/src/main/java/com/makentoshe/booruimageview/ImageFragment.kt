package com.makentoshe.booruimageview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorulibrary.entitiy.Tag
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

/**
 * Base fragment.
 */
class ImageFragment : Fragment() {

    /** Navigator instance performs navigation between screens */
    private var navigator: BooruImageScreenNavigator
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable("Navigation", value)
        get() = arguments!!.get("Navigation") as BooruImageScreenNavigator

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
        return ImageFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // create adapter instance
        val viewpagerAdapter = ImageViewPagerAdapter(childFragmentManager, booru, tags)
        // create presenter and bind root view
        val presenter = ImageFragmentPresenter(disposables, navigator, position, viewpagerAdapter)
        presenter.bindView(view)
        // bind sliding view
        val slidingview = view.findViewById<SlidingUpPanelLayout>(com.makentoshe.booruimageview.R.id.slidingPanel)
        presenter.bindSlidingUpPanel(slidingview)
        // bind viewpager
        val viewpager = view.findViewById<ViewPager>(com.makentoshe.booruimageview.R.id.viewpager)
        presenter.bindViewPager(viewpager)
    }

    /** Release disposables */
    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        /** Factory method creates a [ImageFragment] instance */
        fun build(
            navigator: BooruImageScreenNavigator, position: Int, booru: Booru, tags: Set<Tag>, post: Post
        ): ImageFragment {
            val fragment = ImageFragment()
            fragment.navigator = navigator
            fragment.position = position
            fragment.booru = booru
            fragment.tags = tags
            return fragment
        }
    }
}
