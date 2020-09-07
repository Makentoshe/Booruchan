package post

import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import post.network.DanbooruPostsResponse
import post.network.XmlDanbooruPostResponse

interface DanbooruPostsDeserializer<Posts : DanbooruPosts<*>> {
    fun deserializePosts(response: DanbooruPostsResponse.Success): Posts
}

class XmlDanbooruPostsDeserializer : DanbooruPostsDeserializer<XmlDanbooruPosts> {

    private val xmlPostDeserializer = XmlDanbooruPostDeserializer()

    override fun deserializePosts(response: DanbooruPostsResponse.Success): XmlDanbooruPosts {
        val jsoup = Jsoup.parse(response.string, "", Parser.xmlParser())
        val posts = jsoup.getElementsByTag("post").map {
            XmlDanbooruPostResponse.Success(it.toString())
        }.map(xmlPostDeserializer::deserializePost)
        return XmlDanbooruPosts(posts)
    }
}
