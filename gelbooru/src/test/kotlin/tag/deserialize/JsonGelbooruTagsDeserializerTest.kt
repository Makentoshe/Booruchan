package tag.deserialize

import deserialize.DeserializeException
import org.junit.Assert.assertEquals
import org.junit.Test

class JsonGelbooruTagsDeserializerTest {

    @Test
    fun `should deserialize tags json`() {
        val json = javaClass.classLoader.getResource("tags.json")!!.readText()
        val deserialize = JsonGelbooruTagsDeserializer().deserializeTags(json)
        val successDeserialize = deserialize.getOrNull()!!

        assertEquals(20, successDeserialize.deserializes.size)
        assertEquals(20, successDeserialize.tags.size)
        assertEquals(0, successDeserialize.failures.size)
    }

    @Test
    fun `should deserialize corrupted tags json`() {
        val json = javaClass.classLoader.getResource("tags-corrupted.json")!!.readText()
        val deserialize = JsonGelbooruTagsDeserializer().deserializeTags(json)
        val successDeserialize = deserialize.getOrNull()!!

        assertEquals(20, successDeserialize.deserializes.size)
        assertEquals(18, successDeserialize.tags.size)
        assertEquals(2, successDeserialize.failures.size)
    }

    @Test(expected = DeserializeException::class)
    fun `should return empty collection on invalid json`() {
        val json = "invalid json"
        val deserialize = JsonGelbooruTagsDeserializer().deserializeTags(json)
        throw deserialize.exceptionOrNull()!!
    }
}
