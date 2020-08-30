import org.junit.Assert.assertEquals
import org.junit.Test
import tag.XmlGelbooruTagsFormatter

internal class XmlGelbooruTagsFormatterTest {

    @Test
    fun `should deserialize single tag instance`() {
        val count = 829873
        val name = "skirt"
        val ambiguous = false
        val id = 107
        val type = 0
        val xml = "<tag type=\"$type\" count=\"$count\" name=\"$name\" ambiguous=\"$ambiguous\" id=\"$id\"/>\n"

        val tag = XmlGelbooruTagsFormatter().deserialize(xml).tags.first()

        // fixme add type check
        assertEquals(count, tag.count)
        assertEquals(name, tag.text)
        assertEquals(ambiguous, tag.ambiguous)
        assertEquals(id, tag.tagId)
    }

    @Test
    fun `should deserialize list of tags`() {
        val xml = """
            <tags type="array">
            <tag type="1" count="124" name="raikageart" ambiguous="false" id="948490"/>
            <tag type="0" count="45444" name="trap" ambiguous="false" id="450"/>
            </tags>
        """.trimIndent()

        val tags = XmlGelbooruTagsFormatter().deserialize(xml).tags

        assertEquals(2, tags.size)
    }
}