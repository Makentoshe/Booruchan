package com.makentoshe.booruchan.booru.content

import com.makentoshe.booruchan.booru.content.view.ContentFragment

interface Content {

    fun createView(contentViewModel: ContentViewModel): ContentFragment
}