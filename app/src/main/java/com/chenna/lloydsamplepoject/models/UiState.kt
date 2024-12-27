package com.chenna.lloydsamplepoject.models

import com.chenna.domain.models.Error

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
data class UiState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val showSearchMessage: Boolean = false,
    val error: Error? = null,
)