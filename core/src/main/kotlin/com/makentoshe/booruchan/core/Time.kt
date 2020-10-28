package com.makentoshe.booruchan.core

import java.time.ZonedDateTime

interface Time {
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