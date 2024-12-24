package com.chenna.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Chenna Rao on 12/08/24.
 * <p>
 * Frost Interactive
 */
@Entity(tableName = "show")
data class ShowEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val type: String,
    val language: String,
    val genres: List<String>,
    val status: String,
    val runtime: Int,
    val rating: ShowRatingEntity,
    val network: NetworkEntity,
    val weight: Int,
    val image: ShowImageEntity,
    val summary: String,
)

data class NetworkEntity(
    val country: CountryEntity,
)

data class CountryEntity(
    val name: String?,
)

data class ShowRatingEntity(
    val average: Float,
)

data class ShowImageEntity(
    val medium: String,
    val original: String,
)

