package com.chenna.lloydsamplepoject.models

import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.model.ShowModel

data class ResultActionStateModel(
    val list: List<ShowModel> = listOf(),
    val bookMarks: List<ShowEntity> = listOf(),
)
