package com.makentoshe.booruchan.network.fuel

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.core.Response
import com.github.kittinunf.result.Result
import com.makentoshe.booruchan.network.HttpResult
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.net.URL

class FuelHttpResult(
    private val response: Triple<Request, Response, Result<ByteArray, FuelError>>
) : HttpResult {

    override val stream: InputStream
        get() = ByteArrayInputStream(response.third.get())

    override val body: String
        get() = String(response.third.get())

    override val code: Int
        get() = response.second.statusCode

    override val message: String
        get() = response.second.responseMessage

    override val url: URL
        get() = response.second.url
}