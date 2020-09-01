package post

import Text

interface Tags {
    val tags: Set<Text>
}

fun tags(set: Set<Text>) = object : Tags {
    override val tags = set
}