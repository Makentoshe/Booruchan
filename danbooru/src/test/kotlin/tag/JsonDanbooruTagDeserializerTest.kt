package tag

import org.junit.Assert
import org.junit.Test

class JsonDanbooruTagDeserializerTest {

    @Test
    fun `should deserialize single tag instance`() {
        val json = javaClass.classLoader.getResource("single_tag.json")!!.readText()
        val tag = JsonDanbooruTagDeserializer().deserialize(json)

        Assert.assertEquals(1591223, tag.tagId)
        Assert.assertEquals("linnne", tag.text)
        Assert.assertEquals(2, tag.count)
        // todo add category check
        Assert.assertEquals("2020-08-30T14:42:57.567-04:00", tag.creationTime.raw)
        Assert.assertEquals("2020-08-30T15:35:42.180-04:00", tag.updationTime.raw)
        Assert.assertEquals(false, tag.isLocked)
    }
}