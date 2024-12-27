package com.chenna.data.repository

import com.chenna.data.datasource.TvShowLocalDataSource
import com.chenna.data.datasource.TvShowRemoteDataSource
import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.model.CastModel
import com.chenna.domain.model.ShowModel
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
    private val remoteDataSource: TvShowRemoteDataSource,
    private val localDataSource: TvShowLocalDataSource,
) : TvShowRepository {

    override suspend fun getListOfShows(): NetworkResult<List<ShowModel>?, FailedResponse, Exception> {
        return remoteDataSource.getTvShows()
    }

    override suspend fun fetchCasts(): NetworkResult<List<CastModel>?, FailedResponse, Exception> {
        return remoteDataSource.fetchCasts()
    }

    override suspend fun saveBookmark(showEntity: ShowEntity) {
        localDataSource.saveBookMark(showEntity)
    }

    override suspend fun removeBookmark(id: Int) {
        localDataSource.deleteBookMark(id)
    }

    override suspend fun getAllBookmarks(): List<ShowEntity> {
        return localDataSource.getBookmarkedTvShows()
    }

    override suspend fun isShowBookmarked(id: Int): Boolean {
        return localDataSource.isShowBookmarked(id)
    }
}