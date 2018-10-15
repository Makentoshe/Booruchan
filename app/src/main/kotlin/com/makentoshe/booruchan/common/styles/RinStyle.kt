package com.makentoshe.booruchan.common.styles

import android.annotation.SuppressLint
import android.os.Parcelable
import com.makentoshe.booruchan.R
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

@Parcelize
class RinStyle : Style, Parcelable {

    @IgnoredOnParcel
    override val chip: ChipStyle = ChipStyle(
            R.color.MaterialIndigo200, android.R.color.white,
            R.color.MaterialIndigo700, android.R.color.white)

    @IgnoredOnParcel
    override val backdrop = BackdropStyle(R.color.MaterialYellow500, android.R.color.white)
    @IgnoredOnParcel
    override val toolbar = ToolbarStyle(R.color.MaterialYellow500, android.R.color.black)
    @IgnoredOnParcel
    override val view: ViewStyle = ViewStyle(android.R.color.white, android.R.color.black)
    @IgnoredOnParcel
    override val floatingActionButton = FloatingActionButtonStyle(R.color.MaterialYellow500, android.R.color.black)

    override val hintColor: Int
        @SuppressLint("PrivateResource")
        get() = R.color.abc_hint_foreground_material_dark

    override val assentSecondaryColor: Int
        get() = R.color.MaterialYellow200

    override val styleId: Int
        get() = Style.Rin

}