package com.makentoshe.booruchan.booru.model.gallery

import android.arch.lifecycle.LifecycleOwner

interface GalleryViewModel {

    fun addSearchTermObserver(owner: LifecycleOwner, observer: (String?) -> (Unit))

}