//package com.makentoshe.booruchan.posttags.model
//
//import com.makentoshe.booruapi.Booru
//import com.makentoshe.booruapi.Tag
//import com.makentoshe.booruchan.booru.BooruScreen
//import ru.terrakok.cicerone.Router
//
//class PostTagsNavigator(private val router: Router, private val booru: Booru) {
//    fun startNewSearch(tags: Set<Tag>) {
//        val screen = BooruScreen(booru, tags)
//        router.backTo(screen)
//        router.replaceScreen(screen)
//    }
//
//    fun back() = router.exit()
//}