package com.makentoshe.booruchan

import com.makentoshe.booruchan.api.safebooru.Safebooru
import com.makentoshe.booruchan.network.fuel.FuelHttpClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun sas() = runBlocking {
        val result = Safebooru(FuelHttpClient()).getAutocomplete().request("hat")

        Unit
    }
}
