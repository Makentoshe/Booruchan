package com.makentoshe.booruchan.application.android.screen.search.model

import com.makentoshe.booruchan.core.Text
import com.makentoshe.booruchan.core.tag.Tag
import com.makentoshe.booruchan.core.tag.Type

class CompositeSearchTagsContainer(private vararg val containers: TypedSearchTagsContainer) : SearchTagsContainer {

    override val tags: Set<Text>
        get() = containers.flatMap { it.tags }.toHashSet()

    override val appliedTags: Set<Text>
        get() = containers.flatMap { it.appliedTags }.toHashSet()

    /** Add tag to container specified by tag type */
    fun addTag(tag: Tag): Boolean {
        val container = containers.find { it.type == tag.type }
        return container?.tags?.add(tag) ?: false
    }

    /** Add tag to general container */
    fun addTag(text: Text): Boolean {
        val container = containers.find { it.type == Type.GENERAL }
        return container?.tags?.add(text) ?: false
    }

    /** Remove tag from general container */
    fun remove(text: Text): Boolean {
        val container = containers.find { it.type == Type.GENERAL }
        if (container?.tags?.remove(text) == true) return true
        if (container?.appliedTags?.remove(text) == true) return true
        return false
    }

    /** Remove tag from specified by tag type container */
    fun remove(tag: Tag): Boolean {
        val container = containers.find { it.type == tag.type }
        if (container?.tags?.remove(tag) == true) return true
        if (container?.appliedTags?.remove(tag) == true) return true
        return false
    }

    fun applyTags(): Set<Text> = containers.flatMap { container ->
        container.appliedTags.addAll(container.tags)
        container.tags.clear()
        container.appliedTags
    }.toSet()
}