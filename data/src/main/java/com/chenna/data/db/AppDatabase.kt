package com.chenna.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chenna.data.db.converter.DateConverter
import com.chenna.data.db.dao.TVShowDao
import com.chenna.domain.entities.ShowEntity

/**
 * Created by Chenna Rao on 18/12/24.
 * <p>
 * Frost Interactive
 */
@Database(entities = [ShowEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun showDao(): TVShowDao
}