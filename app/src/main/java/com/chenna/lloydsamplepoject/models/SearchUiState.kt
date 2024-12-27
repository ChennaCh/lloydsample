package com.chenna.lloydsamplepoject.models

import com.chenna.domain.models.Error

/**
 * Created by Chenna Rao on 27/12/24.
 * <p>
 * Frost Interactive
 */
data class SearchUiState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val showSearchMessage: Boolean = true,
    val error: Error? = null,
    var query: String? = "",
)