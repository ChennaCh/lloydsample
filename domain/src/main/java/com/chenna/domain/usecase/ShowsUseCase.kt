package com.chenna.domain.usecase

import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.models.CastModel
import com.chenna.domain.models.ShowModel
import com.chenna.domain.models.Work

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
interface ShowsUseCase {
    suspend fun getListOfShows(): Work<List<ShowModel>>
    suspend fun fetchCasts(): Work<List<CastModel>>
    suspend fun saveBookmark(showEntity: ShowEntity)
    suspend fun removeBookmark(id: Int)
    suspend fun getAllBookmarks(): List<ShowEntity>
    suspend fun isShowBookmarked(id: Int): Boolean

}