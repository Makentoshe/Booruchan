package com.makentoshe.booruchan

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.makentoshe.booruapi.Booru
import com.makentoshe.booruapi.Tag
import com.makentoshe.booruchan.account.AccountFragment
import com.makentoshe.booruchan.booru.view.BooruFragment
import com.makentoshe.booruchan.booru.model.DrawerController
import com.makentoshe.booruchan.postpreviewspage.PostPageFragment
import com.makentoshe.booruchan.postpreviews.view.PostsFragment
import com.makentoshe.booruchan.postsamples.view.PostSampleFragment
import com.makentoshe.booruchan.postsamples.model.SamplePageBlockController
import com.makentoshe.booruchan.postsamplespage.PostSamplePageFragment
import com.makentoshe.booruchan.postsamplespageinfo.PostSamplePageInfoFragment
import com.makentoshe.booruchan.postsamplespageimage.PostSamplePageImageFragment
import com.makentoshe.booruchan.settings.SettingsFragment
import com.makentoshe.booruchan.start.StartFragment
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.*
import java.util.*
import kotlin.collections.HashSet

/**
 * Screen is a base class for description and creation application screen.<br>
 * NOTE: If you have described the creation of Intent then Activity will be started.<br>
 * Recommendation: Use Intents for launch external application.
 */
abstract class Screen : ru.terrakok.cicerone.Screen() {
    abstract val fragment: Fragment
    open val activityIntent: Intent? = null
}

class StartScreen : Screen() {
    override val fragment: Fragment
        get() = StartFragment()
}

class SettingsScreen : Screen() {
    override val fragment: Fragment
        get() = SettingsFragment()
}

class BooruScreen(
    private val booru: Booru,
    private val tags: HashSet<Tag> = HashSet()
) : Screen() {
    override val fragment: ViewModelFragment<*>
        get() = BooruFragment().putArguments(arguments)

    private val arguments = Bundle().apply {
        putSerializable(Booru::class.java.simpleName, booru)
        putSerializable(Tag::class.java.simpleName, tags)
    }
}

abstract class BooruContentScreen(
    private val booru: Booru,
    private val drawerController: DrawerController,
    private val `class`: Class<out ViewModelFragment<*>>
) : Screen() {
    override val fragment: ViewModelFragment<*>
        get() = `class`.newInstance().putArguments(arguments)

    protected open val arguments = Bundle().apply {
        putSerializable(Booru::class.java.simpleName, booru)
        putSerializable(DrawerController::class.java.simpleName, drawerController)
    }
}

class PostsScreen(
    booru: Booru,
    drawerController: DrawerController,
    private val tags: HashSet<Tag> = HashSet()
) : BooruContentScreen(booru, drawerController, PostsFragment::class.java) {

    override val arguments: Bundle
        get() = super.arguments.apply {
            putSerializable(Set::class.java.simpleName + Tag::class.java.simpleName, tags)
        }
}

class AccountScreen(booru: Booru, drawerController: DrawerController) :
    BooruContentScreen(booru, drawerController, AccountFragment::class.java)

class PostPageScreen(
    private val booru: Booru,
    private val position: Int,
    private val postsRepository: PostsRepository,
    private val previewsRepository: PreviewImageRepository
) : Screen() {
    override val fragment: Fragment
        get() = PostPageFragment().putArguments(arguments)

    private val arguments: Bundle
        get() = Bundle().apply {
            putSerializable(Booru::class.java.simpleName, booru)
            putInt(PostPageFragment::class.java.simpleName, position)
            putSerializable(PostsRepository::class.java.simpleName, postsRepository)
            putSerializable(PreviewImageRepository::class.java.simpleName, previewsRepository)
        }
}

class PostSamplesScreen(
    private val booru: Booru,
    private val startPosition: Int,
    private val postsRepository: PostsRepository,
    private val sampleRepository: ImageRepository
) : Screen() {
    override val fragment: Fragment
        get() = PostSampleFragment().apply {
            arguments = this@PostSamplesScreen.arguments
        }

    private val arguments: Bundle
        get() = Bundle().apply {
            putSerializable(Booru::class.java.simpleName, booru)
            putInt(Int::class.java.simpleName, startPosition)
            putSerializable(PostsRepository::class.java.simpleName, postsRepository)
            putSerializable(ImageRepository::class.java.simpleName, sampleRepository)
        }
}

