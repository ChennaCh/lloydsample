package com.chenna.lloydsamplepoject

import com.chenna.domain.entities.CountryModel
import com.chenna.domain.entities.NetWorkModel
import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.entities.ShowImageModel
import com.chenna.domain.entities.ShowRatingModel
import com.chenna.domain.usecase.ShowsUseCase
import com.chenna.domain.utils.Constants
import com.chenna.domain.utils.Message
import com.chenna.domain.utils.MessageType
import com.chenna.domain.utils.Work
import com.chenna.lloydsamplepoject.viewmodels.TVShowsViewModel
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
 * Created by Chenna Rao on 22/12/24.
 * <p>
 * Frost Interactive
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
    fun `getBookMarks updates resultState with bookmarks`() = runTest {
        // Arrange
        val bookmarks = getShowList() // Replace with a method or mock that provides test data
        coEvery { useCase.getAllBookmarks() } returns bookmarks

        // Act
        viewModel.getBookMarks()
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val resultState = viewModel.resultState.value
        assertFalse(resultState.isLoading)
        assertNull(resultState.error)
        assertNotNull(resultState.data)
        assertEquals(bookmarks, resultState.data?.bookMarks)
        coVerify { useCase.getAllBookmarks() }
    }

    @Test
    fun `fetchTvShows updates resultState with fetched shows`() = runTest {
        // Arrange
        val tvShows = getShowList()
        coEvery { useCase.getListOfShows() } returns Work.Result(tvShows)

        // Act
        viewModel.fetchTvShows()
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val resultState = viewModel.resultState.value
        assertFalse(resultState.isLoading)
        assertNull(resultState.error)
        assertNotNull(resultState.data)
        assertEquals(tvShows, resultState.data?.list)
        coVerify { useCase.getListOfShows() }
    }

    @Test
    fun `fetchTvShows updates resultState with no shows error for empty list`() = runTest {
        // Arrange
        coEvery { useCase.getListOfShows() } returns Work.Result(emptyList())

        // Act
        viewModel.fetchTvShows()
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val resultState = viewModel.resultState.value
        assertFalse(resultState.isLoading)
        assertNotNull(resultState.error)
        assertEquals("No shows", resultState.error?.title)
        assertEquals(Constants.Errors.TV_SHOWS, resultState.error?.description)
        coVerify { useCase.getListOfShows() }
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
        assertFalse(resultState.isLoading)
        assertNotNull(resultState.error)
        assertEquals("Error", resultState.error?.title)
        assertEquals(errorMessage.message, resultState.error?.description)
        coVerify { useCase.getListOfShows() }
    }

}

fun getShowList(): List<ShowEntity> {
    return listOf(
        ShowEntity(
            id = 1,
            name = "Under the Dome",
            language = "English",
            genres = listOf("Drama", "Science-Fiction", "Thriller"),
            status = "Ended",
            runtime = 60,
            rating = ShowRatingModel(average = 6.5f),
            weight = 98,
            type = "Scripted",
            network = NetWorkModel(country = CountryModel(name = "United States")),
            image = ShowImageModel(
                medium = "https://static.tvmaze.com/uploads/images/medium_portrait/81/202627.jpg",
                original = "https://static.tvmaze.com/uploads/images/original_untouched/81/202627.jpg"
            ),
            summary = "Under the Dome is the story of a small town sealed off by an enormous dome."
        ),
        ShowEntity(
            id = 2,
            name = "Breaking Bad",
            language = "English",
            genres = listOf("Crime", "Drama", "Thriller"),
            status = "Ended",
            runtime = 47,
            type = "Scripted",
            network = NetWorkModel(country = CountryModel(name = "United States")),
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

fun getShowItem(): ShowEntity {
    return ShowEntity(
        id = 1,
        name = "Under the Dome",
        language = "English",
        genres = listOf("Drama", "Science-Fiction", "Thriller"),
        status = "Ended",
        runtime = 60,
        rating = ShowRatingModel(average = 6.5f),
        weight = 98,
        type = "Scripted",
        network = NetWorkModel(country = CountryModel(name = "United States")),
        image = ShowImageModel(
            medium = "https://static.tvmaze.com/uploads/images/medium_portrait/81/202627.jpg",
            original = "https://static.tvmaze.com/uploads/images/original_untouched/81/202627.jpg"
        ),
        summary = "Under the Dome is the story of a small town sealed off by an enormous dome."
    )
}
