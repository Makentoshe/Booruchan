package com.makentoshe.booruchan.screen.samples.controller

import androidx.fragment.app.Fragment
import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.screen.samples.fragment.SampleOptionFragment

class SampleOptionsController(
    private val booru: Booru,
    private val post: Post
) {
    fun show(fragment: Fragment) {
        val fragmentManager = fragment.childFragmentManager
        SampleOptionFragment.create(booru, post)
            .show(fragmentManager, SampleOptionFragment::class.java.simpleName)
    }
}