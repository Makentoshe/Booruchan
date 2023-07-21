package com.makentoshe.booruchan.screen.boorucontent.domain

data class SearchTagUi(
    val title: String,
    val value: String,
    val category: SearchTagCategory = SearchTagCategory.General,
)

sealed interface SearchTagCategory {
    object General: SearchTagCategory
    object Metadata: SearchTagCategory
    object Character: SearchTagCategory
    object Artist: SearchTagCategory
    object Copyright: SearchTagCategory
}