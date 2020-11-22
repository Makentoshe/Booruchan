package com.makentoshe.booruchan.application.android.screen.samples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.makentoshe.booruchan.application.android.fragment.FragmentArguments
import com.makentoshe.booruchan.core.post.Post

class SampleFragment : CoreFragment() {

    companion object {
        fun build(post: Post): SampleFragment {
            val fragment = SampleFragment()
            fragment.arguments.post = post
            return fragment
        }
    }

    val arguments = Arguments(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_sample, container, false)
        return TextView(requireContext()).apply { text = arguments.post.toString() }
    }

    class Arguments(fragment: SampleFragment) : FragmentArguments<SampleFragment>(fragment) {

        var post: Post
            get() = fragmentArguments.getSerializable(POST) as Post
            set(value) = fragmentArguments.putSerializable(POST, value)

        var booruContextTitle: String
            get() = fragmentArguments.getString(TITLE)!!
            set(value) = fragmentArguments.putString(TITLE, value)

        companion object {
            private const val POST = "post"
            private const val TITLE = "BooruContext#title"
        }
    }

}
