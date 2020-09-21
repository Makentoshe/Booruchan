package tag.deserialize

import org.junit.Assert.assertEquals
import org.junit.Test
import tag.network.JsonGelbooruTagResponse

class JsonGelbooruTagDeserializerTest {

    @Test
    fun `should deserialize tag`() {
        val json = javaClass.classLoader.getResource("tag.json")!!.readText()
        val deserialize = JsonGelbooruTagDeserializer().deserializeTag(JsonGelbooruTagResponse.Success(json))
        val successDeserialize = deserialize as JsonGelbooruTagDeserialize.Success

        // todo add asserts for all fields
        assertEquals(13710, successDeserialize.tag.tagId)
    }

    @Test
    fun `should deserialize variant tag`() {
        val json = javaClass.classLoader.getResource("tag-variant.json")!!.readText()
        val deserialize = JsonGelbooruTagDeserializer().deserializeTag(JsonGelbooruTagResponse.Success(json))
        val successDeserialize = deserialize as JsonGelbooruTagDeserialize.Success

        // todo add asserts for all fields
        assertEquals(13710, successDeserialize.tag.tagId)
    }

    @Test
    fun `should deserialize corrupted tag`() {
        val json = javaClass.classLoader.getResource("tag-corrupted.json")!!.readText()
        val deserialize = JsonGelbooruTagDeserializer().deserializeTag(JsonGelbooruTagResponse.Success(json))
        val failureDeserialize = deserialize as JsonGelbooruTagDeserialize.Failure

        assertEquals(5, failureDeserialize.raw.size)
    }
}