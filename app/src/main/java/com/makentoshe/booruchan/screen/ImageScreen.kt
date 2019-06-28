package com.makentoshe.booruchan.screen

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.navigation.Router
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.booruimageview.BooruImageScreenNavigator
import com.makentoshe.booruimageview.ImageFragment
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import java.io.Serializable

/** Class describes a booru image screen */
class ImageScreen(
    private val navigator: BooruImageScreenNavigator,
    private val position : Int,
    private val booru: Booru,
    private val tags: Set<Tag>
) : Screen() {
    override val fragment: Fragment
        get() = ImageFragment.build(navigator, position, booru, tags)
}

/**
 * Navigator component performs navigation from [ImageScreen].
 */
class BooruImageScreenNavigator(private val router: Router): BooruImageScreenNavigator, Serializable {

    /** Close current screen (return to previous) */
    override fun close() = router.exit()
}
