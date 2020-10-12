package post.network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test
import post.postId

class JsonGelbooruPostNetworkManagerTest {

    @Test
    @Ignore("real api")
    fun `should request json post`() = runBlocking {
        val request = JsonGelbooruPostRequest(GelbooruPostFilter.ById(postId(2)))
        val response = GelbooruPostNetworkManager(HttpClient()).getPost(request)
        println(response)
    }

}