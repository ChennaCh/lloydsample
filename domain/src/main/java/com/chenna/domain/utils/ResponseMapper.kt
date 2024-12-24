package com.chenna.domain.utils

import com.google.gson.JsonParser
import okhttp3.ResponseBody

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
object ResponseMapper {

    fun <T> parseNetworkResults(
        networkResult: NetworkResult<List<T>?, FailedResponse, Exception>,
        backfireMessage: String = Constants.SOMETHING_WENT_WRONG,
        messageType: MessageType = MessageType.TOAST,
    ): Work<List<T>> {
        return when (networkResult) {
            is NetworkResult.Success -> {
                networkResult.data?.let { data ->
                    return Work.result(
                        data = data, message = Message(
                            message = "Success"
                        )
                    )
                }
                Work.backfire(exception = RuntimeException(backfireMessage))
            }

            is NetworkResult.Failed -> {
                val error = parseErrorResponseBody(networkResult.errorResponse.errorResponse)
                Work.stop(
                    Message(
                        message = error,
                        messageType = messageType
                    )
                )
            }

            is NetworkResult.Error -> {
                Work.backfire(exception = networkResult.exception)
            }

            NetworkResult.NetworkConnection -> {
                Work.Stop(
                    message = Message(
                        message = Constants.CONNECTION_ERROR,
                        messageType = messageType
                    )
                )
            }
        }
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