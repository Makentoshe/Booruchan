package com.makentoshe.booruchan.booru.model.content.comments

import com.makentoshe.booruchan.booru.model.content.common.DataLoader
import com.makentoshe.booruchan.booru.model.content.common.Downloader
import com.makentoshe.booruchan.common.api.Boor

class CommentsContentDataLoader(private val downloader: Downloader,
                                private val booru: Boor): DataLoader()