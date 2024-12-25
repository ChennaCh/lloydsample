package com.chenna.lloydsamplepoject.models

import com.chenna.domain.model.ShowModel

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
sealed class CastActionEvent {
    data object FetchCasts : CastActionEvent()
}