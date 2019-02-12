package com.makentoshe.booruchan.booru.model

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DrawerRxControllerTest {

    private lateinit var drawerRxController: DrawerRxController

    @Before
    fun init() {
        drawerRxController = DrawerRxController()
    }

    @Test
    fun `should call onClose when close event happened`() {
        var flag = false
        drawerRxController.subscribe { onClose { flag = true } }
        drawerRxController.closeDrawer()
        assertTrue(flag)
        assertEquals(DrawerState.DrawerClose, drawerRxController.state)

    }

    @Test
    fun `should call onOpen when open event happened`() {
        var flag = false
        drawerRxController.subscribe { onOpen { flag = true } }
        drawerRxController.openDrawer()
        assertTrue(flag)
        assertEquals(DrawerState.DrawerOpen, drawerRxController.state)
    }

    @Test
    fun `should remove all subscribers on clear`() {
        var flag = false
        drawerRxController.subscribe {
            onOpen {
                flag = true
            }
            onClose {
                flag = false
            }
        }
        drawerRxController.openDrawer()
        assertTrue(flag)
        drawerRxController.clear()
        drawerRxController.closeDrawer()
        assertTrue(flag)
    }
}