package com.makentoshe.booruchan.postpreviews

import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.FragmentScreen
import com.makentoshe.booruchan.booru.model.DrawerController

class PostsScreen(private val data: Arguments) : FragmentScreen() {

    override val fragment: Fragment
        get() = PostsFragment.create(data.booru, data.drawerController, data.tags)

    data class Arguments(val booru: Booru, val drawerController: DrawerController, val tags: Set<Tag>)
}