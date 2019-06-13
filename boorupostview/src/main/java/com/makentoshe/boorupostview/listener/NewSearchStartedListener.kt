package com.makentoshe.boorupostview.listener

import com.makentoshe.boorulibrary.entitiy.Tag

interface NewSearchStartedListener {
    fun onNewSearchStarted(action: (Set<Tag>) -> Unit)
}