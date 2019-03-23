package com.makentoshe.booruchan.screen.booru

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Tag
import com.makentoshe.booruchan.navigation.Screen

class BooruScreen(private val booru: Booru) : Screen() {
    override val fragment: Fragment
        get() = BooruFragment.create(booru)
}