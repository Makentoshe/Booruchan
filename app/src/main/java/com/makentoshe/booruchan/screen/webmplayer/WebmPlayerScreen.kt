package com.makentoshe.booruchan.screen.webmplayer

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.Post
import com.makentoshe.booruchan.navigation.Screen

class WebmPlayerScreen(
    private val booru: Booru,
    private val post: Post
) : Screen() {

    override val fragment: Fragment
        get() = WebmPlayerFragment.create(booru, post)
}