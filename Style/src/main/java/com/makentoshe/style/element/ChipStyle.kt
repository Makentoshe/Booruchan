package com.makentoshe.style.element

import androidx.annotation.ColorRes

class ChipStyle(@ColorRes primaryColorRes: Int,
                @ColorRes onPrimaryColorRes: Int,
                @ColorRes secondaryColorRes: Int,
                @ColorRes onSecondaryColorRes: Int)
    : ViewStyle(primaryColorRes, onPrimaryColorRes, secondaryColorRes, onSecondaryColorRes)