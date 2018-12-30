package com.makentoshe.booruchan

import android.content.Intent
import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.Screen

/**
 * AbsScreen is base class for description and creation application screen.<br>
 * NOTE: If you have described the creation of Intent then Activity will be started.<br>
 * Recommendation: Use Intents for launch external application.
 */
abstract class Screen() : Screen() {
    abstract val fragment: Fragment
    open val activityIntent: Intent? = null
}