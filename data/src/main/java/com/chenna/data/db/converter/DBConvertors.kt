package com.chenna.data.db.converter

import androidx.room.TypeConverter
import com.chenna.domain.entities.NetworkEntity
import com.chenna.domain.entities.ShowImageEntity
import com.chenna.domain.entities.ShowRatingEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Chenna Rao on 12/08/24.
 * <p>
 * Frost Interactive
 */

object DateConverter {
    private val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun toNetwork(networkString: String?): NetworkEntity? {
        return if (networkString.isNullOrEmpty()) {
            null
        } else {
            gson.fromJson(networkString, NetworkEntity::class.java)
        }
    }

    @TypeConverter
    @JvmStatic
    fun toRating(ratingString: String?): ShowRatingEntity? {
        return if (ratingString.isNullOrEmpty()) {
            null
        } else {
            gson.fromJson(ratingString, ShowRatingEntity::class.java)
        }
    }

    @TypeConverter
    @JvmStatic
    fun toImage(imageString: String?): ShowImageEntity? {
        return if (imageString.isNullOrEmpty()) {
            null
        } else {
            gson.fromJson(imageString, ShowImageEntity::class.java)
        }
    }

    @TypeConverter
    @JvmStatic
    fun toGenresList(genresString: String?): List<String>? {
        return if (genresString.isNullOrEmpty()) {
            emptyList()
        } else {
            val listType = object : TypeToken<List<String>>() {}.type
            gson.fromJson(genresString, listType)
        }
    }

    @TypeConverter
    @JvmStatic
    fun fromGenresList(genres: List<String>?): String {
        return gson.toJson(genres)
    }

    @TypeConverter
    @JvmStatic
    fun fromRating(rating: ShowRatingEntity?): String {
        return gson.toJson(rating)
    }

    @TypeConverter
    @JvmStatic
    fun fromNetwork(network: NetworkEntity?): String {
        return gson.toJson(network)
    }

    @TypeConverter
    @JvmStatic
    fun fromImage(image: ShowImageEntity?): String {
        return gson.toJson(image)
    }
}