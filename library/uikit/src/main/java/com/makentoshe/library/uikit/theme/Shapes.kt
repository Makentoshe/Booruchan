package com.makentoshe.library.uikit.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable


val DefaultBooruchanShapesScheme = BooruchanShapeScheme(
    backdrop = RoundedCornerShape(0)
)

@Immutable
data class BooruchanShapeScheme(
    val backdrop: CornerBasedShape,
)
