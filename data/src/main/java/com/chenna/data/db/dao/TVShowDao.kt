package com.chenna.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chenna.domain.entities.ShowEntity

/**
 * Created by Chenna Rao on 18/12/24.
 * <p>
 * Frost Interactive
 */

@Dao
interface TVShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBookmark(show: ShowEntity)

    @Query("SELECT COUNT(*) FROM show WHERE id = :showId")
    suspend fun isShowBookmarked(showId: Int): Boolean

    @Query("DELETE FROM show WHERE id = :showId")
    suspend fun removeBookmark(showId: Int)

    @Query("SELECT * FROM show")
    suspend fun getSavedBookMarks(): List<ShowEntity>
}