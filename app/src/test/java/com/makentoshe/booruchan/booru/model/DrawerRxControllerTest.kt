package com.makentoshe.booruchan.booru.model

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DrawerControllerImplTest {

    private lateinit var drawerController: DrawerControllerImpl

    @Before
    fun init() {
        drawerController = DrawerControllerImpl()
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