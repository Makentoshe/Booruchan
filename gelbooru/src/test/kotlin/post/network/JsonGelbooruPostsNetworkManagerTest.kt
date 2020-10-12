package post.network

import io.ktor.client.*
import kotlinx.coroutines.runBlocking
import org.junit.Ignore
import org.junit.Test

class JsonGelbooruPostsNetworkManagerTest {

    @Test
    @Ignore("real api")
    fun `should request json posts`() = runBlocking {
        val request = JsonGelbooruPostsRequest(GelbooruPostsFilter(count = 20))
        val response = GelbooruPostsNetworkManager(HttpClient()).getPosts(request)
        println(response)
    }
}