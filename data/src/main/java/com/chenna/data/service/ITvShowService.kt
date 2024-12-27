package com.chenna.data.service

import com.chenna.domain.config.Constants
import com.chenna.domain.models.CastModel
import com.chenna.domain.models.SearchShowModel
import com.chenna.domain.models.ShowModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
interface ITvShowService {

    @GET(Constants.Apis.GET_TV_SHOWS)
    suspend fun getTvShows(): Response<List<ShowModel>>

    @GET(Constants.Apis.GET_CASTS)
    suspend fun fetchCasts(): Response<List<CastModel>>

    @GET(Constants.Apis.SEARCH_SHOW)
    suspend fun getSearchList(@Query("q") q: String): Response<List<SearchShowModel>>

}
