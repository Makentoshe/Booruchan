package com.makentoshe.booruchan.boors.gelbooru

import com.makentoshe.booruchan.boors.Boor

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

}