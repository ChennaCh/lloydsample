package com.chenna.domain.config

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
object Constants {
    const val BASE_URL = "https://api.tvmaze.com/"

    const val SCREEN_KEY: String = "screen_key"

    const val CONNECTION_ERROR: String =
        "We couldn't load the data. Ensure your device is connected to the internet."
    const val SOMETHING_WENT_WRONG: String = "Something went wrong"

    object Apis {
        const val GET_TV_SHOWS = "shows"
        const val GET_CASTS = "shows/1/cast"
        const val SEARCH_SHOW = "search/shows"
    }
}