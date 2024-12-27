package com.chenna.data.datasource

import android.content.Context
import com.chenna.domain.models.FailedResponse
import com.chenna.domain.models.NetworkResult
import com.chenna.domain.utils.NetworkUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
abstract class BaseRemoteDataSource {

    @Inject
    @ApplicationContext
    lateinit var context: Context

    suspend fun <T> performSafeApiRequestCall(function: suspend () -> Response<T>): NetworkResult<T?, FailedResponse, Exception> {

        if (!NetworkUtils.isInternetAvailable(context)) {
            return NetworkResult.NetworkConnection
        }

        return try {
            val result = function.invoke()
            return when (result.isSuccessful) {
                true -> {
                    NetworkResult.Success(result.body())
                }

                else -> {
                    NetworkResult.Failed(FailedResponse(result.code(), result.errorBody()))
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            NetworkResult.Error(e)
        }
    }
}