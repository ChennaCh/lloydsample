package com.chenna.lloydsamplepoject.util

/**
 * Created by Chenna Rao on 23/12/24.
 * <p>
 * Frost Interactive
 */
object Constants {
    object DB {
        const val NAME: String = "LLOYD_SAMPLE_APPLICATION_DB"
    }

    enum class AppRoute {
        SHOW_DETAILS
    }

    object Errors {
        const val TV_SHOWS = "TV show details cannot be retrieved"
        const val TV_SHOW_CASTS = "No casts found for the shows"
        const val ERROR = "Error"
        const val CONNECTION_ERROR = "Connection Error"
    }
}