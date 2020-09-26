package tag

import org.junit.Assert.assertEquals
import org.junit.Test
import tag.deserialize.JsonDanbooruTagsDeserializer

class JsonDanbooruTagsDeserializerTest {

    @Test
    fun `should deserialize tags json`() {
        val json = javaClass.classLoader.getResource("tags.json")!!.readText()
        val deserialize = JsonDanbooruTagsDeserializer().deserializeTags(json)

        assertEquals(20, deserialize.deserializes.size)
        assertEquals(20, deserialize.tags.size)
        assertEquals(0, deserialize.failures.size)
    }

    @Test
    fun `should deserialize corrupted tags json`() {
        val json = javaClass.classLoader.getResource("tags-corrupted.json")!!.readText()
        val deserialize = JsonDanbooruTagsDeserializer().deserializeTags(json)

        assertEquals(20, deserialize.deserializes.size)
        assertEquals(18, deserialize.tags.size)
        assertEquals(2, deserialize.failures.size)
    }
}
