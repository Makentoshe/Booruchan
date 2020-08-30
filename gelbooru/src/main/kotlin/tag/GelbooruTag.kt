package tag

data class GelbooruTag(
    override val rawString: String,
    override val rawMap: Map<String, String>,
    override val text: String,
    override val tagId: Int,
    override val type: Type,
    /** How many posts tagged by this tag */
    val count: Int?,
    /** Is tag name objective or subjective? For example: cute */
    val ambiguous: Boolean?
) : Tag {

    companion object {
        const val COUNT = "count"
        const val AMBIGUOUS = "ambiguous"
        const val TEXT = "name"
        const val TAG_ID = "id"
        const val TYPE = "type"
    }
}

