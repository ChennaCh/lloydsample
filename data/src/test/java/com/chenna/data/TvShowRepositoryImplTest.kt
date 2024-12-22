package com.chenna.data

import com.chenna.data.datasource.TVShowLocalDataSource
import com.chenna.data.datasource.TVShowRemoteDataSource
import com.chenna.data.repository.TvShowRepositoryImpl
import com.chenna.domain.entities.CountryModel
import com.chenna.domain.entities.NetWorkModel
import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.entities.ShowImageModel
import com.chenna.domain.entities.ShowRatingModel
import com.chenna.domain.utils.NetworkResult
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

    private lateinit var remoteDataSource: TVShowRemoteDataSource
    private lateinit var localDataSource: TVShowLocalDataSource
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

        coEvery { localDataSource.deleteBookMark(mockShow) } just Runs

        repository.removeBookmark(mockShow)

        coVerify { localDataSource.deleteBookMark(mockShow) }
    }

    @Test
    fun `test getAllBookmarks`() = runBlocking {
        val mockBookmarks = getShowList()

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
