package com.makentoshe.booruchan.postpreviews.model

import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.postpreviews.model.DelayAutocompleteAdapter

interface AdapterBuilder {
    /**
     * Method returns the [DelayAutocompleteAdapter] which must autocomplete the [CharSequence] requests.
     */
    fun getAutocompleteAdapter(): DelayAutocompleteAdapter

    /**
     * Method returns a [PagerAdapter] for paging the [androidx.fragment.app.Fragment]s.
     *
     * @param fragmentManager is a [FragmentManager] default param for creating [PagerAdapter] instance.
     * @param tags is a [Set] of the [Tag]s used for the searching into the pages.
     * @return a [PagerAdapter] instance for paging the [androidx.fragment.app.Fragment]s
     */
    fun getViewPagerAdapter(fragmentManager: FragmentManager, tags: Set<Tag>): PagerAdapter
}