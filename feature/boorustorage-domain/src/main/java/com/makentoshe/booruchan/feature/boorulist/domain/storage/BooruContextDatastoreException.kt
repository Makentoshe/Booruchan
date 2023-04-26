package com.makentoshe.booruchan.feature.boorulist.domain.storage

sealed class BooruContextDatastoreException : Exception() {
    data class IdentifierAlreadyExists(val string: String) : BooruContextDatastoreException()
    data class IdentifierNotFound(val string: String) : BooruContextDatastoreException()
}