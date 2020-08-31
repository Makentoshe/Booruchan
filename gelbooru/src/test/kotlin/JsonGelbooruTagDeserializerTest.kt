import org.junit.Assert.assertEquals
import org.junit.Test
import tag.JsonGelbooruTagDeserializer

class JsonGelbooruTagDeserializerTest {

    @Test
    fun `should deserialize single tag`() {
        val json = javaClass.classLoader.getResource("single_tag.json")!!.readText()
        val tag = JsonGelbooruTagDeserializer().deserialize(json)

        assertEquals(1, tag.tagId)
        assertEquals("asahina_mikuru", tag.text)
        assertEquals(4579, tag.count)
        // todo add category check
        assertEquals(false, tag.ambiguous)
    }
}
