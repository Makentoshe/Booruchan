package com.makentoshe.booruchan.boors.gelbooru

import com.makentoshe.booruchan.boors.Boor
import com.makentoshe.booruchan.boors.entity.Post

class Gelbooru: Boor(GelbooruRequestAPI()) {

    override fun getBooruName(): String {
        return "Gelbooru"
    }

    override fun convertLocalTimeToDefault(time: String): String {
        val split = time.split(" ")
        val year = split[5]
        val month = split[1]
        val day = split[2]
        val daytime = split[3]
        return "$day $month $year in $daytime"
    }

//    override fun getAutocompleteSearchVariations(term: String): List<String> = runBlocking {
//        val async = async {
//            HttpRequest.get(getApi().getAutocompleteSearchRequest(term)).stream()
//        }
//        return@runBlocking parseJsonForAutocomplete(
//                Scanner(async.await()).useDelimiter("\\A").next())
//    }

    class Post: com.makentoshe.booruchan.boors.entity.Post() {

        var previewHeight: Int = -1

        var previewWidth: Int = -1

        lateinit var status: String

        var sampleWidth: Int = -1

        lateinit var change: String

        var width: Int = -1

        var hasNotes: Boolean = false

        var sampleHeight: Int = -1

        var height: Int = -1

        var hasChildren: Boolean = false

        lateinit var source: String

        lateinit var md5: String

        var parentId: Int = -1

        override fun fill(attributes: Map<String, String>) {
            super.fill(attributes)
            //create Entry
            val entrySet = attributes.entries
            //for each attribute
            for ((key, value) in entrySet) {
                when (key) {
                    "md5" -> {
                        md5 = value
                    }
                    "source" -> {
                        source = value
                    }
                    "height" -> {
                        height = value.toInt()
                    }
                    "width" -> {
                        width = value.toInt()
                    }
                    "sample_height" -> {
                        sampleHeight = value.toInt()
                    }
                    "sample_width" -> {
                        sampleWidth = value.toInt()
                    }
                    "preview_height" -> {
                        previewHeight = value.toInt()
                    }
                    "preview_width" -> {
                        previewWidth = value.toInt()
                    }
                    "has_notes" -> {
                        hasNotes = value.toBoolean()
                    }
                    "status" -> {
                        status = value
                    }
                    "change" -> {
                        change = value
                    }
                    "has_children" -> {
                        hasChildren = value.toBoolean()
                    }
                    "parent_id" -> {
                        try{
                            parentId = value.toInt()
                        } catch (e: Exception){}
                    }
                }
            }
        }


    }

}