package com.chenna.lloydsamplepoject.viewmodels

import com.chenna.domain.model.CountryModel
import com.chenna.domain.model.NetWorkModel
import com.chenna.domain.model.ShowImageModel
import com.chenna.domain.model.ShowModel
import com.chenna.domain.model.ShowRatingModel
import com.chenna.domain.usecase.ShowsUseCase
import com.chenna.domain.utils.Error
import com.chenna.domain.utils.Message
import com.chenna.domain.utils.MessageType
import com.chenna.domain.utils.Work
import com.chenna.lloydsamplepoject.models.TvShowActionEvent
import com.chenna.lloydsamplepoject.util.Constants
import com.chenna.lloydsamplepoject.util.NavigationEvent
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
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
 * Test suite for [TVShowsViewModel].
 * Verifies fetching TV shows functionality.
 *
 * Created by Chenna Rao on 22/12/24.
 */

@ExperimentalCoroutinesApi
class TVShowsViewModelTest {

    @MockK
    private lateinit var useCase: ShowsUseCase

    private lateinit var viewModel: TVShowsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = TVShowsViewModel(useCase)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset main dispatcher
    }

    @Test
    fun `fetchTvShows updates resultState with fetched shows`() = runTest {
        val tvShows = getShowList()
        coEvery { useCase.getListOfShows() } returns Work.Result(data = tvShows)

        viewModel.onActionEvent(TvShowActionEvent.FetchTvShows)

        testDispatcher.scheduler.advanceUntilIdle()

        val resultState = viewModel.resultState.value
        assertFalse(resultState.isLoading)
        assertNull(resultState.error)
        assertNotNull(resultState.data)
        assertEquals(tvShows, resultState.data?.list)
        coVerify(exactly = 1) { useCase.getListOfShows() }

        // Optional: Print log to check the execution flow
        println("fetchTvShows invoked successfully.")
    }

    @Test
    fun `fetchTvShows updates resultState with no shows error for empty list`() = runTest {
        // Arrange
        coEvery { useCase.getListOfShows() } returns Work.Result(data = emptyList())

        viewModel.onActionEvent(TvShowActionEvent.FetchTvShows)

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val resultState = viewModel.resultState.value
        assertFalse("Loading state should be false", resultState.isLoading)
        assertNotNull("Error should not be null", resultState.error)
        assertEquals("No shows", resultState.error?.title)
        assertEquals(Constants.Errors.TV_SHOWS, resultState.error?.description)
        coVerify(exactly = 1) { useCase.getListOfShows() }
    }

    @Test
    fun `fetchTvShows updates resultState with error from Work Stop`() = runTest {
        // Arrange
        val errorMessage = Message(
            message = "Failed to fetch shows",
            messageType = MessageType.TOAST
        )
        coEvery { useCase.getListOfShows() } returns Work.Stop(errorMessage)

        viewModel.onActionEvent(TvShowActionEvent.FetchTvShows)

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val resultState = viewModel.resultState.value
        assertFalse("Loading state should be false", resultState.isLoading)
        assertNotNull("Error should not be null", resultState.error)
        assertEquals("Failed to fetch shows", resultState.error?.title)
        assertEquals(errorMessage.message, resultState.error?.description)
        coVerify(exactly = 1) { useCase.getListOfShows() }
    }

    @Test
    fun `fetchTvShows updates resultState with handleConnectionError`() = runTest {
        // Arrange
        val errorMessage = Error(
            title = Constants.Errors.CONNECTION_ERROR,
            description = Constants.Errors.TV_SHOWS
        )

        coEvery { useCase.getListOfShows() } returns Work.backfire(RuntimeException())

        viewModel.onActionEvent(TvShowActionEvent.FetchTvShows)

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val resultState = viewModel.resultState.value
        assertFalse("Loading state should be false", resultState.isLoading)
        assertNotNull("Error should not be null", resultState.error)
        assertEquals(errorMessage, errorMessage)
        coVerify(exactly = 1) { useCase.getListOfShows() }
    }

    @Test
    fun `WHEN click on show THEN redirect to show details`() = runTest {
        val actionEvent = TvShowActionEvent.RedirectToShowDetails(showModel)
        val navigationEvent = NavigationEvent(
            route = Constants.AppRoute.SHOW_DETAILS,
            any = actionEvent.model
        )
        val navigationEvents = mutableListOf<NavigationEvent>()
        val job = collectEvents(viewModel.navigationEvent, navigationEvents)

        viewModel.onActionEvent(actionEvent)
        advanceUntilIdle()
        job.cancel()
        assertEquals(navigationEvent, navigationEvents.last())
    }

    @Test
    fun `Retry action sets isRefreshing to true and fetches shows`() = runTest {
        val tvShows = getShowList()
        coEvery { useCase.getListOfShows() } returns Work.Result(data = tvShows)

        viewModel.onActionEvent(TvShowActionEvent.Retry)

        testDispatcher.scheduler.advanceUntilIdle()

        val resultState = viewModel.resultState.value
        assertFalse(resultState.isLoading)
        assertNull(resultState.error)
        assertNotNull(resultState.data)
        assertEquals(tvShows, resultState.data?.list)
        assertFalse(viewModel.isRefreshing.value) // Ensures _isRefreshing is false after completion
        coVerify(exactly = 1) { useCase.getListOfShows() }

        // Optional: Print log to verify
        println("Retry action invoked and shows fetched successfully.")
    }

    @Test
    fun `fetchTvShows sets isRefreshing to false when list is not empty`() = runTest {
        val tvShows = getShowList()
        coEvery { useCase.getListOfShows() } returns Work.Result(data = tvShows)

        viewModel.onActionEvent(TvShowActionEvent.FetchTvShows)

        testDispatcher.scheduler.advanceUntilIdle()

        val resultState = viewModel.resultState.value
        assertFalse(viewModel.isRefreshing.value) // Ensures _isRefreshing is false
        assertFalse(resultState.isLoading)
        assertNull(resultState.error)
        assertNotNull(resultState.data)
        assertEquals(tvShows, resultState.data?.list)
    }

    @Test
    fun `fetchTvShows sets isRefreshing to false when list is empty`() = runTest {
        coEvery { useCase.getListOfShows() } returns Work.Result(data = emptyList())

        viewModel.onActionEvent(TvShowActionEvent.FetchTvShows)

        testDispatcher.scheduler.advanceUntilIdle()

        val resultState = viewModel.resultState.value
        assertFalse(viewModel.isRefreshing.value) // Ensures _isRefreshing is false
        assertFalse(resultState.isLoading)
        assertNotNull(resultState.error)
        assertEquals("No shows", resultState.error?.title)
        assertEquals(Constants.Errors.TV_SHOWS, resultState.error?.description)
    }


    // Sample data generator
    private fun getShowList(): List<ShowModel> {
        return listOf(
            ShowModel(
                id = 1,
                name = "Under the Dome",
                language = "English",
                genres = listOf("Drama", "Science-Fiction", "Thriller"),
                status = "Ended",
                runtime = 60,
                rating = ShowRatingModel(average = 6.5f),
                weight = 98,
                type = "Scripted",
                network = NetWorkModel(
                    country = CountryModel(name = "United States")
                ),
                image = ShowImageModel(
                    medium = "https://static.tvmaze.com/uploads/images/medium_portrait/81/202627.jpg",
                    original = "https://static.tvmaze.com/uploads/images/original_untouched/81/202627.jpg"
                ),
                summary = "Under the Dome is the story of a small town sealed off by an enormous dome."
            ),
            ShowModel(
                id = 2,
                name = "Breaking Bad",
                language = "English",
                genres = listOf("Crime", "Drama", "Thriller"),
                status = "Ended",
                runtime = 47,
                type = "Scripted",
                network = NetWorkModel(
                    country = CountryModel(name = "United States")
                ),
                rating = ShowRatingModel(average = 9.5f),
                weight = 100,
                image = ShowImageModel(
                    medium = "https://static.tvmaze.com/uploads/images/medium_portrait/0/2400.jpg",
                    original = "https://static.tvmaze.com/uploads/images/original_untouched/0/2400.jpg"
                ),
                summary = "A high school chemistry teacher turned methamphetamine producer."
            )
        )
    }

    companion object {
        val showModel = ShowModel(
            id = 1,
            name = "Under the Dome",
            language = "English",
            genres = listOf("Drama", "Science-Fiction", "Thriller"),
            status = "Ended",
            runtime = 60,
            rating = ShowRatingModel(average = 6.5f),
            weight = 98,
            type = "Scripted",
            network = NetWorkModel(
                country = CountryModel(name = "United States")
            ),
            image = ShowImageModel(
                medium = "https://static.tvmaze.com/uploads/images/medium_portrait/81/202627.jpg",
                original = "https://static.tvmaze.com/uploads/images/original_untouched/81/202627.jpg"
            ),
            summary = "Under the Dome is the story of a small town sealed off by an enormous dome."
        )
    }
}

fun <T> TestScope.collectEvents(flow: Flow<T>, events: MutableList<T>): Job {
    return launch {
        flow.collect { event ->
            events.add(event)
        }
    }
}