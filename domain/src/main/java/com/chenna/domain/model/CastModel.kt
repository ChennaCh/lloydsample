package com.chenna.domain.model

/**
 * Created by Chenna Rao on 25/12/24.
 * <p>
 * Frost Interactive
 */
data class CastModel(
    val person: PersonModel,
)

data class PersonModel(
    val id: String,
    val name: String,
    val birthday: String,
    val country: PersonCountryModel,
    val gender: String,
    val image: PersonImageModel,
)

data class PersonCountryModel(
    val name: String?,
)

data class PersonImageModel(
    val medium: String,
    val original: String,
)