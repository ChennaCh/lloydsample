package com.chenna.data.datasource

import com.chenna.data.service.ITvShowService
import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.utils.BaseApiResponse
import com.chenna.domain.utils.FailedResponse
import com.chenna.domain.utils.NetworkResult
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
class TVShowRemoteDataSource @Inject constructor(private val retrofit: Retrofit) :
    BaseRemoteDataSource() {

    private val requestService: ITvShowService by lazy { retrofit.create(ITvShowService::class.java) }
    suspend fun getTvShows(): NetworkResult<List<ShowEntity>?, FailedResponse, Exception> {
        return performSafeApiRequestCall {
            requestService.getTvShows()
        }
    }

    suspend fun getTvShowById(showId: String): NetworkResult<BaseApiResponse<ShowEntity>?, FailedResponse, Exception> {
        return performSafeApiRequestCall {
            requestService.getTvShowById(showId)
        }
    }
}