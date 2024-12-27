package com.chenna.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class SearchShowModel(

    val show: ShowModel,

    ) : Parcelable
