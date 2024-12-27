package com.chenna.domain.models


/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
sealed class Work<out T> {
    data class Result<T>(val data: T, val message: Message? = null) : Work<T>()
    data class Stop(val message: Message) : Work<Nothing>()
    data class Backfire(val exception: Exception) : Work<Nothing>()

    companion object {
        fun <T> result(data: T, message: Message? = null): Result<T> = Result(data, message)
        fun stop(message: Message) = Stop(message)
        fun backfire(exception: Exception) = Backfire(exception)
    }
}