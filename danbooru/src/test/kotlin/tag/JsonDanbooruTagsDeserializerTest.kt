package tag

import org.junit.Assert.assertEquals
import org.junit.Test
import tag.deserialize.JsonDanbooruTagsDeserializer
import tag.network.JsonDanbooruTagsResponse

class JsonDanbooruTagsDeserializerTest {

    @Test
    fun `should deserialize tags json`() {
        val json = javaClass.classLoader.getResource("tags.json")!!.readText()
        val tags = JsonDanbooruTagsDeserializer().deserializeTags(JsonDanbooruTagsResponse.Success(json))

        assertEquals(tags.tags.size, 20)
    }
}
