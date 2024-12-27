package com.chenna.domain.repository

import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.models.CastModel
import com.chenna.domain.models.ShowModel
import com.chenna.domain.models.FailedResponse
import com.chenna.domain.models.NetworkResult

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
interface TvShowRepository {
    suspend fun getListOfShows(): NetworkResult<List<ShowModel>?, FailedResponse, Exception>
    suspend fun fetchCasts(): NetworkResult<List<CastModel>?, FailedResponse, Exception>

    suspend fun saveBookmark(
        showEntity: ShowEntity,
    )

    suspend fun removeBookmark(
        id: Int,
    )

    suspend fun getAllBookmarks(
    ): List<ShowEntity>

    suspend fun isShowBookmarked(id: Int): Boolean

}