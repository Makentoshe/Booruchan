package com.makentoshe.booruchan.booru.model.content

import com.makentoshe.booruchan.booru.ContentViewModel
import com.makentoshe.booruchan.booru.view.content.ContentFragment

interface Content {

    fun createView(contentViewModel: ContentViewModel): ContentFragment
}