package tag.entity

import Time
import time

data class DanbooruTag(
    override val tagId: Int,
    override val text: String,
    override val type: Type,
    val creationTime: Time,
    val updationTime: Time,
    val isLocked: Boolean,
    val count: Int
) : Tag {

    companion object {
        const val TAG_ID = "id"
        const val TEXT = "name"
        const val COUNT = "post_count"
        const val TYPE = "category"
        const val CREATION_TIME = "created_at"
        const val UPDATION_TIME = "updated_at"
        const val IS_LOCKED = "is_locked"

        fun create(map: Map<String, String>): DanbooruTag {
            val tagId = map[TAG_ID]?.toIntOrNull() ?: deserializeError(TAG_ID)
            val text = map[TEXT] ?: deserializeError(TEXT)
            val type = deserializeType(map[TYPE])
            val creationTime = map[CREATION_TIME]?.let(::time) ?: deserializeError(CREATION_TIME)
            val updationTime = map[UPDATION_TIME]?.let(::time) ?: deserializeError(UPDATION_TIME)
            val count = map[COUNT]?.toIntOrNull() ?: deserializeError(COUNT)
            val isLocked = map[IS_LOCKED]?.let { it.toBoolean() } ?: deserializeError(IS_LOCKED)
            return DanbooruTag(tagId, text, type, creationTime, updationTime, isLocked, count)
        }

        private fun deserializeType(type: String?): Type {
            return Type.UNDEFINED
        }

        private fun deserializeError(param: String): Nothing {
            error("Could not define $param attribute")
        }
    }
}
