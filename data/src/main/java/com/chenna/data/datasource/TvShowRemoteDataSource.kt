package com.chenna.data.datasource

import com.chenna.data.service.ITvShowService
import com.chenna.domain.models.CastModel
import com.chenna.domain.models.FailedResponse
import com.chenna.domain.models.NetworkResult
import com.chenna.domain.models.SearchShowModel
import com.chenna.domain.models.ShowModel
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
class TvShowRemoteDataSource @Inject constructor(private val retrofit: Retrofit) :
    BaseRemoteDataSource() {

    private val requestService: ITvShowService by lazy { retrofit.create(ITvShowService::class.java) }
    suspend fun getTvShows(): NetworkResult<List<ShowModel>?, FailedResponse, Exception> {
        return performSafeApiRequestCall {
            requestService.getTvShows()
        }
    }

    suspend fun fetchCasts(): NetworkResult<List<CastModel>?, FailedResponse, Exception> {
        return performSafeApiRequestCall {
            requestService.fetchCasts()
        }
    }

    suspend fun getSearchList(text: String): NetworkResult<List<SearchShowModel>?, FailedResponse, Exception> {
        return performSafeApiRequestCall {
            requestService.getSearchList(q = text)
        }
    }
}