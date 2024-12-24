package com.chenna.lloydsamplepoject.viewmodels

import com.chenna.domain.entities.CountryEntity
import com.chenna.domain.entities.NetworkEntity
import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.entities.ShowImageEntity
import com.chenna.domain.entities.ShowRatingEntity
import com.chenna.domain.usecase.ShowsUseCase
import com.chenna.lloydsamplepoject.models.TvShowDetailsActionEvent
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/**
 * Created by Chenna Rao on 24/12/24.
 * <p>
 * Frost Interactive
 */
@ExperimentalCoroutinesApi
class TvShowDetailsViewModelTest {

    private val useCase: ShowsUseCase = mockk(relaxUnitFun = true)
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: TvShowDetailsViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this) // Initialize MockK annotations
        Dispatchers.setMain(testDispatcher)
        viewModel = TvShowDetailsViewModel(useCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset main dispatcher
    }

    @Test
    fun `WHEN saveBookMark called THEN VERIFY useCase saveBookmark call`() = runTest {
        viewModel.onActionEvent(TvShowDetailsActionEvent.SaveBookMark(showEntity))
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { useCase.saveBookmark(showEntity) }
    }

    @Test
    fun `WHEN removeBookmark called THEN VERIFY useCase removeBookmark call`() = runTest {
        val tvShowId = 1
        viewModel.onActionEvent(TvShowDetailsActionEvent.RemoveBookMark(tvShowId))
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { useCase.removeBookmark(tvShowId) }
    }


    @Test
    fun `WHEN IsShowBookmarked THEN updates the state`() = runTest {
        val tvShowId = 1
        val isBookmark = true
        coEvery { useCase.isShowBookmarked(tvShowId) } returns isBookmark

        viewModel.onActionEvent(TvShowDetailsActionEvent.IsShowBookmarked(tvShowId))
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val result = viewModel.isBookmarked.value
        Assert.assertEquals(isBookmark, result)
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