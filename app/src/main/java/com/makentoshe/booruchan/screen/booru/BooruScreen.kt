package com.makentoshe.booruchan.screen.booru

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.navigation.Screen
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import com.makentoshe.booruview.BooruFragment

class BooruScreen(
    private val booru: Booru,
    private val tags: Set<Tag>
) : Screen() {
    override val fragment: Fragment
        get() = BooruFragment.build(booru, tags)
}