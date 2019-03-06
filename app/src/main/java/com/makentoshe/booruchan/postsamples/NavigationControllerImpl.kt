package com.makentoshe.booruchan.postsamples

import android.view.MenuItem
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.R
import com.makentoshe.booruchan.postinfo.PostInfoScreen
import com.makentoshe.booruchan.postsamples.model.NavigationController
import com.makentoshe.booruchan.posttags.PostTagsFragmentScreen
import ru.terrakok.cicerone.Router

class NavigationControllerImpl(
    private val router: Router,
    private val booru: Booru,
    private val position: Int,
    private val tags: Set<Tag>
) : NavigationController {

    override fun exit() = router.exit()

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.postsamples_bottombar_tagsitem -> {
                val screen = PostTagsFragmentScreen(booru, tags, position)
                router.navigateTo(screen)
                return true
            }
            R.id.postsamples_bottombar_infoitem -> {
                val screen = PostInfoScreen(booru, tags, position)
                router.navigateTo(screen)
                return true
            }
            R.id.postsamples_bottombar_commentsitem -> {

                return true
            }
        }
        return false
    }

}