package com.makentoshe.booruchan.booru

import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.FragmentScreen

class BooruScreen(
    private val booru: Booru,
    private val tags: HashSet<Tag> = HashSet()
) : FragmentScreen() {

    override val fragment: Fragment
        get() = BooruFragment.create(booru, tags)
}