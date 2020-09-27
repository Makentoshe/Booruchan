package tag

import Text

interface Tag: TagId, Text {
    /** Mandatory param: tag type */
    val type: Type
}