package com.makentoshe.booruchan.feature

sealed interface NetworkMethod {
    object Get: NetworkMethod
    object Head: NetworkMethod
}