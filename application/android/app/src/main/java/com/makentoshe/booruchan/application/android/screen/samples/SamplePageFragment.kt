package com.makentoshe.booruchan.application.android.screen.samples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.makentoshe.booruchan.application.android.fragment.FragmentArguments
import com.makentoshe.booruchan.application.android.screen.samples.model.SampleFragmentStateAdapter
import com.makentoshe.booruchan.core.context.BooruContext
import com.makentoshe.booruchan.core.post.Post
import kotlinx.android.synthetic.main.fragment_sample_page.*

class SamplePageFragment : CoreFragment() {

    companion object {
        fun build(post: Post, booruContextClass: Class<BooruContext>): SamplePageFragment {
            val fragment = SamplePageFragment()
            fragment.arguments.post = post
            fragment.arguments.booruclass = booruContextClass
            return fragment
        }
    }

    val arguments = Arguments(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = SampleFragmentStateAdapter(this, arguments.post, arguments.booruclass)
        fragment_sample_vertical.adapter = adapter
        fragment_sample_vertical.setCurrentItem(1, false)
    }

    class Arguments(fragment: SamplePageFragment) : FragmentArguments<SamplePageFragment>(fragment) {

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

}
