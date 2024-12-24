package com.chenna.domain

import com.chenna.domain.entities.CountryEntity
import com.chenna.domain.entities.NetworkEntity
import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.entities.ShowImageEntity
import com.chenna.domain.entities.ShowRatingEntity
import com.chenna.domain.model.CountryModel
import com.chenna.domain.model.NetWorkModel
import com.chenna.domain.model.ShowImageModel
import com.chenna.domain.model.ShowModel
import com.chenna.domain.model.ShowRatingModel
import com.chenna.domain.repository.TvShowRepository
import com.chenna.domain.usecase.impl.ShowsUseCaseImpl
import com.chenna.domain.utils.NetworkResult
import com.chenna.domain.utils.Work
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/**
 * Created by Chenna Rao on 19/12/24.
 * <p>
 * Frost Interactive
 */
class ShowsUseCaseImplTest {

    private lateinit var showsUseCaseImpl: ShowsUseCaseImpl
    private val repository: TvShowRepository = mockk()

    @Before
    fun setUp() {
        showsUseCaseImpl = ShowsUseCaseImpl(repository)
    }

    @Test
    fun `getListOfShows should return parsed network results`() = runTest {
        // Arrange
        val mockShows =
            listOf(
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
                ), ShowModel(
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
        val mockNetworkResult = NetworkResult.Success(mockShows)

        // Mock the repository behavior
        coEvery { repository.getListOfShows() } returns mockNetworkResult

        // Act: Call the use case method
        val result = showsUseCaseImpl.getListOfShows()

        // Assert: Verify the result is as expected
        assert(result is Work.Result)
        assertEquals(mockShows, (result as Work.Result).data)
    }

    @Test
    fun `saveBookmark should call repository with the correct data`() = runTest {
        // Arrange
        val mockShow = ShowEntity(
            id = 1,
            name = "Under the Dome",
            language = "English",
            genres = listOf("Drama", "Science-Fiction", "Thriller"),
            status = "Ended",
            runtime = 60,
            rating = ShowRatingEntity(average = 6.5f),
            weight = 98,
            type = "Scripted",
            network = NetworkEntity(country = CountryEntity(name = "United States")),
            image = ShowImageEntity(
                medium = "https://static.tvmaze.com/uploads/images/medium_portrait/81/202627.jpg",
                original = "https://static.tvmaze.com/uploads/images/original_untouched/81/202627.jpg"
            ),
            summary = "Under the Dome is the story of a small town sealed off by an enormous dome."
        )
        coEvery { repository.saveBookmark(mockShow) } returns Unit

        // Act
        showsUseCaseImpl.saveBookmark(mockShow)

        // Assert
        coVerify { repository.saveBookmark(mockShow) }
    }

    @Test
    fun `removeBookmark should call repository with the correct data`() = runTest {
        // Arrange
        val mockShow = ShowEntity(
            id = 1,
            name = "Under the Dome",
            language = "English",
            genres = listOf("Drama", "Science-Fiction", "Thriller"),
            status = "Ended",
            runtime = 60,
            rating = ShowRatingEntity(average = 6.5f),
            weight = 98,
            type = "Scripted",
            network = NetworkEntity(country = CountryEntity(name = "United States")),
            image = ShowImageEntity(
                medium = "https://static.tvmaze.com/uploads/images/medium_portrait/81/202627.jpg",
                original = "https://static.tvmaze.com/uploads/images/original_untouched/81/202627.jpg"
            ),
            summary = "Under the Dome is the story of a small town sealed off by an enormous dome."
        )
        coEvery { repository.removeBookmark(mockShow.id) } returns Unit

        // Act
        showsUseCaseImpl.removeBookmark(mockShow.id)

        // Assert
        coVerify { repository.removeBookmark(mockShow.id) }
    }

    @Test
    fun `getAllBookmarks should return the list of bookmarked shows`() = runTest {
        // Arrange
        val mockBookmarks = listOf(
            ShowEntity(
                id = 1,
                name = "Under the Dome",
                language = "English",
                genres = listOf("Drama", "Science-Fiction", "Thriller"),
                status = "Ended",
                runtime = 60,
                rating = ShowRatingEntity(average = 6.5f),
                weight = 98,
                type = "Scripted",
                network = NetworkEntity(country = CountryEntity(name = "United States")),
                image = ShowImageEntity(
                    medium = "https://static.tvmaze.com/uploads/images/medium_portrait/81/202627.jpg",
                    original = "https://static.tvmaze.com/uploads/images/original_untouched/81/202627.jpg"
                ),
                summary = "Under the Dome is the story of a small town sealed off by an enormous dome."
            )
        )
        coEvery { repository.getAllBookmarks() } returns mockBookmarks

        // Act
        val result = showsUseCaseImpl.getAllBookmarks()

        // Assert
        assertEquals(mockBookmarks, result)
        coVerify { repository.getAllBookmarks() }
    }

    @Test
    fun `isShowBookmarked should return true when show is bookmarked`() = runTest {
        // Arrange
        val mockId = 1
        coEvery { repository.isShowBookmarked(mockId) } returns true

        // Act
        val result = showsUseCaseImpl.isShowBookmarked(mockId)

        // Assert
        assertEquals(true, result)
        coVerify { repository.isShowBookmarked(mockId) }
    }
}
