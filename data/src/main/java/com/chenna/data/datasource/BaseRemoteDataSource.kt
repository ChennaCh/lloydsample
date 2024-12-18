package com.chenna.data.datasource

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import android.content.Intent
import com.chenna.domain.utils.Constants
import com.chenna.domain.utils.FailedResponse
import com.chenna.domain.utils.NetworkResult
import com.chenna.domain.utils.NetworkUtils
import retrofit2.Response

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
                    if (result.code() == 401) {
                        forceLogout()
                    }
                    NetworkResult.Failed(FailedResponse(result.code(), result.errorBody()))
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            NetworkResult.Error(e)
        }
    }

    private fun forceLogout() {
        val intent = Intent(Constants.Action.LOGOUT_INTENT).apply {
            putExtra("logout", true)
        }
        context.sendBroadcast(intent)
    }
}