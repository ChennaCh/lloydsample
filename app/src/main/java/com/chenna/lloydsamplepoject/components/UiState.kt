package com.chenna.lloydsamplepoject.components

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
    val balance: Double? = 0.0,
)