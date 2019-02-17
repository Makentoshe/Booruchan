package com.makentoshe.booruchan.postsamples

import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruchan.FragmentScreen

class PostSamplesScreen(private val booru: Booru) : FragmentScreen() {
    override val fragment: Fragment
        get() = PostSamplesFragment.create(booru)

}