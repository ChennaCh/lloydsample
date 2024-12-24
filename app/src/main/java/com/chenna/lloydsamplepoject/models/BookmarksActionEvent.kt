package com.chenna.lloydsamplepoject.models

import com.chenna.domain.entities.ShowEntity

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
sealed class BookmarksActionEvent {
    data class RedirectToShowDetails(val entity: ShowEntity) : BookmarksActionEvent()
    data object GetBookmarks : BookmarksActionEvent()
}