import org.junit.Assert
import org.junit.Test
import tag.JsonGelbooruTagsDeserializer

class JsonGelbooruTagsDeserializerTest {

    @Test
    fun `should deserialize list of tags`() {
        val json = javaClass.classLoader.getResource("tags.json")!!.readText()
        val tags = JsonGelbooruTagsDeserializer().deserialize(json)

        Assert.assertEquals(tags.tags.size, 100)
    }
}