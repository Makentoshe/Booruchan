package com.makentoshe.booruchan.core.tag

import java.io.Serializable

interface TagId: Serializable {
    /** Mandatory param: tag id */
    val tagId: Int
}

fun tagId(id: Int) = object : TagId {
    override val tagId = id
}