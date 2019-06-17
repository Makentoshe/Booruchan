package com.makentoshe.booruview

import com.makentoshe.boorulibrary.booru.entity.Booru
import com.makentoshe.boorulibrary.entitiy.Tag
import ru.terrakok.cicerone.NavigatorHolder
import java.io.Serializable

/**
 * Performs local navigation.
 */
interface BooruFragmentNavigator : Serializable, NavigatorHolder {

    /** Replace current screen to the posts */
    fun navigateToPosts(booru: Booru, tags: Set<Tag>)

    /** Replace current screen to the accounts */
    fun navigateToAccount()
}