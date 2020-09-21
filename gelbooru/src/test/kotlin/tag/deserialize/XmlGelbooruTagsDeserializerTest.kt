package tag.deserialize

import org.junit.Assert.assertEquals
import org.junit.Test
import tag.network.XmlGelbooruTagsResponse

class XmlGelbooruTagsDeserializerTest {

    @Test
    fun `should deserialize tags`() {
        val xml = javaClass.classLoader.getResource("tags.xml")!!.readText()
        val deserialize = XmlGelbooruTagsDeserializer().deserializeTags(XmlGelbooruTagsResponse.Success(xml))

        assertEquals(20, deserialize.deserializes.size)
        assertEquals(20, deserialize.tags.size)
        assertEquals(0, deserialize.failures.size)
    }

    @Test
    fun `should deserialize corrupted tags`() {
        val xml = javaClass.classLoader.getResource("tags-corrupted.xml")!!.readText()
        val deserialize = XmlGelbooruTagsDeserializer().deserializeTags(XmlGelbooruTagsResponse.Success(xml))

        assertEquals(20, deserialize.deserializes.size)
        assertEquals(18, deserialize.tags.size)
        assertEquals(2, deserialize.failures.size)
    }
}