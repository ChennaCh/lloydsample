package com.chenna.lloydsamplepoject.models

import android.content.Context

/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
sealed class CastActionEvent {
    data object FetchCasts : CastActionEvent()
    data object Retry : CastActionEvent()
    data class RedirectToWeb(val url: String, val context: Context) : CastActionEvent()
}