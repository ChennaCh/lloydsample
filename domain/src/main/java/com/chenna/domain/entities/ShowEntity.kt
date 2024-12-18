package com.chenna.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Chenna Rao on 12/08/24.
 * <p>
 * Frost Interactive
 */
@Entity(tableName = "tvshow")
data class ShowEntity(
    @PrimaryKey val id: Int,
    val name: String?,
    val type: String?,
    val language: String?,
    val genres: List<String>?,
    val status: String?,
    val runtime: Int?,
    val rating: ShowRatingModel?,
    val network: NetWorkModel?,
    val weight: Int,
    val image: ShowImageModel?,
    val summary: String,
)

data class NetWorkModel(
    val country: CountryModel,
)

data class CountryModel(
    val name: String?,
)

data class ShowRatingModel(
    val average: Float,
)

data class ShowImageModel(
    val medium: String,
    val original: String,
)

