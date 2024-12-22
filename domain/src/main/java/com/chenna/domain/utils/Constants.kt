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

    const val CONNECTION_ERROR: String = "Please check the internet connection"
    const val SOMETHING_WENT_WRONG: String = "Something went wrong"
    const val NO_BOOKMARKS: String = "No bookmarks added"

    object Action {
        const val LOGOUT_INTENT = "${BuildConfig.APPLICATION_ID}.INTENT.LOGOUT"
    }

    object Apis {
        const val GET_TV_SHOWS = "shows"
    }

    object Errors {
        const val TV_SHOWS = "TV show details cannot be retrieved"
    }

    enum class AppRoute {
        SHOW_DETAILS,
    }
}