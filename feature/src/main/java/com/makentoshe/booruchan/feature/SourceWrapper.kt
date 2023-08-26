package com.makentoshe.booruchan.feature

import com.makentoshe.booruchan.extension.base.Source
import com.makentoshe.booruchan.extension.base.factory.FetchPostsFactory
import com.makentoshe.booruchan.extension.base.factory.HealthCheckFactory

class SourceWrapper(private val source: Source) : Source {
    override val host: String
        get() = source.host

    override val id: String
        get() = source.id

    override val title: String
        get() = source.title

    override val healthCheckFactory: HealthCheckFactory?
        get() = tryOrNull { source.healthCheckFactory }

    override val fetchPostsFactory: FetchPostsFactory?
        get() = tryOrNull { source.fetchPostsFactory }

    /** If source doesn't implemented method this method allows to avoid AME error */
    private fun <T> tryOrNull(action: () -> T?): T? = try {
        action()
    } catch (error: AbstractMethodError) {
        null
    }
}