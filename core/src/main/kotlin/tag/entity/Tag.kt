package tag.entity

interface Tag: TagId {
    /** Mandatory param: tag name */
    val text: String
    /** Mandatory param: tag type */
    val type: Type
}