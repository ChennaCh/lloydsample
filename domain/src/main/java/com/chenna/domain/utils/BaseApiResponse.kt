package com.chenna.domain.utils

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
data class BaseApiResponse<T>(
    val status: Boolean? = false,
    val data: T? = null,
    val message: String? = null,
)