package com.chenna.domain.usecase

import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.utils.Work

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
interface ShowsUseCase {
    suspend fun getListOfShows(): Work<List<ShowEntity>>?
    suspend fun getTvShowById(showId: String): Work<ShowEntity>
    suspend fun saveBookmark(showEntity: ShowEntity)
    suspend fun removeBookmark(showEntity: ShowEntity)
    suspend fun getAllBookmarks(): List<ShowEntity>
    suspend fun isShowBookmarked(id: Int): Boolean

}