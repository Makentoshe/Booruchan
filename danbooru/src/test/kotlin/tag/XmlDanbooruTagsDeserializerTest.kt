package tag

import org.junit.Assert
import org.junit.Test

class XmlDanbooruTagsDeserializerTest {

    @Test
    fun `should deserialize tags xml`() {
        val xml = javaClass.classLoader.getResource("tags.xml")!!.readText()
        val tags = XmlDanbooruTagsDeserializer().deserializeTags(xml)

        Assert.assertEquals(tags.tags.size, 20)
    }
}