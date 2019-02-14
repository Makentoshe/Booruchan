package com.makentoshe.booruchan.postpreviews.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.postpreviews.model.TagController
import com.makentoshe.booruchan.postpreviews.model.TagsController
import com.makentoshe.viewmodel.ViewModel

class TagsViewModel : ViewModel(), TagsController {
    /* Performs adding tags */
    private lateinit var addController: TagController
    /* Performs removing tags */
    private lateinit var removeController: TagController
    /* Performs containing tags */
    private val tagset = HashSet<Tag>()

    /** Adds a [tag] to the [currentlySelectedTags] set. */
    override fun addTag(tag: Tag) = addController.action(tag)

    /** Removes a [tag] from the [currentlySelectedTags] set */
    override fun removeTag(tag: Tag) = removeController.action(tag)

    /**
     * Subscribes on the [addTag] or [removeTag] actions.
     *
     * @param action the action that must be executed when some action with
     * the [currentlySelectedTags] set will be invoked.
     * The [Boolean] takes the false value for remove tag event and true for add tag event.
     * The [Tag] contains only [Tag.name] property and describes a tag with which actionwas performed
     * */
    override fun subscribeOnChange(action: (Tag, Boolean) -> Unit) {
        addController.subscribe {
            tagset.add(it)
            action(it, true)
        }
        removeController.subscribe {
            tagset.remove(it)
            action(it, false)
        }
    }

    /**
     * [Set] that contains all selected [Tag] elements at the moment.
     */
    override val currentlySelectedTags: Set<Tag>
        get() = tagset

    override fun onCreateView(owner: Fragment) {
        addController.clear()
        removeController.clear()
    }

    override fun onCleared() {
        super.onCleared()
        addController.clear()
        removeController.clear()
    }

    /**
     * @param initTagSet the tag set which will be added to the [currentlySelectedTags] set on init.
     */
    class Factory(private val initTagSet: Set<Tag>) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : androidx.lifecycle.ViewModel?> create(modelClass: Class<T>): T {
            val viewModel = TagsViewModel()
            viewModel.addController = TagController()
            viewModel.removeController = TagController()

            initTagSet.forEach(viewModel.addController::action)

            return viewModel as T
        }
    }
}