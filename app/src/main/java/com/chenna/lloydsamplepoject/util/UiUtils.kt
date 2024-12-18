package com.chenna.lloydsamplepoject.util

import android.content.Context
import android.widget.Toast
import com.chenna.domain.entities.CountryModel
import com.chenna.domain.entities.NetWorkModel
import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.entities.ShowImageModel
import com.chenna.domain.entities.ShowRatingModel
import com.chenna.domain.utils.Message
import com.chenna.domain.utils.MessageType

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
object UiUtils {

    fun buildUIMessage(context: Context, it: Message) {
        when (it.messageType) {
            MessageType.TOAST -> {
                buildToast(context.applicationContext, it.message)
            }

            else -> {

            }
        }
    }

    fun buildToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }

    fun staticTVShowsList(): List<ShowEntity> {
        val sampleShows = listOf(
            ShowEntity(
                id = 1,
                name = "Under the Dome",
                language = "English",
                genres = listOf("Drama", "Science-Fiction", "Thriller"),
                status = "Ended",
                runtime = 60,
                rating = ShowRatingModel(average = 6.5f),
                weight = 98,
                type = "Scripted",
                network = NetWorkModel(country = CountryModel(name = "United States")),
                image = ShowImageModel(
                    medium = "https://static.tvmaze.com/uploads/images/medium_portrait/81/202627.jpg",
                    original = "https://static.tvmaze.com/uploads/images/original_untouched/81/202627.jpg"
                ),
                summary = "Under the Dome is the story of a small town sealed off by an enormous dome."
            ),
            ShowEntity(
                id = 2,
                name = "Breaking Bad",
                language = "English",
                genres = listOf("Crime", "Drama", "Thriller"),
                status = "Ended",
                runtime = 47,
                type = "Scripted",
                network = NetWorkModel(country = CountryModel(name = "United States")),
                rating = ShowRatingModel(average = 9.5f),
                weight = 100,
                image = ShowImageModel(
                    medium = "https://static.tvmaze.com/uploads/images/medium_portrait/0/2400.jpg",
                    original = "https://static.tvmaze.com/uploads/images/original_untouched/0/2400.jpg"
                ),
                summary = "A high school chemistry teacher turned methamphetamine producer."
            ),
            ShowEntity(
                id = 3,
                name = "Stranger Things",
                language = "English",
                type = "Scripted",
                network = NetWorkModel(country = CountryModel(name = "United States")),
                genres = listOf("Drama", "Fantasy", "Horror"),
                status = "Running",
                runtime = 51,
                rating = ShowRatingModel(average = 8.8f),
                weight = 99,
                image = ShowImageModel(
                    medium = "https://static.tvmaze.com/uploads/images/medium_portrait/200/500645.jpg",
                    original = "https://static.tvmaze.com/uploads/images/original_untouched/200/500645.jpg"
                ),
                summary = "A group of friends confront supernatural forces in a small town."
            ), ShowEntity(
                id = 4,
                name = "Stranger Things",
                type = "Scripted",
                network = NetWorkModel(country = CountryModel(name = "United States")),
                language = "English",
                genres = listOf("Drama", "Fantasy", "Horror"),
                status = "Running",
                runtime = 51,
                rating = ShowRatingModel(average = 8.8f),
                weight = 99,
                image = ShowImageModel(
                    medium = "https://static.tvmaze.com/uploads/images/medium_portrait/200/500645.jpg",
                    original = "https://static.tvmaze.com/uploads/images/original_untouched/200/500645.jpg"
                ),
                summary = "A group of friends confront supernatural forces in a small town."
            ), ShowEntity(
                id = 5,
                name = "Stranger Things",
                type = "Scripted",
                network = NetWorkModel(country = CountryModel(name = "United States")),
                language = "English",
                genres = listOf("Drama", "Fantasy", "Horror"),
                status = "Running",
                runtime = 51,
                rating = ShowRatingModel(average = 8.8f),
                weight = 99,
                image = ShowImageModel(
                    medium = "https://static.tvmaze.com/uploads/images/medium_portrait/200/500645.jpg",
                    original = "https://static.tvmaze.com/uploads/images/original_untouched/200/500645.jpg"
                ),
                summary = "A group of friends confront supernatural forces in a small town."
            ), ShowEntity(
                id = 6,
                name = "Stranger Things",
                type = "Scripted",
                network = NetWorkModel(country = CountryModel(name = "United States")),
                language = "English",
                genres = listOf("Drama", "Fantasy", "Horror"),
                status = "Running",
                runtime = 51,
                rating = ShowRatingModel(average = 8.8f),
                weight = 99,
                image = ShowImageModel(
                    medium = "https://static.tvmaze.com/uploads/images/medium_portrait/200/500645.jpg",
                    original = "https://static.tvmaze.com/uploads/images/original_untouched/200/500645.jpg"
                ),
                summary = "A group of friends confront supernatural forces in a small town."
            )
        )

        return sampleShows;
    }


}