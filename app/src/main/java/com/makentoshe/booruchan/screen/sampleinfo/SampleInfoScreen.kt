package com.makentoshe.booruchan.screen.sampleinfo

import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Post
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.navigation.Screen

class SampleInfoScreen(
    private val itemId: Int,
    private val booru: Booru,
    private val tags: Set<Tag>,
    private val position: Int
) : Screen() {
    override val fragment: Fragment
        get() = SampleInfoFragment.create(itemId, booru, tags, position)
}

//
//class Transformer : ViewPager.PageTransformer {
//    override fun transformPage(page: View, position: Float) {
//        if (position >= -1 && position <= 1) {
//            page.findViewById<View>(R.id.background).translationX = -position * page.width / 2
//        } else {
//            page.alpha = 1f
//        }
//    }
//}