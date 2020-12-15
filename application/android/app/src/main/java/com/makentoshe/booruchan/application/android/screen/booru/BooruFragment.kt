package com.makentoshe.booruchan.application.android.screen.booru

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.makentoshe.booruchan.application.android.R
import com.makentoshe.booruchan.application.android.fragment.CoreFragment
import com.makentoshe.booruchan.application.android.fragment.FragmentArguments
import com.makentoshe.booruchan.application.android.screen.booru.navigation.BooruNavigation
import com.makentoshe.booruchan.core.context.BooruContext
import kotlinx.android.synthetic.main.fragment_booru.*
import toothpick.ktp.delegate.inject

class BooruFragment : CoreFragment() {

    companion object {

        fun build(booruclass: Class<BooruContext>): BooruFragment {
            val fragment = BooruFragment()
            fragment.arguments.booruclass = booruclass
            return fragment
        }

        fun build(booruContext: BooruContext): BooruFragment {
            return build(booruContext.javaClass)
        }
    }

    val arguments = Arguments(this)
    private val navigation by inject<BooruNavigation>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_booru, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragment_booru_navigation.setOnNavigationItemSelectedListener(::onBottomNavigationListener)
        fragment_booru_navigation.selectedItemId = R.id.booru_navigation_posts
    }

    private fun onBottomNavigationListener(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.booru_navigation_account -> navigation.navigateToAccountScreen()
            R.id.booru_navigation_posts -> navigation.navigateToPostsScreen()
            R.id.booru_navigation_menu -> navigation.navigateToMenuScreen()
            else -> throw NoSuchElementException(item.toString())
        }
        return true
    }

    class Arguments(booruFragment: BooruFragment): FragmentArguments(booruFragment) {

        var booruclass: Class<BooruContext>
            get() = fragmentArguments.getSerializable(CLASS) as Class<BooruContext>
            set(value) = fragmentArguments.putSerializable(CLASS, value)

        companion object {
            private const val CLASS = "class"
        }
    }
}
