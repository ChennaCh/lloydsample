package com.chenna.lloydsamplepoject.config

/**
 * Created by Chenna Rao on 23/12/24.
 * <p>
 * Frost Interactive
 */
object Constants {

    const val CONNECTION_ERROR: String =
        "We couldn't load the data. Ensure your device is connected to the internet."
    const val SEARCH_INFORMATION = "Discover your favorite TvShows and find detailed information"

    object DB {
        const val NAME: String = "LLOYD_SAMPLE_APPLICATION_DB"
    }

    enum class AppRoute {
        SHOW_DETAILS,
        REDIRECT_TO_WEB,
    }

    object Errors {
        const val TV_SHOWS = "Tv show details cannot be retrieved"
        const val TV_SHOW_CASTS = "No casts found for the shows"
        const val CONNECTION_ERROR = "Connection Error"
        const val NO_BOOKMARKS: String = "No bookmarks added. Try adding from the TvShows list."
        const val NO_SHOWS_FOUND =
            "No results found for the TvShow. Try searching with a different title or more specific terms."
    }
}