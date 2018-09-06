package com.makentoshe.booruchan

import com.makentoshe.booruchan.styles.Style
import org.jetbrains.anko.AnkoComponent

abstract class StyleableAnkoComponent<T>(protected val style: Style): AnkoComponent<T>