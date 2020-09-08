package tag

import org.junit.Assert.assertEquals
import org.junit.Test

class XmlDanbooruTagDeserializerTest {

    @Test
    fun `should deserialize single tag xml`() {
        val xml = javaClass.classLoader.getResource("tag.xml")!!.readText()
        val tag = XmlDanbooruTagDeserializer().deserializeTag(xml)

        assertEquals(1593445, tag.tagId)
    }
}
