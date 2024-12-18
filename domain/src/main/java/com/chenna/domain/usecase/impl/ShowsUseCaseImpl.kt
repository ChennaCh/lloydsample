package com.chenna.domain.usecase.impl

import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.repository.TvShowRepository
import com.chenna.domain.usecase.ShowsUseCase
import com.chenna.domain.utils.ResponseUtils
import com.chenna.domain.utils.Work
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
class ShowsUseCaseImpl @Inject constructor(
    private val repository: TvShowRepository,
) : ShowsUseCase {

    override suspend fun getListOfShows(): Work<List<ShowEntity>>? {
        val result = withContext(Dispatchers.IO) {
            repository.getListOfShows()
        }

        return ResponseUtils.parseNetworkResults(result)
    }

    override suspend fun getTvShowById(showId: String): Work<ShowEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun saveBookmark(showEntity: ShowEntity) {
        repository.saveBookmark(showEntity)
    }

    override suspend fun removeBookmark(showEntity: ShowEntity) {
        repository.removeBookmark(showEntity)
    }

    override suspend fun getAllBookmarks(): List<ShowEntity> {
        return repository.getAllBookmarks()
    }

    override suspend fun isShowBookmarked(id:Int): Boolean {
        return repository.isShowBookmarked(id)
    }

}