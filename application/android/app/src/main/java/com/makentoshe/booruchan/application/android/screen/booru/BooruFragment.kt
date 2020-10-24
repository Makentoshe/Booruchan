package com.makentoshe.booruchan.application.android.screen.booru

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.application.android.R
import context.BooruContext
import kotlinx.android.synthetic.main.fragment_booru.*
import toothpick.ktp.delegate.inject

class BooruFragment : Fragment() {

    companion object {

        fun build(booruContextTitle: String): BooruFragment {
            val fragment = BooruFragment()
            fragment.arguments.booruContextTitle = booruContextTitle
            return fragment
        }

        fun build(booruContext: BooruContext): BooruFragment {
            return build(booruContext.title)
        }
    }

    val arguments = Arguments(this)

    private val booruContext by inject<BooruContext>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_booru, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragment_booru_navigation.selectedItemId = R.id.booru_navigation_posts
    }

    class Arguments(private val booruFragment: BooruFragment) {

        init {
            val fragment = booruFragment as Fragment
            if (fragment.arguments == null) {
                fragment.arguments = Bundle()
            }
        }

        private val fragmentArguments: Bundle
            get() = booruFragment.requireArguments()

        var booruContextTitle: String
            get() = fragmentArguments.getString(TITLE)!!
            set(value) = fragmentArguments.putString(TITLE, value)

        companion object {
            private const val TITLE = "BooruContext#title"
        }
    }
}