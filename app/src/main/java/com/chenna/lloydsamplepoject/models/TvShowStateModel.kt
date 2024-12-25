package com.chenna.lloydsamplepoject.models

import com.chenna.domain.model.ShowModel

data class TvShowStateModel(
    val list: List<ShowModel> = listOf(),
    val isRefreshing: Boolean = false
)
