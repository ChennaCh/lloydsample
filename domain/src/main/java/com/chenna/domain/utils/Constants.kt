package com.chenna.domain.utils

import androidx.multidex.BuildConfig

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
object Constants {
    const val BASE_URL = "https://api.tvmaze.com/"

    const val SCREEN_KEY: String = "screen_key"
    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
    private const val IMAGE_SIZE_W185 = "w185"
    private const val IMAGE_SIZE_W780 = "w780"

    const val CONNECTION_ERROR: String = "Please check the internet connection"
    const val SOMETHING_WENT_WRONG: String = "Something went wrong"

    object Action {
        const val LOGOUT_INTENT = "${BuildConfig.APPLICATION_ID}.INTENT.LOGOUT"
    }

    object Apis {
        const val GET_TV_SHOWS = "shows"
        const val GET_SHOW_DETAILS_BY_ID = "shows/"
    }

    object Errors {
        const val TV_SHOWS = "TV show details cannot be retrieved"
    }

    enum class AppRoute {
        SHOW_DETAILS,
    }
}