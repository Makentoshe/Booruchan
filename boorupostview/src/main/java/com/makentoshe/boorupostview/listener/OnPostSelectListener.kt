package com.makentoshe.boorupostview.listener

/** Interface for post click listener in the posts view */
interface OnPostSelectListener {

    /** Int is a post position starts from 0 to posts count per page. Method calls on each click event */
    fun onSelect(action: (Int) -> Unit)
}