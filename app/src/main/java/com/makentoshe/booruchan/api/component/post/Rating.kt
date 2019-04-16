package com.makentoshe.booruchan.api.component.post

enum class Rating {
    SAFE, QUESTIONABLE, EXPLICIT, UNSPECIFIED;

    companion object {
        fun parseRating(str: String?): Rating {
            return when (str) {
                "s" -> SAFE
                "q" -> QUESTIONABLE
                "e" -> EXPLICIT
                else -> UNSPECIFIED
            }
        }
    }
}