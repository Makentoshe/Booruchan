package com.makentoshe.booruchan.application.android.screen.search.model

import com.makentoshe.booruchan.application.core.arena.ArenaStorage
import com.makentoshe.booruchan.application.core.arena.ArenaStorageException
import com.makentoshe.booruchan.core.tag.Tag
import com.makentoshe.booruchan.core.tag.deserialize.TagsDeserialize
import com.makentoshe.booruchan.core.tag.network.TagsFilter

class TagsArenaStorage : ArenaStorage<TagsFilter, TagsDeserialize<Tag>> {

    override fun fetch(key: TagsFilter): Result<TagsDeserialize<Tag>> {
        return Result.failure(ArenaStorageException(""))
    }

    override fun carry(key: TagsFilter, value: TagsDeserialize<Tag>) {

    }
}