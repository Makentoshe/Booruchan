package post

/** Interface contains any scoring information */
interface Score {
    /** The summary score value */
    val score: Int
    /** Up scores (likes) */
    val upScore: Int?
    /** Down scores (dislikes) */
    val downScore: Int?
}

fun score(score: Int) = object: Score {
    override val score = score
    override val downScore: Int? = null
    override val upScore: Int? = null
}

fun score(up: Int, down: Int) = object: Score {
    override val downScore: Int = down
    override val upScore: Int = up
    override val score = up - down
}