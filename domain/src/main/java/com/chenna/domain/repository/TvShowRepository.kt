package com.chenna.domain.repository

import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.utils.BaseApiResponse
import com.chenna.domain.utils.FailedResponse
import com.chenna.domain.utils.NetworkResult

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
interface TvShowRepository {
    suspend fun getListOfShows(
    ): NetworkResult<List<ShowEntity>?, FailedResponse, Exception>

    suspend fun saveBookmark(
        showEntity: ShowEntity,
    )

    suspend fun removeBookmark(
        showEntity: ShowEntity,
    )

    suspend fun getAllBookmarks(
    ): List<ShowEntity>

    suspend fun isShowBookmarked(id: Int): Boolean

}