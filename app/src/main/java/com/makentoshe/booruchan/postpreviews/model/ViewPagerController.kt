package com.makentoshe.booruchan.postpreviews.model

/**
 * Interface for performing [androidx.viewpager.widget.ViewPager] controlling
 */
interface ViewPagerController {
    /** Goes to the next page */
    fun nextPage()

    /** Goes to the previous page */
    fun prevPage()

    /** Goes to the selected [page] */
    fun gotoPage(page: Int)

    /**
     * Add onPageChangedListener where the lambda argument is a new selected page.
     */
    fun onPageChangedListener(action: (Int) -> Unit)
}