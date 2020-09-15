package tag

import org.junit.Assert.assertEquals
import org.junit.Test
import tag.deserialize.XmlDanbooruTagsDeserializer
import tag.network.XmlDanbooruTagsResponse

class XmlDanbooruTagsDeserializerTest {

    @Test
    fun `should deserialize tags xml`() {
        val xml = javaClass.classLoader.getResource("tags.xml")!!.readText()
        val deserialize = XmlDanbooruTagsDeserializer().deserializeTags(XmlDanbooruTagsResponse.Success(xml))

        assertEquals(20, deserialize.deserializes.size)
        assertEquals(20, deserialize.tags.size)
        assertEquals(0, deserialize.failures.size)
    }

    @Test
    fun `should deserialize corrupted tags xml`() {
        val xml = javaClass.classLoader.getResource("tags-corrupted.xml")!!.readText()
        val deserialize = XmlDanbooruTagsDeserializer().deserializeTags(XmlDanbooruTagsResponse.Success(xml))

        assertEquals(20, deserialize.deserializes.size)
        assertEquals(18, deserialize.tags.size)
        assertEquals(2, deserialize.failures.size)
    }
}