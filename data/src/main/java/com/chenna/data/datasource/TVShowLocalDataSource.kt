package com.chenna.data.datasource

import com.chenna.data.db.dao.TVShowDao
import com.chenna.domain.entities.ShowEntity
import javax.inject.Inject

/**
 * Created by Chenna Rao on 18/12/24.
 * <p>
 * Frost Interactive
 */
class TVShowLocalDataSource @Inject constructor(private val tvShowDao: TVShowDao) {
    suspend fun getBookmarkedTvShows(): List<ShowEntity> {
        return tvShowDao.getSavedBookMarks()
    }

    suspend fun saveBookMark(showEntity: ShowEntity) {
        tvShowDao.saveBookmark(showEntity)
    }

    suspend fun deleteBookMark(showEntity: ShowEntity) {
        tvShowDao.removeBookmark(showEntity)
    }

    suspend fun isShowBookmarked(id: Int): Boolean {
        return tvShowDao.isShowBookmarked(showId = id)
    }
}