package com.chenna.lloydsamplepoject

import com.chenna.domain.model.*
import com.chenna.domain.usecase.ShowsUseCase
import com.chenna.domain.utils.Message
import com.chenna.domain.utils.MessageType
import com.chenna.domain.utils.Work
import com.chenna.lloydsamplepoject.util.Constants
import com.chenna.lloydsamplepoject.viewmodels.TVShowsViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*

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
        MockKAnnotations.init(this) // Initialize MockK annotations
        Dispatchers.setMain(testDispatcher)
        viewModel = TVShowsViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset main dispatcher
    }

    @Test
    fun `fetchTvShows updates resultState with fetched shows`() = runTest {
        val tvShows = getShowList()
        coEvery { useCase.getListOfShows() } returns Work.Result(data = tvShows)

        viewModel.fetchTvShows()
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

        // Act
        viewModel.fetchTvShows()

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

        // Act
        viewModel.fetchTvShows()
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val resultState = viewModel.resultState.value
        assertFalse("Loading state should be false", resultState.isLoading)
        assertNotNull("Error should not be null", resultState.error)
        assertEquals("Error", resultState.error?.title)
        assertEquals(errorMessage.message, resultState.error?.description)
        coVerify(exactly = 1) { useCase.getListOfShows() }
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
}
