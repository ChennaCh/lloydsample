package com.chenna.data.db.converter

import androidx.room.TypeConverter
import com.chenna.domain.entities.NetWorkModel
import com.chenna.domain.entities.ShowImageModel
import com.chenna.domain.entities.ShowRatingModel
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
    fun toNetwork(networkString: String?): NetWorkModel? {
        return if (networkString.isNullOrEmpty()) {
            null
        } else {
            gson.fromJson(networkString, NetWorkModel::class.java)
        }
    }

    @TypeConverter
    @JvmStatic
    fun toRating(ratingString: String?): ShowRatingModel? {
        return if (ratingString.isNullOrEmpty()) {
            null
        } else {
            gson.fromJson(ratingString, ShowRatingModel::class.java)
        }
    }

    @TypeConverter
    @JvmStatic
    fun toImage(imageString: String?): ShowImageModel? {
        return if (imageString.isNullOrEmpty()) {
            null
        } else {
            gson.fromJson(imageString, ShowImageModel::class.java)
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
    fun fromRating(rating: ShowRatingModel?): String {
        return gson.toJson(rating)
    }

    @TypeConverter
    @JvmStatic
    fun fromNetwork(network: NetWorkModel?): String {
        return gson.toJson(network)
    }

    @TypeConverter
    @JvmStatic
    fun fromImage(image: ShowImageModel?): String {
        return gson.toJson(image)
    }
}