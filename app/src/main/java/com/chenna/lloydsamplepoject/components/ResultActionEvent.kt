package com.chenna.lloydsamplepoject.components

import com.chenna.domain.entities.ShowEntity

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
sealed class ResultActionEvent {
    data class ResultActionState(
        val list: List<ShowEntity> = listOf(),
        val bookMarks: List<ShowEntity> = listOf(),
    )

    data class RedirectToShowDetails(val entity: ShowEntity) : ResultActionEvent()
    data object GetBookmarks : ResultActionEvent()
    data class SaveBookMark(val entity: ShowEntity) : ResultActionEvent()
    data class RemoveBookMark(val entity: ShowEntity) : ResultActionEvent()
    data class IsShowBookmarked(val showId: Int) : ResultActionEvent()
}