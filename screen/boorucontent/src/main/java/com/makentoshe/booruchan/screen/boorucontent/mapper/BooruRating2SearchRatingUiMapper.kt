package com.makentoshe.booruchan.screen.boorucontent.mapper

import com.makentoshe.booruchan.extension.factory.BooruPostSearchFactory
import com.makentoshe.booruchan.screen.boorucontent.domain.SearchRatingUi
import javax.inject.Inject

class BooruRating2SearchRatingUiMapper @Inject constructor() {
    fun map(rating: BooruPostSearchFactory.Rating) = SearchRatingUi(
        title = rating.value,
    )
}