class PostSamplePageScreen(
    private val position: Int,
    private val samplePageController: SamplePageBlockController,
    private val sampleRepository: ImageRepository,
    private val postsRepository: PostsRepository
) : Screen() {
    override val fragment: Fragment
        get() = PostSamplePageFragment().apply {
            arguments = this@PostSamplePageScreen.arguments
        }

    private val arguments: Bundle
        get() = Bundle().apply {
            putInt(Int::class.java.simpleName, position)
            putSerializable(SamplePageBlockController::class.java.simpleName, samplePageController)
            putSerializable(ImageRepository::class.java.simpleName, sampleRepository)
            putSerializable(PostsRepository::class.java.simpleName, postsRepository)
        }
}

class PostSamplePagePreviewScreen(
    private val sampleRepository: ImageRepository,
    private val position: Int,
    private val postsRepository: PostsRepository
) : Screen() {
    override val fragment: Fragment
        get() = PostSamplePageImageFragment().apply {
            arguments = this@PostSamplePagePreviewScreen.arguments
        }

    private val arguments: Bundle
        get() = Bundle().apply {
            putInt(Int::class.java.simpleName, position)
            putSerializable(ImageRepository::class.java.simpleName, sampleRepository)
            putSerializable(PostsRepository::class.java.simpleName, postsRepository)
        }
}

class PostSamplePageInfoScreen(
    private val position: Int,
    private val postsRepository: PostsRepository
) : Screen() {
    override val fragment: Fragment
        get() = PostSamplePageInfoFragment().apply {
            arguments = this@PostSamplePageInfoScreen.arguments
        }

    private val arguments: Bundle
        get() = Bundle().apply {
            putInt(Int::class.java.simpleName, position)
            putSerializable(PostsRepository::class.java.simpleName, postsRepository)
        }
}

/**
 * Navigator is a class which manages application screens using default android classes - Activity and Fragment.<br>
 * This class is a rebuild of the original Navigator class from Cicerone library using kotlin.
 */
