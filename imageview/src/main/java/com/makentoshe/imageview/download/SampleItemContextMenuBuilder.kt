package com.makentoshe.imageview.download

import androidx.fragment.app.FragmentManager
import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Post

/** Creates a context menu for a sample item. The menu is a dialog fragment */
class SampleItemContextMenuBuilder(
    private val fragmentManager: FragmentManager,
    private val booru: Booru,
    private val post: Post
) : ContextMenuBuilder {

    override fun show() {
        val fragment = SampleItemDialogMenuFragment.build(booru, post)
        fragment.show(fragmentManager, SampleItemDialogMenuFragment::class.java.simpleName)
    }

}