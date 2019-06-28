package com.makentoshe.booruimageview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoContext
import java.io.Serializable

class ImageFragment : Fragment() {

    /** Navigator instance performs navigation between screens */
    private var navigator: BooruImageScreenNavigator
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(NAVIGATION, value)
        get() = arguments!!.get(NAVIGATION) as BooruImageScreenNavigator

    /** Current [Booru] API instance used for requests */
    private var booru: Booru
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(BOORU, value)
        get() = arguments!!.get(BOORU) as Booru

    /** Set of a tags might used in requests */
    private var tags: Set<Tag>
        set(value) = (arguments ?: Bundle().also { arguments = it }).putSerializable(TAGS, value as Serializable)
        get() = arguments!!.get(TAGS) as Set<Tag>

    /** Current [androidx.viewpager.widget.ViewPager] page index starts from 0 */
    private var position: Int
        set(value) = (arguments ?: Bundle().also { arguments = it }).putInt(POSITION, value)
        get() = arguments!!.getInt(POSITION)

    /** Container for [io.reactivex.disposables.Disposable] objects will be released on destroy lifecycle event */
    private val disposables = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ImageFragmentUi().createView(AnkoContext.create(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // create presenter and bind root view
        val presenter = ImageFragmentPresenter(disposables, childFragmentManager, navigator, position)
        presenter.bindView(view)
        // bind sliding view
        val slidingview = view.findViewById<SlidingUpPanelLayout>(com.makentoshe.booruimageview.R.id.slidingPanel)
        presenter.bindSlidingUpPanel(slidingview)
        // bind viewpager
        val viewpager = view.findViewById<ViewPager>(com.makentoshe.booruimageview.R.id.viewpager)
        presenter.bindViewPager(viewpager)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    companion object {
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        private const val POSITION = "Position"
        private const val NAVIGATION = "Navigation"
        fun build(navigator: BooruImageScreenNavigator, position: Int, booru: Booru, tags: Set<Tag>): ImageFragment {
            val fragment = ImageFragment()
            fragment.navigator = navigator
            fragment.position = position
            fragment.booru = booru
            fragment.tags = tags
            return fragment
        }
    }
}
