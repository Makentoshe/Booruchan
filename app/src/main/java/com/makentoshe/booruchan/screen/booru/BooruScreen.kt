package com.makentoshe.booruchan.screen.booru

import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.navigation.Screen

class BooruScreen(
    private val booru: Booru,
    private val tags: Set<Tag>
) : Screen() {
    override val fragment: Fragment
        get() = BooruFragment.create(booru, tags)
}