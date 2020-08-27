package tag

interface Tag: TagId {
    val raw: Map<String, String>
    val text: String
}