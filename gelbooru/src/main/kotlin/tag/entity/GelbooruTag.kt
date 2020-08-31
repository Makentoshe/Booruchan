package tag.entity

data class GelbooruTag(
    override val tagId: Int,
    override val text: String,
    override val type: Type,
    /** How many posts tagged by this tag */
    val count: Int?,
    /** Is tag name objective or subjective? For example: cute */
    val ambiguous: Boolean?
) : Tag {

    companion object {
        const val COUNT = "count"
        const val IS_AMBIGUOUS = "ambiguous"
        const val TEXT = "tag"
        const val TAG_ID = "id"
        const val TYPE = "type"

        fun create(map: Map<String, String>): GelbooruTag {
            val tagId = map[TAG_ID]?.toIntOrNull() ?: deserializeError(TAG_ID)
            val text = map[TEXT] ?: deserializeError(TEXT)
            val type = deserializeType(map[TYPE])
            val count = map[COUNT]?.toIntOrNull() ?: deserializeError(COUNT)
            val isAmbiguous = deserializeAmbiguous(map[IS_AMBIGUOUS])
            return GelbooruTag(tagId, text, type, count, isAmbiguous)
        }

        private fun deserializeAmbiguous(string: String?): Boolean {
            if (string == null) deserializeError(IS_AMBIGUOUS)
            return when (string.toIntOrNull()) {
                0 -> false
                1 -> true
                else -> deserializeError(IS_AMBIGUOUS)
            }
        }

        private fun deserializeType(type: String?): Type {
            return Type.UNDEFINED
        }

        private fun deserializeError(param: String): Nothing {
            error("Could not define $param attribute")
        }
    }
}

