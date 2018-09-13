package com.makentoshe.booruchan.start.model.api.factory

import com.makentoshe.booruchan.common.api.Boor
import com.makentoshe.booruchan.common.api.gelbooru.Gelbooru

interface Factory {

    fun createServiceInstance(): Boor

    companion object {

        fun createServiceInstance(serviceClass: Class<Boor>): Factory {
            return when (serviceClass) {
                Gelbooru::class.java -> GelbooruFactory()
                else -> throw IllegalArgumentException("Factory for service $serviceClass is not defined")
            }
        }

    }

}