package com.makentoshe.boorupostview

import java.io.Serializable

/**
 * Interface for performing navigation inside posts screen
 */
interface PostsFragmentNavigator : Serializable {

    /** Navigate to sample screen */
    fun navigateToSampleFragment(page: Int, position: Int, total: Int)

}