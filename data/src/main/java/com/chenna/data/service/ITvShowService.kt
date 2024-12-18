package com.chenna.data.service

import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.utils.BaseApiResponse
import com.chenna.domain.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
interface ITvShowService {

    @GET(Constants.Apis.GET_TV_SHOWS)
    suspend fun getTvShows(): Response<List<ShowEntity>>

    @GET(Constants.Apis.GET_SHOW_DETAILS_BY_ID)
    suspend fun getTvShowById(@Path("showId") showId: String): Response<BaseApiResponse<ShowEntity>>
}