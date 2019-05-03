package com.makentoshe.booruchan.screen.sampleinfo

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.tag.Tag
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.booruchan.screen.sampleinfo.fragment.SampleInfoFragment

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