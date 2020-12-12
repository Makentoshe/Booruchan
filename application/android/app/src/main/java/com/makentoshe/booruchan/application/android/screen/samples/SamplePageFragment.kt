package com.makentoshe.booruchan.application.android.screen.samples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.makentoshe.booruchan.application.android.FullContentDownloadExecutor
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.makentoshe.booruchan.application.android.fragment.FragmentArguments
import com.makentoshe.booruchan.application.android.screen.samples.model.SampleFragmentStateAdapter
import com.makentoshe.booruchan.application.android.screen.samples.navigation.SampleNavigation
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.Content
import com.makentoshe.booruchan.core.post.Post
import kotlinx.android.synthetic.main.fragment_sample_page.*
import toothpick.ktp.delegate.inject
import java.io.File

class SamplePageFragment : CoreFragment(), FullContentDownloadExecutor.DownloadListener {

    companion object {
        fun build(post: Post, booruContextClass: Class<BooruContext>): SamplePageFragment {
            val fragment = SamplePageFragment()
            fragment.arguments.post = post
            fragment.arguments.booruclass = booruContextClass
            return fragment
        }
    }

    val arguments = Arguments(this)
    private val navigation by inject<SampleNavigation>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = SampleFragmentStateAdapter(this, arguments.post, arguments.booruclass)
        fragment_sample_vertical.adapter = adapter
        fragment_sample_vertical.setCurrentItem(1, false)
        fragment_sample_vertical.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position != 0) return
                fragment_sample_vertical.alpha = positionOffset
                if (positionOffset == 0f) navigation.closeScreen()
            }
        })
    }

    override fun onFinishDownload(directory: File, result: Result<*>) {
        val stringbuilder = StringBuilder("File ${directory.name} ")
        result.fold({ stringbuilder.append("successfully downloaded") }, { stringbuilder.append("already exists") })
        Toast.makeText(requireContext(), stringbuilder.toString(), Toast.LENGTH_LONG).show()
    }

    class Arguments(fragment: SamplePageFragment) : FragmentArguments(fragment) {

        var post: Post
            get() = fragmentArguments.getSerializable(POST) as Post
            set(value) = fragmentArguments.putSerializable(POST, value)

        var booruclass: Class<BooruContext>
            get() = fragmentArguments.getSerializable(CLASS) as Class<BooruContext>
            set(value) = fragmentArguments.putSerializable(CLASS, value)

        companion object {
            private const val POST = "post"
            private const val CLASS = "class"
        }
    }

    override fun onStartDownload(directory: File, content: Content) = Unit
}
