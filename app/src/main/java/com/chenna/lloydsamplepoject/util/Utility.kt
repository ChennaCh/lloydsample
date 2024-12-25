package com.chenna.lloydsamplepoject.util

import com.chenna.domain.model.CastModel
import com.chenna.domain.model.CountryModel
import com.chenna.domain.model.NetWorkModel
import com.chenna.domain.model.PersonCountryModel
import com.chenna.domain.model.PersonImageModel
import com.chenna.domain.model.PersonModel
import com.chenna.domain.model.ShowImageModel
import com.chenna.domain.model.ShowModel
import com.chenna.domain.model.ShowRatingModel
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Created by Chenna Rao on 25/12/24.
 * <p>
 * Frost Interactive
 */
object Utility {

    fun getTvShowDummyData(): ShowModel {

        return ShowModel(
            id = 1,
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
        )
    }

    fun getCastData(): CastModel {
        return CastModel(
            person = PersonModel(
                id = "1",
                name = "Mike Vogel",
                birthday = "1979-07-17",
                country = PersonCountryModel(
                    name = "United States"
                ),
                gender = "Male",
                image = PersonImageModel(
                    medium = "https://static.tvmaze.com/uploads/images/medium_portrait/0/3.jpg",
                    original = "https://static.tvmaze.com/uploads/images/original_untouched/0/3.jpg"
                )
            )
        )
    }

    fun formatDateToReadable(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("MMM d'st', yyyy", Locale.getDefault())

            val date = inputFormat.parse(dateString)
            if (date != null) {
                val day = SimpleDateFormat("d", Locale.getDefault()).format(date).toInt()
                val suffix = when {
                    day in 11..13 -> "th" // Handle 11th, 12th, 13th
                    day % 10 == 1 -> "st"
                    day % 10 == 2 -> "nd"
                    day % 10 == 3 -> "rd"
                    else -> "th"
                }
                outputFormat.format(date).replace("st", suffix)
            } else {
                dateString // Fallback to the original string if parsing fails
            }
        } catch (e: Exception) {
            dateString // Fallback to the original string if there's an error
        }
    }
}