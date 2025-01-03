package com.chenna.domain.models

import okhttp3.ResponseBody


/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
sealed class NetworkResult<out A, out B, out C> {
    data class Success<out A>(val data: A) : NetworkResult<A, Nothing, Nothing>()
    data class Failed<out B>(val errorResponse: B) : NetworkResult<Nothing, B, Nothing>()
    data class Error<out C>(val exception: C) : NetworkResult<Nothing, Nothing, C>()
    object NetworkConnection : NetworkResult<Nothing, Nothing, Nothing>()

    val isSuccess get() = this is Success<A>
    val isFailed get() = this is Failed<B>
    val isError get() = this is Error<C>
}

data class FailedResponse(val code: Int, val errorResponse: ResponseBody?)