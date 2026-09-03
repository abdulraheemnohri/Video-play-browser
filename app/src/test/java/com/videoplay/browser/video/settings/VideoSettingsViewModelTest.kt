package com.videoplay.browser.video.settings

import com.videoplay.browser.core.preferences.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@OptIn(ExperimentalCoroutinesApi::class)
class VideoSettingsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var mockRepository: SettingsRepository
    private lateinit var viewModel: VideoSettingsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = mock(SettingsRepository::class.java)
        viewModel = VideoSettingsViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testSetAutoPlay() = runTest {
        viewModel.setAutoPlay("always")
        testDispatcher.scheduler.advanceUntilIdle()
        verify(mockRepository).setAutoPlay("always")
    }

    @Test
    fun testSetDefaultPlaybackSpeed() = runTest {
        viewModel.setDefaultPlaybackSpeed(1.5f)
        testDispatcher.scheduler.advanceUntilIdle()
        verify(mockRepository).setDefaultPlaybackSpeed(1.5f)
    }

    @Test
    fun testSetRememberPlaybackSpeed() = runTest {
        viewModel.setRememberPlaybackSpeed(false)
        testDispatcher.scheduler.advanceUntilIdle()
        verify(mockRepository).setRememberPlaybackSpeed(false)
    }

    @Test
    fun testSetEnablePiP() = runTest {
        viewModel.setEnablePiP(true)
        testDispatcher.scheduler.advanceUntilIdle()
        verify(mockRepository).setEnablePiP(true)
    }

    @Test
    fun testSetEnableMiniPlayer() = runTest {
        viewModel.setEnableMiniPlayer(true)
        testDispatcher.scheduler.advanceUntilIdle()
        verify(mockRepository).setEnableMiniPlayer(true)
    }
}
