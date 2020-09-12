package tag

import org.junit.Assert.assertEquals
import org.junit.Test
import tag.deserialize.XmlDanbooruTagDeserialize
import tag.deserialize.XmlDanbooruTagDeserializer
import tag.network.XmlDanbooruTagResponse

class XmlDanbooruTagDeserializerTest {

    @Test
    fun `should deserialize single tag xml`() {
        val xml = javaClass.classLoader.getResource("tag.xml")!!.readText()
        val deserialize = XmlDanbooruTagDeserializer().deserializeTag(XmlDanbooruTagResponse.Success(xml))

        // todo add asserts for all fields
        deserialize as XmlDanbooruTagDeserialize.Success
        assertEquals(1593445, deserialize.tag.tagId)
    }
}
