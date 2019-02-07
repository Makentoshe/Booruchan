package com.makentoshe.booruchan.booru

import android.os.Bundle
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.Fragment
import com.makentoshe.booruchan.FragmentScreen

class BooruScreen(
    private val booru: Booru,
    private val tags: HashSet<Tag> = HashSet()
) : FragmentScreen() {
    override val fragment: Fragment<*>
        get() = BooruFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Booru::class.java.simpleName, booru)
                putSerializable(Tag::class.java.simpleName, tags)
            }
        }
}