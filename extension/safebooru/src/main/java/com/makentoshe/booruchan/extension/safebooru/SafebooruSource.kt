package com.makentoshe.booruchan.extension.safebooru

import com.makentoshe.booruchan.extension.factory.AutoCompleteTagFactory
import com.makentoshe.booruchan.extension.BooruContext
import com.makentoshe.booruchan.extension.BooruSource
import com.makentoshe.booruchan.extension.safebooru.factory.SafebooruAutoCompleteTagFactory
import com.makentoshe.booruchan.extension.safebooru.factory.SafebooruPostSearchFactory
import com.makentoshe.booruchan.extension.safebooru.parser.SafebooruAutoCompleteTagJsonParser
import com.makentoshe.booruchan.extension.safebooru.parser.SafebooruPostSearchJsonParser
import com.makentoshe.booruchan.feature.context.BooruSystem

class SafebooruSource : BooruSource {

    override val id: String
        get() = "safebooru"

    override val context = BooruContext(
        title = "Safebooru",
        system = BooruSystem.Gelbooru020System,
        host = "https://safebooru.org",
    )

    override val postSearchFactory
        get() = SafebooruPostSearchFactory(context = context, parser = SafebooruPostSearchJsonParser())

    override val autoCompleteTagFactory: AutoCompleteTagFactory
        get() = SafebooruAutoCompleteTagFactory(context = context, parser = SafebooruAutoCompleteTagJsonParser())

}
