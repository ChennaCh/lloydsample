package com.chenna.lloydsamplepoject.models

import com.chenna.domain.models.SearchShowModel

/**
 * Created by Chenna Rao on 27/12/24.
 * <p>
 * Frost Interactive
 */
data class SearchStateModel(
    val searchList: List<SearchShowModel> = listOf(),
)
