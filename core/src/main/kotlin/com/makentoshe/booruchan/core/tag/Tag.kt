package com.makentoshe.booruchan.core.tag

import com.makentoshe.booruchan.core.Text

interface Tag: TagId, Text {
    /** Mandatory param: tag type */
    val type: Type
}