package com.chenna.data.repository

import com.chenna.data.datasource.TVShowLocalDataSource
import com.chenna.data.datasource.TVShowRemoteDataSource
import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.repository.TvShowRepository
import com.chenna.domain.utils.FailedResponse
import com.chenna.domain.utils.NetworkResult
import javax.inject.Inject

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
class TvShowRepositoryImpl @Inject constructor(
    private val dataSource: TVShowRemoteDataSource,
    private val localDataSource: TVShowLocalDataSource,
) : TvShowRepository {

    override suspend fun getListOfShows(): NetworkResult<List<ShowEntity>?, FailedResponse, Exception> {
        return dataSource.getTvShows()
    }

    override suspend fun saveBookmark(showEntity: ShowEntity) {
        localDataSource.saveBookMark(showEntity)
    }

    override suspend fun removeBookmark(showEntity: ShowEntity) {
        localDataSource.deleteBookMark(showEntity)
    }

    override suspend fun getAllBookmarks(): List<ShowEntity> {
        return localDataSource.getBookmarkedTvShows()
    }

    override suspend fun isShowBookmarked(id: Int): Boolean {
        return localDataSource.isShowBookmarked(id)
    }
}