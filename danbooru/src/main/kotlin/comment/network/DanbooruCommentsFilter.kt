package comment.network

class DanbooruCommentsFilter(params: Map<String, Any>) : CommentsFilter(params){

    constructor(count: Int?) : this(buildMap(count))

    companion object {
        private const val COUNT = "limit"

        private fun buildMap(count: Int?): Map<String, Any> {
            val params = HashMap<String, Any>()
            // required param else the posts will be returned.
            params["group_by"] = "comment"
            if (count != null) params[COUNT] = count
            return params
        }
    }
}
