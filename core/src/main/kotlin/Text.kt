/** Describes entity title or body */
interface Text {
    val text: String
}

fun text(text: String) = object: Text {
    override val text = text
}