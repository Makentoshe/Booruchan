package com.makentoshe.booruchan.model

interface PositionHolder {
    val position: Int
}

class PositionHolderImpl(value: Int): PositionHolder {
    override val position = value
}