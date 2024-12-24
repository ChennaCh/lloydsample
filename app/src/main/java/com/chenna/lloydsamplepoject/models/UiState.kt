package com.chenna.lloydsamplepoject.models

import com.chenna.domain.utils.Error

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
data class UiState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: Error? = null,
)