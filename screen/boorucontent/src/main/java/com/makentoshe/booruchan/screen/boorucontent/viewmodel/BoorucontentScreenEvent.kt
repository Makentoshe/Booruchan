package com.makentoshe.booruchan.screen.boorucontent.viewmodel

import com.makentoshe.booruchan.screen.boorucontent.domain.SearchTagUi

sealed interface BoorucontentScreenEvent {
    data class Initialize(val booruSourceId: String): BoorucontentScreenEvent

    object Search: BoorucontentScreenEvent

    data class AutoCompleteTag(val autocompleteQuery: String): BoorucontentScreenEvent

    data class AddSearchTag(val tag: String, val index: Int): BoorucontentScreenEvent

    data class RemoveSearchTag(val tag: SearchTagUi): BoorucontentScreenEvent

    object NavigationBack: BoorucontentScreenEvent
}