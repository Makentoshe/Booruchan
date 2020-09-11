package tag

//class JsonGelbooruTagsDeserializer: TagsDeserializer<GelbooruTag> {
//
//    private val jsonDeserializer = Json { isLenient = true }
//
//    override fun deserialize(string: String): Tags<GelbooruTag> {
//        return GelbooruTags(jsonDeserializer.decodeFromString<List<Map<String, String>>>(string).map(GelbooruTag.Companion::create))
//    }
//}
//