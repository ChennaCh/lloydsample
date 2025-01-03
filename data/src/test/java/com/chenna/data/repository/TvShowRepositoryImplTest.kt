package com.chenna.data.repository

import com.chenna.data.datasource.TvShowLocalDataSource
import com.chenna.data.datasource.TvShowRemoteDataSource
import com.chenna.domain.entities.CountryEntity
import com.chenna.domain.entities.NetworkEntity
import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.entities.ShowImageEntity
import com.chenna.domain.entities.ShowRatingEntity
import com.chenna.domain.models.CastModel
import com.chenna.domain.models.CountryModel
import com.chenna.domain.models.NetWorkModel
import com.chenna.domain.models.NetworkResult
import com.chenna.domain.models.PersonCountryModel
import com.chenna.domain.models.PersonImageModel
import com.chenna.domain.models.PersonModel
import com.chenna.domain.models.SearchShowModel
import com.chenna.domain.models.ShowImageModel
import com.chenna.domain.models.ShowModel
import com.chenna.domain.models.ShowRatingModel
import com.chenna.domain.models.toShowEntity
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Created by Chenna Rao on 22/12/24.
 * <p>
 * Frost Interactive
 */

class TvShowRepositoryImplTest {

    private lateinit var remoteDataSource: TvShowRemoteDataSource
    private lateinit var localDataSource: TvShowLocalDataSource
    private lateinit var repository: TvShowRepositoryImpl

    @Before
    fun setUp() {
        remoteDataSource = mockk()
        localDataSource = mockk()
        repository = TvShowRepositoryImpl(remoteDataSource, localDataSource)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `test getListOfShows returns success`() = runBlocking {
        val mockShows = getShowList()
        val mockResult = NetworkResult.Success(mockShows)

        coEvery { remoteDataSource.getTvShows() } returns mockResult

        val result = repository.getListOfShows()

        assertTrue(result is NetworkResult.Success)
        assertEquals(mockShows, (result as NetworkResult.Success).data)
        coVerify { remoteDataSource.getTvShows() }
    }

    @Test
    fun `test saveBookmark`() = runBlocking {
        val mockShow = getShowItem()

        coEvery { localDataSource.saveBookMark(mockShow) } just Runs

        repository.saveBookmark(mockShow)

        coVerify { localDataSource.saveBookMark(mockShow) }
    }

    @Test
    fun `test removeBookmark`() = runBlocking {
        val mockShow = getShowItem()

        coEvery { localDataSource.deleteBookMark(mockShow.id) } just Runs

        repository.removeBookmark(mockShow.id)

        coVerify { localDataSource.deleteBookMark(mockShow.id) }
    }

    @Test
    fun `test getAllBookmarks`() = runBlocking {
        val mockBookmarks = getShowList().map { it.toShowEntity() }

        coEvery { localDataSource.getBookmarkedTvShows() } returns mockBookmarks

        val result = repository.getAllBookmarks()

        assertEquals(mockBookmarks, result)
        coVerify { localDataSource.getBookmarkedTvShows() }
    }

    @Test
    fun `test isShowBookmarked returns true`() = runBlocking {
        val mockId = 1

        coEvery { localDataSource.isShowBookmarked(mockId) } returns true

        val result = repository.isShowBookmarked(mockId)

        assertTrue(result)
        coVerify { localDataSource.isShowBookmarked(mockId) }
    }

    @Test
    fun `test isShowBookmarked returns false`() = runBlocking {
        val mockId = 2

        coEvery { localDataSource.isShowBookmarked(mockId) } returns false

        val result = repository.isShowBookmarked(mockId)

        assertFalse(result)
        coVerify { localDataSource.isShowBookmarked(mockId) }
    }

    @Test
    fun `test getTvShowCastAndCrews returns success`() = runBlocking {
        val mockShows = getShowCasts()
        val mockResult = NetworkResult.Success(mockShows)

        coEvery { remoteDataSource.fetchCasts() } returns mockResult

        val result = repository.fetchCasts()

        assertTrue(result is NetworkResult.Success)
        assertEquals(mockShows, (result as NetworkResult.Success).data)
        coVerify { remoteDataSource.fetchCasts() }
    }

    @Test
    fun `test getSearchList returns success`() = runBlocking {
        val mockSearchResults = getSearchShowList() // Reusing getShowList() for mock data
        val mockResult = NetworkResult.Success(mockSearchResults)

        coEvery { remoteDataSource.getSearchList("query") } returns mockResult

        val result = repository.getSearchList("query")

        assertTrue(result is NetworkResult.Success)
        assertEquals(mockSearchResults, (result as NetworkResult.Success).data)
        coVerify { remoteDataSource.getSearchList("query") }
    }

}

fun getShowList(): List<ShowModel> {
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

fun getShowCasts(): List<CastModel> {
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

fun getShowItem(): ShowEntity {
    return ShowEntity(
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
}
