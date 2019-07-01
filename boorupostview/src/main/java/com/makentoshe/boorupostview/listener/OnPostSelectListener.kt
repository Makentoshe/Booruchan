package com.makentoshe.boorupostview.listener

import com.makentoshe.boorulibrary.entitiy.Post

/** Interface for post click listener in the posts view */
interface OnPostSelectListener {

    /** Int is a post position starts from 0 to posts count per page. Method calls on each click event */
    fun onSelect(action: (Int, Post) -> Unit)
}