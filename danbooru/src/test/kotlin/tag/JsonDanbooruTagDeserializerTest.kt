package tag

import org.junit.Assert.assertEquals
import org.junit.Test
import tag.deserialize.JsonDanbooruTagDeserialize
import tag.deserialize.JsonDanbooruTagDeserializer

class JsonDanbooruTagDeserializerTest {

    @Test
    fun `should deserialize single tag json`() {
        val json = javaClass.classLoader.getResource("tag.json")!!.readText()
        val deserialize = JsonDanbooruTagDeserializer().deserializeTag(json)
        val successDeserialize = deserialize as JsonDanbooruTagDeserialize.Success

        // todo add asserts for all fields
        assertEquals(1591223, successDeserialize.tag.tagId)
    }

    @Test
    fun `should deserialize corrupted tag json`() {
        val json = javaClass.classLoader.getResource("tag-corrupted.json")!!.readText()
        val deserialize = JsonDanbooruTagDeserializer().deserializeTag(json)
        val failureDeserialize = deserialize as JsonDanbooruTagDeserialize.Failure

        assertEquals(7, failureDeserialize.raw.size)
    }
}