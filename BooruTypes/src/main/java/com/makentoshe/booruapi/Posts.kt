package com.makentoshe.booruapi

class Posts(
    posts: List<Post>,
    val count: Int = -1,
    val offset: Int = 0
): ArrayList<Post>(), BooruType  {
    init {
        addAll(posts)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as Posts

        if (count != other.count) return false
        if (offset != other.offset) return false
        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + count
        result = 31 * result + offset
        return result
    }
}