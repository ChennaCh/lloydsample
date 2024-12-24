@file:Suppress("IMPLICIT_CAST_TO_ANY")

package com.chenna.lloydsamplepoject.screens.utils

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.model.ShowModel
import com.chenna.domain.model.toShowModel
import com.chenna.lloydsamplepoject.screens.BookmarkScreen
import com.chenna.lloydsamplepoject.screens.DashboardNavComp
import com.chenna.lloydsamplepoject.screens.ShowDetailsScreen
import com.chenna.lloydsamplepoject.screens.ShowsScreen
import com.chenna.lloydsamplepoject.util.Constants
import com.chenna.lloydsamplepoject.util.NavigationEvent
import com.google.gson.Gson

/**
 * Created by Chenna Rao on 23/12/24.
 * <p>
 * Frost Interactive
 */
object NavigationGraphBuilder {

    @Composable
    fun DashboardGraph(
        navHostController: NavHostController,
        modifier: Modifier = Modifier,
        updateTitle: (title: String) -> Unit,
        updateElevation: (elevation: Dp) -> Unit,
        updateBottomNavVisibility: (Boolean) -> Unit,
        goto: (NavigationEvent) -> Unit,
    ) {
        NavHost(
            navHostController, startDestination = DashboardNavComp.Shows.route, modifier = modifier,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
        ) {
            composable(DashboardNavComp.Shows.route) {
                updateElevation(3.dp)
                updateTitle(DashboardNavComp.Shows.title)
                ShowsScreen() {
                    goto(it)
                }
                updateBottomNavVisibility(true)
            }
            composable(DashboardNavComp.Bookmark.route) {
                updateElevation(0.dp)
                updateTitle(DashboardNavComp.Bookmark.title)
                BookmarkScreen() {
                    goto(it)
                }
                updateBottomNavVisibility(true)
            }

            composable("${Constants.Route.Show_Details}/{showEntity}") { backStackEntry ->
                val serializedShowEntity = backStackEntry.arguments?.getString("showEntity")
                val showModel =
                    serializedShowEntity?.let { Gson().fromJson(it, ShowModel::class.java) }

                showModel?.let {
                    ShowDetailsScreen(showModel = it)
                }
                updateTitle("")
                updateElevation(0.dp)
                updateBottomNavVisibility(false)
            }
        }
    }
}