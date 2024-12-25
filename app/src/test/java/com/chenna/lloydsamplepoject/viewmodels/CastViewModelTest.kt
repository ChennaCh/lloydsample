package com.chenna.lloydsamplepoject.viewmodels

import com.chenna.domain.model.CastModel
import com.chenna.domain.model.PersonCountryModel
import com.chenna.domain.model.PersonImageModel
import com.chenna.domain.model.PersonModel
import com.chenna.domain.usecase.ShowsUseCase
import com.chenna.domain.utils.Error
import com.chenna.domain.utils.Message
import com.chenna.domain.utils.MessageType
import com.chenna.domain.utils.Work
import com.chenna.lloydsamplepoject.models.CastActionEvent
import com.chenna.lloydsamplepoject.util.Constants
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

/**
 * Test suite for [CastViewModel].
 * Verifies fetching Cast functionality.
 *
 * Created by Chenna Rao on 25/12/24.
 */

@ExperimentalCoroutinesApi
class CastViewModelTest {

    @MockK
    private lateinit var useCase: ShowsUseCase

    private lateinit var viewModel: CastViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = CastViewModel(useCase)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset main dispatcher
    }

    @Test
    fun `fetchCasts updates resultState with fetched shows`() = runTest {
        val castsList = getShowCasts()
        coEvery { useCase.fetchCasts() } returns Work.Result(data = castsList)

        viewModel.onActionEvent(CastActionEvent.FetchCasts)

        testDispatcher.scheduler.advanceUntilIdle()

        val resultState = viewModel.resultState.value
        assertFalse(resultState.isLoading)
        assertNull(resultState.error)
        assertNotNull(resultState.data)
        assertEquals(castsList, resultState.data?.list)
        coVerify(exactly = 1) { useCase.fetchCasts() }

        // Optional: Print log to check the execution flow
        println("fetchCasts invoked successfully.")
    }

    @Test
    fun `fetchCasts updates resultState with no shows error for empty list`() = runTest {
        // Arrange
        coEvery { useCase.fetchCasts() } returns Work.Result(data = emptyList())

        viewModel.onActionEvent(CastActionEvent.FetchCasts)

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val resultState = viewModel.resultState.value
        assertFalse("Loading state should be false", resultState.isLoading)
        assertNotNull("Error should not be null", resultState.error)
        assertEquals("No casts", resultState.error?.title)
        assertEquals(Constants.Errors.TV_SHOW_CASTS, resultState.error?.description)
        coVerify(exactly = 1) { useCase.fetchCasts() }
    }

    @Test
    fun `fetchCasts updates resultState with error from Work Stop`() = runTest {
        // Arrange
        val errorMessage = Message(
            message = "Failed to fetch casts",
            messageType = MessageType.TOAST
        )
        coEvery { useCase.fetchCasts() } returns Work.Stop(errorMessage)

        viewModel.onActionEvent(CastActionEvent.FetchCasts)

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val resultState = viewModel.resultState.value
        assertFalse("Loading state should be false", resultState.isLoading)
        assertNotNull("Error should not be null", resultState.error)
        assertEquals("Failed to fetch casts", resultState.error?.title)
        assertEquals(errorMessage.message, resultState.error?.description)
        coVerify(exactly = 1) { useCase.fetchCasts() }
    }

    @Test
    fun `fetchCasts updates resultState with handleConnectionError`() = runTest {
        // Arrange
        val errorMessage = Error(
            title = Constants.Errors.CONNECTION_ERROR,
            description = Constants.Errors.TV_SHOWS
        )

        coEvery { useCase.fetchCasts() } returns Work.backfire(RuntimeException())

        viewModel.onActionEvent(CastActionEvent.FetchCasts)

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val resultState = viewModel.resultState.value
        assertFalse("Loading state should be false", resultState.isLoading)
        assertNotNull("Error should not be null", resultState.error)
        assertEquals(errorMessage, errorMessage)
        coVerify(exactly = 1) { useCase.fetchCasts() }
    }

    @Test
    fun `Retry action sets isRefreshing to true and fetches casts`() = runTest {
        val tvShows = getShowCasts()
        coEvery { useCase.fetchCasts() } returns Work.Result(data = tvShows)

        viewModel.onActionEvent(CastActionEvent.Retry)

        testDispatcher.scheduler.advanceUntilIdle()

        val resultState = viewModel.resultState.value
        assertFalse(resultState.isLoading)
        assertNull(resultState.error)
        assertNotNull(resultState.data)
        assertEquals(tvShows, resultState.data?.list)
        assertFalse(viewModel.isRefreshing.value) // Ensures _isRefreshing is false after completion
        coVerify(exactly = 1) { useCase.fetchCasts() }

        // Optional: Print log to verify
        println("Retry action invoked and casts fetched successfully.")
    }

    @Test
    fun `fetchCasts sets isRefreshing to false when list is not empty`() = runTest {
        val tvShows = getShowCasts()
        coEvery { useCase.fetchCasts() } returns Work.Result(data = tvShows)

        viewModel.onActionEvent(CastActionEvent.FetchCasts)

        testDispatcher.scheduler.advanceUntilIdle()

        val resultState = viewModel.resultState.value
        assertFalse(viewModel.isRefreshing.value) // Ensures _isRefreshing is false
        assertFalse(resultState.isLoading)
        assertNull(resultState.error)
        assertNotNull(resultState.data)
        assertEquals(tvShows, resultState.data?.list)
    }

    @Test
    fun `fetchCasts sets isRefreshing to false when list is empty`() = runTest {
        coEvery { useCase.fetchCasts() } returns Work.Result(data = emptyList())

        viewModel.onActionEvent(CastActionEvent.FetchCasts)

        testDispatcher.scheduler.advanceUntilIdle()

        val resultState = viewModel.resultState.value
        assertFalse(viewModel.isRefreshing.value) // Ensures _isRefreshing is false
        assertFalse(resultState.isLoading)
        assertNotNull(resultState.error)
        assertEquals("No casts", resultState.error?.title)
        assertEquals(Constants.Errors.TV_SHOW_CASTS, resultState.error?.description)
    }


    // Sample data generator
    private fun getShowCasts(): List<CastModel> {
        return listOf(
            CastModel(
                person = PersonModel(
                    id = "1",
                    name = "Mike Vogel",
                    birthday = "1979-07-17",
                    country = PersonCountryModel(
                        name = "United States"
                    ),
                    gender = "Male",
                    url = "https://www.tvmaze.com/people/1/mike-vogel",
                    image = PersonImageModel(
                        medium = "https://static.tvmaze.com/uploads/images/medium_portrait/0/3.jpg",
                        original = "https://static.tvmaze.com/uploads/images/original_untouched/0/3.jpg"
                    )
                )
            ),
            CastModel(
                person = PersonModel(
                    id = "1",
                    name = "Rachelle Lefevre",
                    birthday = "1979-02-01",
                    country = PersonCountryModel(
                        name = "Canada"
                    ),
                    gender = "Female",
                    url = "https://www.tvmaze.com/people/1/mike-vogel",
                    image = PersonImageModel(
                        medium = "https://static.tvmaze.com/uploads/images/medium_portrait/82/207417.jpg",
                        original = "https://static.tvmaze.com/uploads/images/original_untouched/82/207417.jpg"
                    )
                )
            )
        )
    }
}