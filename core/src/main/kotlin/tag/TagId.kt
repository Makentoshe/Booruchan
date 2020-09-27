package tag

interface TagId {
    /** Mandatory param: tag id */
    val tagId: Int
}

fun tagId(id: Int) = object : TagId {
    override val tagId = id
}