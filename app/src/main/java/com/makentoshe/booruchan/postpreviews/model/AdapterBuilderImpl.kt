package com.makentoshe.booruchan.postpreviews.model

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.repository.*

class AdapterBuilderImpl(private val booru: Booru) : AdapterBuilder {

    /* Repository which performs autocompletion a CharSequence with the possible variations contains in the list */
    private val autocompleteRepository = DelayAutocompleteRepository(booru)

    override fun getAutocompleteAdapter() =
        DelayAutocompleteAdapter(autocompleteRepository)

    override fun getViewPagerAdapter(fragmentManager: FragmentManager, tags: Set<Tag>): PagerAdapter {
        return ViewPagerAdapter(fragmentManager, booru, tags)
    }
}