package tag

import org.junit.Assert.assertEquals
import org.junit.Test
import tag.network.XmlDanbooruTagResponse

class XmlDanbooruTagDeserializerTest {

    @Test
    fun `should deserialize single tag xml`() {
        val xml = javaClass.classLoader.getResource("tag.xml")!!.readText()
        val tag = XmlDanbooruTagDeserializer().deserializeTag(XmlDanbooruTagResponse.Success(xml))

        assertEquals(1593445, tag.tagId)
    }
}
