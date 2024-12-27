package com.chenna.lloydsamplepoject.screens

/**
 * Created by Chenna Rao on 23/12/24.
 * <p>
 * Frost Interactive
 */
sealed class DashboardNavComp(val route: String, val title: String) {
    data object Shows : DashboardNavComp("dashboard://show", "Shows")
    data object Cast : DashboardNavComp("dashboard://cast", "Casts")
    data object Bookmark : DashboardNavComp("dashboard://bookmark", "Bookmarks")
    data object ShowDetails : DashboardNavComp("dashboard://show/details", "Show Details")
    data object Search : DashboardNavComp("dashboard://show/search", "Search")
}