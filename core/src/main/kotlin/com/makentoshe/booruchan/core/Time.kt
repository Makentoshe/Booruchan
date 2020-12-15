package com.makentoshe.booruchan.core

import java.io.Serializable
import java.time.ZonedDateTime

interface Time: Serializable {
    val raw: String
    val time: ZonedDateTime
}

fun time(raw: String): Time {
    return object: Time {
        override val raw = raw
        // todo implement
        override val time = ZonedDateTime.now()
    }
}