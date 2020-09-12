package tag

import org.junit.Assert.assertEquals
import org.junit.Test
import tag.deserialize.JsonDanbooruTagDeserialize
import tag.deserialize.JsonDanbooruTagDeserializer
import tag.network.JsonDanbooruTagResponse

class JsonDanbooruTagDeserializerTest {

    @Test
    fun `should deserialize single tag json`() {
        val json = javaClass.classLoader.getResource("tag.json")!!.readText()
        val deserialize = JsonDanbooruTagDeserializer().deserializeTag(JsonDanbooruTagResponse.Success(json))

        // todo add asserts for all fields
        deserialize as JsonDanbooruTagDeserialize.Success
        assertEquals(1591223, deserialize.tag.tagId)
    }
}