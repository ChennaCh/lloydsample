package com.chenna.domain.usecase.impl

import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.models.CastModel
import com.chenna.domain.models.ShowModel
import com.chenna.domain.repository.TvShowRepository
import com.chenna.domain.usecase.ShowsUseCase
import com.chenna.domain.utils.ResponseMapper
import com.chenna.domain.models.Work
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

    override suspend fun getListOfShows(): Work<List<ShowModel>> {
        val result = withContext(Dispatchers.IO) {
            repository.getListOfShows()
        }

        return ResponseMapper.map(result)
    }

    override suspend fun fetchCasts(): Work<List<CastModel>> {
        val result = withContext(Dispatchers.IO) {
            repository.fetchCasts()
        }

        return ResponseMapper.map(result)
    }

    override suspend fun saveBookmark(showEntity: ShowEntity) {
        withContext(Dispatchers.IO) {
            repository.saveBookmark(showEntity)
        }
    }

    override suspend fun removeBookmark(id: Int) {
        val result = withContext(Dispatchers.IO) {
            repository.removeBookmark(id)
        }
        return result
    }

    override suspend fun getAllBookmarks(): List<ShowEntity> {
        val result = withContext(Dispatchers.IO) {
            repository.getAllBookmarks()
        }
        return result
    }

    override suspend fun isShowBookmarked(id: Int): Boolean {
        val result = withContext(Dispatchers.IO) {
            repository.isShowBookmarked(id)
        }
        return result
    }

}