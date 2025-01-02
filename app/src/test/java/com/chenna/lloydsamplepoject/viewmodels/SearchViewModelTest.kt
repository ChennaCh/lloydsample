import com.chenna.domain.models.CountryModel
import com.chenna.domain.models.Error
import com.chenna.domain.models.Message
import com.chenna.domain.models.NetWorkModel
import com.chenna.domain.models.SearchShowModel
import com.chenna.domain.models.ShowImageModel
import com.chenna.domain.models.ShowModel
import com.chenna.domain.models.ShowRatingModel
import com.chenna.domain.models.Work
import com.chenna.domain.usecase.ShowsUseCase
import com.chenna.lloydsamplepoject.config.Constants
import com.chenna.lloydsamplepoject.models.SearchActionEvent
import com.chenna.lloydsamplepoject.util.NavigationEvent
import com.chenna.lloydsamplepoject.viewmodels.SearchViewModel
import com.chenna.lloydsamplepoject.viewmodels.collectEvents
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
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Test suite for [SearchViewModel].
 * Verifies fetching search functionality.
 *
 * Created by Chenna Rao on 27/12/24.
 */
@ExperimentalCoroutinesApi
class SearchViewModelTest {

    @MockK
    private lateinit var useCase: ShowsUseCase

    private lateinit var viewModel: SearchViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = SearchViewModel(useCase)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset main dispatcher
    }

    @Test
    fun `getSearchList updates resultState with fetched data`() = runTest {
        val query = "Breaking Bad"
        val searchResults = getSearchShowList()
        coEvery { useCase.getSearchList(query) } returns Work.Result(data = searchResults)

        viewModel.onActionEvent(SearchActionEvent.FetchSearchTvShow(query))

        testDispatcher.scheduler.advanceUntilIdle()

        val resultState = viewModel.resultState.value
        assertFalse(resultState.isLoading)
        assertNull(resultState.error)
        assertNotNull(resultState.data)
        assertEquals(searchResults, resultState.data?.searchList)
        coVerify(exactly = 1) { useCase.getSearchList(query) }
    }

    @Test
    fun `getSearchList updates resultState with no results`() = runTest {
        val query = "NonExistentShow"
        coEvery { useCase.getSearchList(query) } returns Work.Result(data = emptyList())

        viewModel.onActionEvent(SearchActionEvent.FetchSearchTvShow(query))

        testDispatcher.scheduler.advanceUntilIdle()

        val resultState = viewModel.resultState.value
        assertFalse(resultState.isLoading)
        assertNotNull(resultState.data)
        assertNull(resultState.error) // No error expected for no results found
        coVerify(exactly = 1) { useCase.getSearchList(query) }
    }

    @Test
    fun `getSearchList updates resultState with error`() = runTest {
        val query = "ErrorShow"
        val errorMessage = Message(message = "Error fetching search results")
        coEvery { useCase.getSearchList(query) } returns Work.Stop(errorMessage)

        viewModel.onActionEvent(SearchActionEvent.FetchSearchTvShow(query))

        testDispatcher.scheduler.advanceUntilIdle()

        val resultState = viewModel.resultState.value
        assertFalse(resultState.isLoading)
        assertNotNull(resultState.error)
        assertEquals(errorMessage.message, resultState.error?.title)
        assertEquals(errorMessage.message, resultState.error?.description)
        coVerify(exactly = 1) { useCase.getSearchList(query) }
    }

    @Test
    fun `getSearchList updates resultState with handleConnectionError`() = runTest {
        val query = "NetworkErrorShow"
        val errorMessage = Error(
            title = Constants.Errors.CONNECTION_ERROR,
            description = Constants.Errors.NO_SHOWS_FOUND
        )
        coEvery { useCase.getSearchList(query) } returns Work.backfire(RuntimeException())

        viewModel.onActionEvent(SearchActionEvent.FetchSearchTvShow(query))

        testDispatcher.scheduler.advanceUntilIdle()

        val resultState = viewModel.resultState.value
        assertFalse(resultState.isLoading)
        assertNotNull(resultState.error)
        assertEquals(errorMessage.title, resultState.error?.title)
        assertEquals(errorMessage.description, resultState.error?.description)
        coVerify(exactly = 1) { useCase.getSearchList(query) }
    }


    @Test
    fun `redirectToTvShowDetails emits NavigationEvent`() = runTest {
        val showModel = getSearchShowItem()
        val actionEvent = SearchActionEvent.RedirectToShowDetails(showModel.show)
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
    fun `cancelJob cancels active job`() = runTest {
        val query = "Breaking Bad"
        coEvery { useCase.getSearchList(query) } coAnswers {
            Work.Result(data = getSearchShowList())
        }

        viewModel.onActionEvent(SearchActionEvent.FetchSearchTvShow(query))

        // Assert job is active before cancellation
        assertTrue(viewModel.isJobActive())

        viewModel.cancelJob()

        // Assert job is no longer active after cancellation
        assertFalse(viewModel.isJobActive())
    }


}

fun getSearchShowList(): List<SearchShowModel> {
    return listOf(
        SearchShowModel(
            show = ShowModel(
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
        ),
        SearchShowModel(
            show = ShowModel(
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
    )
}

fun getSearchShowItem(): SearchShowModel {
    return SearchShowModel(
        show = ShowModel(
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
    )
}
