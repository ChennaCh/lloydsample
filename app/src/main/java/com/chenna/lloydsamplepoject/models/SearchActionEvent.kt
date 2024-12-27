package com.chenna.lloydsamplepoject.models

import com.chenna.domain.models.ShowModel

/**
 * Created by Chenna Rao on 27/12/24.
 * <p>
 * Frost Interactive
 */
sealed class SearchActionEvent {
    data class FetchSearchTvShow(val text: String) : SearchActionEvent()
    data class RedirectToShowDetails(val model: ShowModel) : SearchActionEvent()
}