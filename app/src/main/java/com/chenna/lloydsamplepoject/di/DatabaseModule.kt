package com.chenna.lloydsamplepoject.di

import android.content.Context
import androidx.room.Room
import com.chenna.data.db.AppDatabase
import com.chenna.data.db.dao.TvShowDao
import com.chenna.lloydsamplepoject.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Chenna Rao on 18/12/24.
 * <p>
 * Frost Interactive
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideShowsDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.DB.NAME
        ).build()
    }

    @Provides
    fun provideShowDao(database: AppDatabase): TvShowDao {
        return database.showDao()
    }
}
