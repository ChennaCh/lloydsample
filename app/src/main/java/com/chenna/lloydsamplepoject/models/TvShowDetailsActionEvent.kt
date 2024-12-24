package com.chenna.lloydsamplepoject.models

import com.chenna.domain.entities.ShowEntity

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
sealed class TvShowDetailsActionEvent {
    data class SaveBookMark(val entity: ShowEntity) : TvShowDetailsActionEvent()
    data class RemoveBookMark(val id: Int) : TvShowDetailsActionEvent()
    data class IsShowBookmarked(val showId: Int) : TvShowDetailsActionEvent()
}