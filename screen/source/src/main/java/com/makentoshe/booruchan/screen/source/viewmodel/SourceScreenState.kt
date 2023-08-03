package com.makentoshe.booruchan.screen.source.viewmodel

data class SourceScreenState(val sas: String) {
    companion object {
        val InitialState = SourceScreenState(
            sas = "",
        )
    }
}