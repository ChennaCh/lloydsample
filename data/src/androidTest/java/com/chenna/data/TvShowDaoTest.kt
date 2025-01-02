package com.chenna.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chenna.data.db.AppDatabase
import com.chenna.data.db.dao.TvShowDao
import com.chenna.domain.entities.CountryEntity
import com.chenna.domain.entities.NetworkEntity
import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.entities.ShowImageEntity
import com.chenna.domain.entities.ShowRatingEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Chenna Rao on 22/12/24.
 * <p>
 * Frost Interactive
 */

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TvShowDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var tvShowDao: TvShowDao

    @Before
    fun setup() {
        // Create an in-memory database for testing
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        tvShowDao = database.showDao()
    }

    @After
    fun tearDown() {
        // Close the database after tests
        database.close()
    }

    @Test
    fun saveBookmark_insertsAndReplacesData() = runTest {
        // Arrange
        val show = getShowItem()

        // Act
        tvShowDao.saveBookmark(show)
        val result =
            tvShowDao.getSavedBookMarks().find { it.id == show.id }

        // Assert
        assertNotNull(result)
        assertEquals(show.id, result?.id)
        assertEquals(show.name, result?.name)
    }

    @Test
    fun isShowBookmarkedReturnsCorrectValueWhenShowIsBookmarked() = runTest {
        // Arrange
        val show = getShowItem()
        tvShowDao.saveBookmark(show)

        // Act
        val isBookmarked = tvShowDao.isShowBookmarked(show.id)

        // Assert
        assertTrue(isBookmarked)
    }

    @Test
    fun removeBookmarkRemovesTheShowCorrectly() = runTest {
        // Arrange
        val show = getShowItem()
        tvShowDao.saveBookmark(show)

        // Act
        tvShowDao.removeBookmark(show.id)
        val result = tvShowDao.getSavedBookMarks()
            .find { it.id == show.id }

        // Assert
        assertNull(result)
    }

    @Test
    fun getSavedBookMarksReturnsAllBookmarkedShows() = runTest {
        // Arrange
        val shows = getShowList()
        shows.forEach { tvShowDao.saveBookmark(it) }

        // Act
        val result = tvShowDao.getSavedBookMarks()

        // Assert
        assertNotNull(result)
        assertEquals(shows.size, result.size)
        shows.forEachIndexed { index, show ->
            assertEquals(show.id, result[index].id)
        }
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
            rating = ShowRatingEntity(average = 6.5f),
            weight = 98,
            type = "Scripted",
            network = NetworkEntity(country = CountryEntity(name = "United States")),
            image = ShowImageEntity(
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
            network = NetworkEntity(country = CountryEntity(name = "United States")),
            rating = ShowRatingEntity(average = 9.5f),
            weight = 100,
            image = ShowImageEntity(
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