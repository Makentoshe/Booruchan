package com.makentoshe.booruchan.common.styles

import android.annotation.SuppressLint
import android.os.Parcelable
import com.makentoshe.booruchan.R
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize


@Parcelize
class AstarteStyle: Style, Parcelable {
    @IgnoredOnParcel
    override val backdrop = BackdropStyle(R.color.MaterialIndigo500, android.R.color.white)

    @IgnoredOnParcel
    override val toolbar = ToolbarStyle(R.color.MaterialIndigo500, android.R.color.white)

    @IgnoredOnParcel
    override val view: ViewStyle = ViewStyle(android.R.color.white, android.R.color.black)

    @IgnoredOnParcel
    override val floatingActionButton = FloatingActionButtonStyle(R.color.MaterialIndigo500, android.R.color.white)

    override val hintColor: Int
        @SuppressLint("PrivateResource")
        get() = R.color.abc_hint_foreground_material_dark

    override val assentSecondaryColor: Int
        get() = R.color.MaterialIndigo200

    override val styleId: Int
        get() = Style.Astarte

}