package com.makentoshe.booruchan.postpreview

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.FragmentScreen
import com.makentoshe.booruchan.booru.model.DrawerController
import com.makentoshe.booruchan.postpreviews.PostsFragment

class PostsScreen(
    private val booru: Booru,
    private val drawerController: DrawerController,
    private val tags: HashSet<Tag> = HashSet()
) : FragmentScreen() {

    override val fragment: Fragment
        get() = PostsFragment().apply {
            arguments = Bundle().apply {
                putSerializable(Booru::class.java.simpleName, booru)
                putSerializable(DrawerController::class.java.simpleName, drawerController)
                putSerializable(Set::class.java.simpleName + Tag::class.java.simpleName, tags)
            }
        }
}