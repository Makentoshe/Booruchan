package com.makentoshe.booruchan.common.api.factory

import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru

interface Factory {

    fun createService(): Boor

    companion object {

        fun createFactory(serviceName: String): Factory {
            return when (serviceName) {
                Gelbooru::class.java.simpleName -> GelbooruFactory()
                else -> throw IllegalArgumentException("Factory for service $serviceName is not defined")
            }
        }

        fun createFactory(serviceClass: Class<Boor>): Factory {
            return when (serviceClass) {
                Gelbooru::class.java -> GelbooruFactory()
                else -> throw IllegalArgumentException("Factory for service $serviceClass is not defined")
            }
        }
    }
}