package com.makentoshe.boorupostview

import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Post
import com.makentoshe.boorulibrary.entitiy.Tag
import java.io.Serializable

/**
 * Interface for performing navigation inside posts screen
 */
interface PostsFragmentNavigator : Serializable {

    /** Navigate to sample screen */
    fun navigateToImageFragment(position: Int, booru: Booru, tags: Set<Tag>, post: Post)

}