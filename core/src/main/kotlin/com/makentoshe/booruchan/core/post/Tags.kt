package com.makentoshe.booruchan.core.post

import com.makentoshe.booruchan.core.Text

interface Tags {
    val tags: Set<Text>
}

fun tags(set: Set<Text>) = object : Tags {
    override val tags = set
}