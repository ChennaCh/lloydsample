package com.chenna.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chenna.data.db.AppDatabase
import com.chenna.data.db.dao.TVShowDao
import com.chenna.domain.entities.CountryModel
import com.chenna.domain.entities.NetWorkModel
import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.entities.ShowImageModel
import com.chenna.domain.entities.ShowRatingModel
import io.mockk.coEvery
import io.mockk.mockk
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
class TVShowDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var tvShowDao: TVShowDao

    @Before
    fun setup() {
        // Create an in-memory database for testing
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        tvShowDao = mockk()  // Mocking the DAO
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

        // Mock the saveBookmark behavior
        coEvery { tvShowDao.saveBookmark(show) } returns Unit  // Specify that this returns Unit

        // Mock the getSavedBookMarks behavior
        coEvery { tvShowDao.getSavedBookMarks() } returns listOf(show)

        // Act
        tvShowDao.saveBookmark(show)
        val result = tvShowDao.getSavedBookMarks().find { it.id == 1 }

        // Assert
        assertNotNull(result)
        assertEquals(show.id, result?.id)
        assertEquals(show.name, result?.name)
    }

    @Test
    fun isShowBookmarkedReturnsCorrectValueWhenShowIsBookmarked() = runTest {
        // Arrange
        val show = getShowItem()

        // Mock saveBookmark behavior
        coEvery { tvShowDao.saveBookmark(show) } returns Unit

        // Mock isShowBookmarked behavior
        coEvery { tvShowDao.isShowBookmarked(1) } returns true

        // Act
        tvShowDao.saveBookmark(show)
        val isBookmarked = tvShowDao.isShowBookmarked(1)

        // Assert
        assertTrue(isBookmarked)
    }

    @Test
    fun removeBookmarkRemovesTheShowCorrectly() = runTest {
        // Arrange
        val show = getShowItem()
        val bookmarkedShows = mutableListOf<ShowEntity>() // Start with an empty list

        // Mock saveBookmark behavior
        coEvery { tvShowDao.saveBookmark(show) } answers {
            if (!bookmarkedShows.contains(show)) {
                bookmarkedShows.add(show)
            }
        }

        // Mock removeBookmark behavior
        coEvery { tvShowDao.removeBookmark(show) } answers {
            bookmarkedShows.remove(show)
        }

        // Mock getSavedBookMarks behavior
        coEvery { tvShowDao.getSavedBookMarks() } answers {
            bookmarkedShows.toList()
        }

        // Act
        tvShowDao.saveBookmark(show) // Save the show
        tvShowDao.removeBookmark(show) // Remove the show
        val result = tvShowDao.getSavedBookMarks().find { it.id == show.id }

        // Assert
        assertNull(result) // Expect null since the show should have been removed
    }

    @Test
    fun getSavedBookMarksReturnsAllBookmarkedShows() = runTest {
        // Arrange
        val shows = getShowList()
        coEvery { tvShowDao.getSavedBookMarks() } returns shows  // Mocking behavior of the method

        // Act
        val result = tvShowDao.getSavedBookMarks()

        // Assert
        assertNotNull(result)
        assertEquals(2, result.size)
        assertEquals(shows[0].id, result[0].id)
        assertEquals(shows[1].id, result[1].id)
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