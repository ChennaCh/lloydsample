package com.chenna.lloydsamplepoject.models

import com.chenna.domain.entities.ShowEntity

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
sealed class TVShowDetailsActionEvent {
    data class SaveBookMark(val entity: ShowEntity) : TVShowDetailsActionEvent()
    data class RemoveBookMark(val id: Int) : TVShowDetailsActionEvent()
    data class IsShowBookmarked(val showId: Int) : TVShowDetailsActionEvent()
}