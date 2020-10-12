package post.deserialize

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import deserialize.EntityDeserializeException
import org.jsoup.Jsoup
import org.jsoup.parser.Parser
import post.XmlGelbooruPost

interface GelbooruPostDeserializer {
    fun deserializePost(string: String): Result<GelbooruPostDeserialize<*>>
}

class XmlGelbooruPostDeserializer : GelbooruPostDeserializer {

    private val mapper = XmlMapper()

    init {
        mapper.registerModules(KotlinModule(), JacksonXmlModule())
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    }

    override fun deserializePost(string: String): Result<XmlGelbooruPostDeserialize> {
        val jsoup = Jsoup.parse(string, "", Parser.xmlParser())
        return try {
            val post = mapper.readValue<XmlGelbooruPost>(jsoup.children().toString())
            Result.success(GelbooruPostDeserialize(post))
        } catch (exception: Exception) {
            val map = mapper.readValue<Map<String, Any?>>(jsoup.children().toString())
            Result.failure(EntityDeserializeException(map, exception))
        }
    }
}

class JsonGelbooruPostDeserializer : GelbooruPostDeserializer {

    private val mapper = JsonMapper()

    override fun deserializePost(string: String): Result<JsonGelbooruPostDeserialize> {
        return try {
            Result.success(GelbooruPostDeserialize(mapper.readValue(string)))
        } catch (exception: Exception) {
            Result.failure(EntityDeserializeException(mapper.readValue(string), exception))
        }
    }
}
