package com.makentoshe.booruchan.feature.healthcheck

import com.makentoshe.booruchan.feature.NetworkRepository
import com.makentoshe.booruchan.feature.NetworkRequest
import javax.inject.Inject

class HealthcheckUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
) {

    suspend operator fun invoke(networkRequest: NetworkRequest): Boolean {
        val networkResponse = networkRepository.execute(networkRequest)

        return networkResponse.statusCode / 100 == 2
    }
}