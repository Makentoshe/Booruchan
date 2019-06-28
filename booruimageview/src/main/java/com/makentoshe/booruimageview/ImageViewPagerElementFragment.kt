package com.makentoshe.booruimageview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.backgroundColor
import java.io.Serializable
import kotlin.random.Random

class ImageViewPagerElementFragment : Fragment() {

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
        return FrameLayout(requireContext()).apply { backgroundColor = Random.nextInt() }
    }

    companion object {
        private const val BOORU = "Booru"
        private const val TAGS = "Tags"
        private const val POSITION = "Position"
        fun build(position: Int, booru: Booru, tags: Set<Tag>): ImageViewPagerElementFragment {
            val fragment = ImageViewPagerElementFragment()
            fragment.booru = booru
            fragment.position = position
            fragment.tags = tags
            return fragment
        }
    }
}
