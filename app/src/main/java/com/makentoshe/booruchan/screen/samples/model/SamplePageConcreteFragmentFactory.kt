package com.makentoshe.booruchan.screen.samples.model

import com.makentoshe.booruchan.api.Booru
import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.screen.samples.fragment.SamplePageGifFragment
import com.makentoshe.booruchan.screen.samples.fragment.SamplePageImageFragment
import com.makentoshe.booruchan.screen.samples.fragment.SamplePageWebmFragment

class SamplePageConcreteFragmentFactory(private val booru: Booru, private val position: Int) {

    fun buildWebmFragment(post: Post) =
        SamplePageWebmFragment.create(booru, post, position)

    fun buildGifFragment(post: Post) =
        SamplePageGifFragment.create(booru, post)

    fun buildImageFragment(post: Post) =
        SamplePageImageFragment.create(booru, post)
}