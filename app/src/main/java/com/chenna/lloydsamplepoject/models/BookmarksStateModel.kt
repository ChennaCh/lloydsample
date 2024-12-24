package com.chenna.lloydsamplepoject.models

import com.chenna.domain.entities.ShowEntity

data class BookmarksStateModel(
    val bookMarks: List<ShowEntity> = listOf()
)