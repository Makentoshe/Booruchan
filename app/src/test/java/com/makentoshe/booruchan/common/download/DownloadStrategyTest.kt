package com.makentoshe.booruchan.common.download

import com.makentoshe.booruchan.api.component.post.Post
import com.makentoshe.booruchan.common.SchedulersProvider
import com.makentoshe.booruchan.repository.Repository
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.random.Random

class DownloadStrategyTest {

    private var disposable = CompositeDisposable()

    private lateinit var strategy: DownloadStrategy
    private val schedulersProvider = SchedulersProvider(Schedulers.trampoline(), Schedulers.trampoline())

    @Test(timeout = 5000)
    fun shouldCallOnSuccessOnRepositoryReturns() {
        val bytes = Random.nextBytes(39)

        val repository = mockk<Repository<Post, ByteArray>>()
        every { repository.get(any()) } returns bytes

        strategy = spyk(DownloadStrategy(repository, disposable, schedulersProvider))
        //set listener
        var result: ByteArray? = null
        strategy.onSuccess { result = it }
        //start
        strategy.start(mockk())
        //wait while onSuccess will be called
        while (result == null) Unit
        //assert result
        assertArrayEquals(result, bytes)
    }

    @Test(timeout = 5000)
    fun shouldCallOnErrorOnRepositoryReturns() {
        val err = Throwable()

        val repository = mockk<Repository<Post, ByteArray>>()
        every { repository.get(any()) } throws err

        //create testing object
        strategy = DownloadStrategy(repository, disposable, schedulersProvider)
        //set listener
        var result: Throwable? = null
        strategy.onError { result = it }
        //start
        strategy.start(mockk())
        //wait while onSuccess will be called
        while (result == null) Unit
        //assert result
        assertEquals(result, err)
    }

    @After
    fun after() {
        disposable.clear()
    }
}