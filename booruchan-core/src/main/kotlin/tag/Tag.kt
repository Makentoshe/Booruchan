package tag

interface Tag: TagId {
    val raw: Map<String, String>
    val body: String
}