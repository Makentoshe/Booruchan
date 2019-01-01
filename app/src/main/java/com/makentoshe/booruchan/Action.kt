package com.makentoshe.booruchan

sealed class Action {
    sealed class UIAction : Action() {
        object OverflowClick : UIAction()
        object MenuClick : UIAction()
    }
}
