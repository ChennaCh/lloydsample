package com.chenna.lloydsamplepoject.models

import com.chenna.domain.model.ShowModel

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
sealed class TvShowActionEvent {
    data object FetchTvShows : TvShowActionEvent()
    data class RedirectToShowDetails(val model: ShowModel) : TvShowActionEvent()
    data object Retry : TvShowActionEvent()
}