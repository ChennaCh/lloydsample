package com.chenna.domain.models

import android.os.Parcelable
import com.chenna.domain.entities.CountryEntity
import com.chenna.domain.entities.NetworkEntity
import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.entities.ShowImageEntity
import com.chenna.domain.entities.ShowRatingEntity
import kotlinx.parcelize.Parcelize

/**
 * Created by Chenna Rao on 23/12/24.
 * <p>
 * Frost Interactive
 */
@Parcelize
data class ShowModel(
    val id: Int,
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
) : Parcelable

@Parcelize
data class NetWorkModel(
    val country: CountryModel,
) : Parcelable

@Parcelize
data class CountryModel(
    val name: String?,
) : Parcelable

@Parcelize
data class ShowRatingModel(
    val average: Float,
) : Parcelable

@Parcelize
data class ShowImageModel(
    val medium: String,
    val original: String,
) : Parcelable

fun ShowRatingModel.toShowRatingEntity(): ShowRatingEntity {
    return ShowRatingEntity(average = this.average)
}

fun NetWorkModel.toNetworkEntity(): NetworkEntity {
    return NetworkEntity(country = this.country.toCountryEntity())
}

fun CountryModel.toCountryEntity(): CountryEntity {
    return CountryEntity(name = this.name ?: "")
}

fun ShowImageModel.toShowImageEntity(): ShowImageEntity {
    return ShowImageEntity(medium = this.medium, original = this.original)
}


fun ShowModel.toShowEntity(): ShowEntity {
    return ShowEntity(
        id = this.id,
        name = this.name.orEmpty(),
        type = this.type.orEmpty(),
        language = this.language.orEmpty(),
        genres = this.genres ?: emptyList(),
        status = this.status.orEmpty(),
        runtime = this.runtime ?: 0,
        rating = this.rating?.toShowRatingEntity() ?: ShowRatingEntity(average = 0f),
        network = this.network?.toNetworkEntity() ?: NetworkEntity(CountryEntity(name = "")),
        weight = this.weight,
        image = this.image?.toShowImageEntity() ?: ShowImageEntity(medium = "", original = ""),
        summary = this.summary.orEmpty()
    )
}

// ShowEntity to ShowModel
fun ShowEntity.toShowModel(): ShowModel {
    return ShowModel(
        id = this.id,
        name = this.name,
        type = this.type,
        language = this.language,
        genres = this.genres,
        status = this.status,
        runtime = this.runtime,
        rating = this.rating.toShowRatingModel(),
        network = this.network.toNetworkModel(),
        weight = this.weight,
        image = this.image.toShowImageModel(),
        summary = this.summary
    )
}

// Rating Entity to ShowRatingModel
fun ShowRatingEntity.toShowRatingModel(): ShowRatingModel {
    return ShowRatingModel(
        average = this.average
    )
}

// Network Entity to NetworkModel
fun NetworkEntity.toNetworkModel(): NetWorkModel {
    return NetWorkModel(
        country = this.country.toCountryModel()
    )
}

// Country Entity to CountryModel
fun CountryEntity.toCountryModel(): CountryModel {
    return CountryModel(
        name = this.name
    )
}

// Image Entity to ShowImageModel
fun ShowImageEntity.toShowImageModel(): ShowImageModel {
    return ShowImageModel(
        medium = this.medium,
        original = this.original
    )
}
