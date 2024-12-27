package com.chenna.data

import com.chenna.data.datasource.TvShowLocalDataSource
import com.chenna.data.db.dao.TvShowDao
import com.chenna.domain.entities.CountryEntity
import com.chenna.domain.entities.NetworkEntity
import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.entities.ShowImageEntity
import com.chenna.domain.entities.ShowRatingEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Chenna Rao on 19/12/24.
 * <p>
 * Frost Interactive
 */

class TvShowLocalDataSourceTest {

    private val mockDao: TvShowDao = mockk(relaxed = true)
    private val localDataSource = TvShowLocalDataSource(mockDao)

    @Test
    fun `test getBookmarkedTvShows`() = runBlocking {
        // Arrange
        val mockShows = listOf(
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
        coEvery { mockDao.getSavedBookMarks() } returns mockShows

        // Act
        val result = localDataSource.getBookmarkedTvShows()

        // Assert
        assertEquals(mockShows, result)
    }

    @Test
    fun `test saveBookMark`() = runBlocking {
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

        // Act
        localDataSource.saveBookMark(mockShow)

        // Assert
        coVerify { mockDao.saveBookmark(mockShow) }
    }

    @Test
    fun `test deleteBookMark`() = runBlocking {
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

        // Act
        localDataSource.deleteBookMark(mockShow.id)

        // Assert
        coVerify { mockDao.removeBookmark(mockShow.id) }
    }

    @Test
    fun `test isShowBookmarked`() = runBlocking {
        // Arrange
        val mockId = 1
        coEvery { mockDao.isShowBookmarked(mockId) } returns true

        // Act
        val result = localDataSource.isShowBookmarked(mockId)

        // Assert
        assertEquals(true, result)
    }
}
