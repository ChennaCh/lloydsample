package com.chenna.lloydsamplepoject.viewmodels

import com.chenna.domain.entities.CountryEntity
import com.chenna.domain.entities.NetworkEntity
import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.entities.ShowImageEntity
import com.chenna.domain.entities.ShowRatingEntity
import com.chenna.domain.models.CountryModel
import com.chenna.domain.models.NetWorkModel
import com.chenna.domain.models.ShowImageModel
import com.chenna.domain.models.ShowModel
import com.chenna.domain.models.ShowRatingModel
import com.chenna.domain.models.toShowEntity
import com.chenna.domain.models.toShowModel
import com.chenna.domain.usecase.ShowsUseCase
import com.chenna.lloydsamplepoject.config.Constants
import com.chenna.lloydsamplepoject.models.BookmarksActionEvent
import com.chenna.lloydsamplepoject.util.NavigationEvent
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
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
 * Created by Chenna Rao on 22/12/24.
 * <p>
 * Frost Interactive
 */

@ExperimentalCoroutinesApi
class BookmarksViewModelTest {

    @MockK
    private lateinit var useCase: ShowsUseCase

    private lateinit var viewModel: BookmarksViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this) // Initialize MockK annotations
        Dispatchers.setMain(testDispatcher)
        viewModel = BookmarksViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset main dispatcher
    }

    @Test
    fun `getBookMarks updates resultState with bookmarks`() = runTest {
        // Arrange
        val bookmarks =
            getTvShowList().map { it.toShowEntity() } // Replace with a method or mock that provides test data
        coEvery { useCase.getAllBookmarks() } returns bookmarks

        // Act
        viewModel.onActionEvent(BookmarksActionEvent.GetBookmarks)
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
    fun `WHEN click on show THEN redirect to show details`() = runTest {
        val actionEvent = BookmarksActionEvent.RedirectToShowDetails(showEntity)
        val navigationEvent = NavigationEvent(
            route = Constants.AppRoute.SHOW_DETAILS,
            any = showEntity.toShowModel()
        )
        val navigationEvents = mutableListOf<NavigationEvent>()
        val job = collectEvents(viewModel.navigationEvent, navigationEvents)

        viewModel.onActionEvent(actionEvent)
        advanceUntilIdle()
        job.cancel()
        assertEquals(navigationEvent, navigationEvents.last())
    }

    private fun getTvShowList(): List<ShowModel> {
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
                network = NetWorkModel(country = CountryModel(name = "United States")),
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

    companion object {
        val showEntity = ShowEntity(
            id = 1,
            name = "Under the Dome",
            language = "English",
            genres = listOf("Drama", "Science-Fiction", "Thriller"),
            status = "Ended",
            runtime = 60,
            rating = ShowRatingEntity(average = 6.5f),
            weight = 98,
            type = "Scripted",
            network = NetworkEntity(
                country = CountryEntity(name = "United States")
            ),
            image = ShowImageEntity(
                medium = "https://static.tvmaze.com/uploads/images/medium_portrait/81/202627.jpg",
                original = "https://static.tvmaze.com/uploads/images/original_untouched/81/202627.jpg"
            ),
            summary = "Under the Dome is the story of a small town sealed off by an enormous dome."
        )
    }
}
