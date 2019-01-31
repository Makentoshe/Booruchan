package com.makentoshe.booruchan.booru.model

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DrawerControllerTest {

    private lateinit var drawerController: DrawerController

    @Before
    fun init() {
        drawerController = DrawerController()
    }

    @Test
    fun `should call onClose when close event happened`() {
        var flag = false
        drawerController.subscribe { onClose { flag = true } }
        drawerController.closeDrawer()
        assertTrue(flag)
        assertEquals(DrawerState.DrawerClose, drawerController.state)

    }

    @Test
    fun `should call onOpen when open event happened`() {
        var flag = false
        drawerController.subscribe { onOpen { flag = true } }
        drawerController.openDrawer()
        assertTrue(flag)
        assertEquals(DrawerState.DrawerOpen, drawerController.state)
    }

    @Test
    fun `should remove all subscribers on clear`() {
        var flag = false
        drawerController.subscribe {
            onOpen {
                flag = true
            }
            onClose {
                flag = false
            }
        }
        drawerController.openDrawer()
        assertTrue(flag)
        drawerController.clear()
        drawerController.closeDrawer()
        assertTrue(flag)
    }
}