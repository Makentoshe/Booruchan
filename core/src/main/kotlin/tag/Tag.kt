package tag

interface Tag: TagId {
    /** Stores source string */
    val rawString: String
    /** Stores mapped source string */
    val rawMap: Map<String, String>
    /** Mandatory param: tag name */
    val text: String
    /** Mandatory param: tag type */
    val type: Type
}