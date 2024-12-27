package com.chenna.domain.utils

import com.chenna.domain.config.Constants
import com.chenna.domain.models.FailedResponse
import com.chenna.domain.models.Message
import com.chenna.domain.models.MessageType
import com.chenna.domain.models.NetworkResult
import com.chenna.domain.models.Work
import com.google.gson.JsonParser
import okhttp3.ResponseBody

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
object ResponseMapper {

    fun <T> map(
        networkResult: NetworkResult<T?, FailedResponse, Exception>,
        backfireMessage: String = Constants.SOMETHING_WENT_WRONG,
        messageType: MessageType = MessageType.TOAST,
    ): Work<T> {
        return when (networkResult) {
            is NetworkResult.Success -> handleSuccess(networkResult, backfireMessage)
            is NetworkResult.Failed -> handleFailed(networkResult, messageType)
            is NetworkResult.Error -> handleError(networkResult.exception)
            NetworkResult.NetworkConnection -> handleNetworkConnection(messageType)
        }
    }

    private fun <T> handleSuccess(
        networkResult: NetworkResult.Success<T?>,
        backfireMessage: String,
    ): Work<T> {
        networkResult.data?.let { data ->
            return Work.result(
                data = data, message = Message(
                    message = "Success"
                )
            )
        }
        return Work.backfire(exception = RuntimeException(backfireMessage))
    }

    private fun <T> handleFailed(
        networkResult: NetworkResult.Failed<FailedResponse>,
        messageType: MessageType,
    ): Work<T> {
        val error = parseErrorResponseBody(networkResult.errorResponse.errorResponse)
        return Work.stop(
            Message(
                message = error,
                messageType = messageType
            )
        )
    }

    private fun <T> handleNetworkConnection(messageType: MessageType): Work<T> {
        return Work.stop(
            message = Message(
                message = Constants.CONNECTION_ERROR,
                messageType = messageType
            )
        )
    }


    private fun <T> handleError(exception: Exception): Work<T> {
        return Work.backfire(exception = exception)
    }


    private fun parseErrorResponseBody(body: ResponseBody?): String {
        return body?.string()?.let {
            try {
                JsonParser.parseString(it).asJsonObject.get("message").asString
            } catch (e: Exception) {
                Constants.SOMETHING_WENT_WRONG
            }
        } ?: Constants.SOMETHING_WENT_WRONG
    }
}