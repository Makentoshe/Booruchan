package post.network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test
import post.postId

class XmlGelbooruPostNetworkManagerTest {

    @Test
    @Ignore("real api")
    fun `should request xml post`() = runBlocking {
        val request = XmlGelbooruPostRequest(GelbooruPostFilter.ById(postId(1)))
        val response = GelbooruPostNetworkManager(HttpClient()).getPost(request)
        println(response)
    }
}

