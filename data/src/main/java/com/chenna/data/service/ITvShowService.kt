package com.chenna.data.service

import com.chenna.domain.model.ShowModel
import com.chenna.domain.utils.Constants
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
interface ITvShowService {

    @GET(Constants.Apis.GET_TV_SHOWS)
    suspend fun getTvShows(): Response<List<ShowModel>>

}
