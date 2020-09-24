package network

abstract class GelbooruRequest {

    protected val host: String = "https://gelbooru.com"

    abstract val url: String
}
