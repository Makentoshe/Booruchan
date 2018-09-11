package com.makentoshe.booruchan.booru.presenter

import com.makentoshe.booruchan.api.Boor
import com.makentoshe.booruchan.booru.model.AutocompleteAdapter
import com.makentoshe.booruchan.booru.model.Extractor
import com.makentoshe.booruchan.booru.view.BooruView

class BooruPresenter(private val view: BooruView) {

    private val extractor = Extractor(view.getIntent())

    fun getBoor(): Boor {
        return extractor.boor
    }

    fun getAutocompleteAdapter(): AutocompleteAdapter {
        return AutocompleteAdapter(view.getContext(), getBoor())
    }

}