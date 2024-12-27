package com.chenna.lloydsamplepoject.util

import com.chenna.lloydsamplepoject.config.Constants


/**
 * Created by Chenna Rao on 17/12/24.
 * <p>
 * Frost Interactive
 */
data class NavigationEvent(val route: Constants.AppRoute, val any: Any? = null)