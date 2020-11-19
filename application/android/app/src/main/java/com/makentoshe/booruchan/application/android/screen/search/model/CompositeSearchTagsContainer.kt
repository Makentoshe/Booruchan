package com.makentoshe.booruchan.application.android.screen.search.model

import com.makentoshe.booruchan.core.Text
import com.makentoshe.booruchan.core.tag.Tag
import com.makentoshe.booruchan.core.tag.Type

internal class CompositeSearchTagsContainer(private vararg val containers: TypedSearchTagsContainer) : SearchTagsContainer {

    override val tags: Set<Text>
        get() = containers.flatMap { it.tags }.toHashSet()

    override val tagsActions: List<SearchTagsContainer.TagAction>
        get() = containers.flatMap { it.tagsActions }

    /** Add tag to container specified by tag type */
    fun addTag(tag: Tag): Boolean {
        val container = containers.find { it.type == tag.type }
        return container?.tagsActions?.add(SearchTagsContainer.TagAction.Add(tag)) ?: false
    }

    /** Add tag to general container */
    fun addTag(text: Text): Boolean {
        val container = containers.find { it.type == Type.GENERAL }
        return container?.tagsActions?.add(SearchTagsContainer.TagAction.Add(text)) ?: false
    }

    /** Remove tag from general container */
    fun remove(text: Text): Boolean {
        val container = containers.find { it.type == Type.GENERAL }
        return container?.tagsActions?.add(SearchTagsContainer.TagAction.Remove(text)) ?: false
    }

    /** Remove tag from specified by tag type container */
    fun remove(tag: Tag): Boolean {
        val container = containers.find { it.type == tag.type }
        return container?.tagsActions?.add(SearchTagsContainer.TagAction.Remove(tag)) ?: false
    }

    /** Executes tags remove/add actions and returns final result */
    fun applyTags(): Set<Text> = containers.flatMap { container ->
        container.tagsActions.forEach { action ->
            when (action) {
                is SearchTagsContainer.TagAction.Add -> container.tags.add(action.tag)
                is SearchTagsContainer.TagAction.Remove -> container.tags.remove(action.tag)
            }
        }

        container.tagsActions.clear()
        return@flatMap container.tags
    }.toSet()

}