open class Navigator(
    private val activity: FragmentActivity,
    private val containerId: Int,
    private val fragmentManager: FragmentManager = activity.supportFragmentManager
) : Navigator {

    private var localStackCopy: LinkedList<String>? = null

    override fun applyCommands(commands: Array<Command>) {
        fragmentManager.executePendingTransactions()

        //copy stack before apply commands
        copyStackToLocal()

        for (command in commands) {
            applyCommand(command)
        }
    }

    private fun copyStackToLocal() {
        localStackCopy = LinkedList()

        val stackSize = fragmentManager.backStackEntryCount
        for (i in 0 until stackSize) {
            localStackCopy!!.add(fragmentManager.getBackStackEntryAt(i).name!!)
        }
    }

    /**
     * Perform transition described by the navigation command
     *
     * @param command the navigation command to apply
     */
    protected open fun applyCommand(command: Command) {
        when (command) {
            is Forward -> activityForward(command)
            is Replace -> activityReplace(command)
            is BackTo -> backTo(command)
            is Back -> fragmentBack()
        }
    }

    protected open fun activityForward(command: Forward) {
        val screen = command.screen as Screen
        val activityIntent = screen.activityIntent

        // Start activity
        if (activityIntent != null) {
            val options = createStartActivityOptions(command, activityIntent)
            checkAndStartActivity(screen, activityIntent, options)
        } else {
            fragmentForward(command)
        }
    }

    protected open fun fragmentForward(command: Forward) {
        val screen = command.screen as Screen
        val fragment = createFragment(screen)

        val fragmentTransaction = fragmentManager.beginTransaction()

        setupFragmentTransaction(
            command,
            fragmentManager.findFragmentById(containerId)!!,
            fragment,
            fragmentTransaction
        )

        fragmentTransaction
            .add(containerId, fragment, screen.screenKey)
            .addToBackStack(screen.screenKey)
            .commit()
        localStackCopy!!.add(screen.screenKey)
    }

    protected open fun fragmentBack() {
        if (localStackCopy!!.size > 0) {
            fragmentManager.popBackStack()
            localStackCopy!!.removeLast()
        } else {
            activityBack()
        }
    }

    protected open fun activityBack() = activity.finish()

    protected open fun activityReplace(command: Replace) {
        val screen = command.screen as Screen
        val activityIntent = screen.activityIntent

        // Replace activity
        if (activityIntent != null) {
            val options = createStartActivityOptions(command, activityIntent)
            checkAndStartActivity(screen, activityIntent, options)
            activity.finish()
        } else {
            fragmentReplace(command)
        }
    }

    protected open fun fragmentReplace(command: Replace) {
        val screen = command.screen as Screen
        val newFragment = createFragment(screen)

        if (localStackCopy!!.size > 0) {
            fragmentManager.popBackStack()
            localStackCopy!!.removeLast()

            val fragmentTransaction = fragmentManager.beginTransaction()

            setupFragmentTransaction(
                command,
                fragmentManager.findFragmentById(containerId),
                newFragment,
                fragmentTransaction
            )

            fragmentTransaction
                .replace(containerId, newFragment)
                .addToBackStack(screen.screenKey)
                .commit()
            localStackCopy!!.add(screen.screenKey)

        } else {
            val fragmentTransaction = fragmentManager.beginTransaction()

            setupFragmentTransaction(
                command,
                fragmentManager.findFragmentById(containerId),
                newFragment,
                fragmentTransaction
            )

            fragmentTransaction
                .replace(containerId, newFragment)
                .commit()
        }
    }

    /**
     * Performs [BackTo] command transition
     */
    protected open fun backTo(command: BackTo) {
        if (command.screen == null) {
            backToRoot()
        } else {
            val key = command.screen.screenKey
            val index = localStackCopy!!.indexOf(key)
            val size = localStackCopy!!.size

            if (index != -1) {
                for (i in 1 until size - index) {
                    localStackCopy!!.removeLast()
                }
                fragmentManager.popBackStack(key, 0)
            } else {
                backToUnexisting(command.screen as Screen)
            }
        }
    }

    private fun backToRoot() {
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        localStackCopy!!.clear()
    }

    /**
     * Override this method to setup fragment transaction [FragmentTransaction].
     * For example: setCustomAnimations(...), addSharedElement(...) or setReorderingAllowed(...)
     *
     * @param command             current navigation command. Will be only [Forward] or [Replace]
     * @param currentFragment     current fragment in container
     * (for [Replace] command it will be screen previous in new chain, NOT replaced screen)
     * @param nextFragment        next screen fragment
     * @param fragmentTransaction fragment transaction
     */
    protected open fun setupFragmentTransaction(
        command: Command,
        currentFragment: Fragment?,
        nextFragment: Fragment?,
        fragmentTransaction: FragmentTransaction
    ) = Unit

    /**
     * Override this method to create option for start activity
     *
     * @param command        current navigation command. Will be only [Forward] or [Replace]
     * @param activityIntent activity intent
     * @return transition options
     */
    protected open fun createStartActivityOptions(command: Command, activityIntent: Intent): Bundle? = null

    private fun checkAndStartActivity(screen: Screen, activityIntent: Intent, options: Bundle?) {
        // Check if we can start activity
        if (activityIntent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(activityIntent, options)
        } else {
            unexistingActivity(screen, activityIntent)
        }
    }

    /**
     * Called when there is no activity to open `screenKey`.
     *
     * @param screen         screen
     * @param activityIntent intent passed to start Activity for the `screenKey`
     */
    // Do nothing by default
    protected open fun unexistingActivity(screen: Screen, activityIntent: Intent) = Unit

    /**
     * Creates Fragment matching `screenKey`.
     *
     * @param screen screen
     * @return instantiated fragment for the passed screen
     */
    protected open fun createFragment(screen: Screen) = screen.fragment

    /**
     * Called when we tried to fragmentBack to some specific screen (via [BackTo] command),
     * but didn't found it.
     *
     * @param screen screen
     */
    protected open fun backToUnexisting(screen: Screen) = backToRoot()
}