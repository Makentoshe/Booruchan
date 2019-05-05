//package com.makentoshe.booruchan.screen.samples
//
//import android.widget.GridView
//import android.widget.RelativeLayout
//import androidx.test.espresso.Espresso
//import androidx.test.espresso.Espresso.onView
//import androidx.test.espresso.ViewAssertion
//import androidx.test.espresso.action.ViewActions.click
//import androidx.test.espresso.action.ViewActions.longClick
//import androidx.test.espresso.matcher.ViewMatchers.withId
//import androidx.test.espresso.matcher.ViewMatchers.withText
//import androidx.test.platform.app.InstrumentationRegistry
//import androidx.test.rule.ActivityTestRule
//import androidx.test.uiautomator.By.clazz
//import androidx.test.uiautomator.By.text
//import androidx.test.uiautomator.UiDevice
//import androidx.test.uiautomator.Until.hasObject
//import com.makentoshe.booruchan.*
//import io.reactivex.android.plugins.RxAndroidPlugins
//import io.reactivex.schedulers.Schedulers
//import org.hamcrest.CoreMatchers.anything
//import org.junit.After
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//
//class NotificationsTest {
//
//    @get:Rule
//    val activityTestRule = ActivityTestRule<AppActivity>(AppActivity::class.java, false, false)
//
//    private lateinit var activity: AppActivity
//    private var position = -1
//    private val instrumentation = InstrumentationRegistry.getInstrumentation()
//    private val device = UiDevice.getInstance(instrumentation)
//
//    @Before
//    fun init() {
//        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.from { activity.runOnUiThread(it) } }
//        Booruchan.INSTANCE.settings.setNsfw(Booruchan.INSTANCE.applicationContext, true)
//        Booruchan.INSTANCE.booruList.add(Mockbooru::class.java)
//        position = Booruchan.INSTANCE.booruList.indexOf(Mockbooru::class.java)
//        activity = activityTestRule.launchActivity(null)
//        activity.setMockbooruFactory()
//
//        val gridView = device.findObjects(clazz(GridView::class.java))
//        val gridChild = gridView[0].children[0]
//        gridChild.click()
//    }
//
//    @Test
//    fun shouldHaveNotificationOnDownloadOption() {
//        //long click
//        onView(withId(R.id.samples_container_viewpager)).check { view, _ ->
//            view.findViewById<RelativeLayout>(R.id.samples_page).performLongClick()
//        }
//
//        onView(withText(R.string.download)).perform(click())
//
//        val appName = activity.getString(R.string.app_name)
//        device.openNotification()
//        device.wait(hasObject(text(appName)), 1000)
//        //should contain title as an app name
//        val title = device.findObject(text(appName))
//        assertEquals(title.text, appName)
//    }
//
//    @After
//    fun after() {
//        Booruchan.INSTANCE.booruList.removeAt(position)
//        position = -1
//    }
//
//}