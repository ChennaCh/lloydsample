package com.chenna.lloydsamplepoject.screens

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chenna.domain.entities.ShowEntity
import com.chenna.domain.utils.Constants
import com.chenna.lloydsamplepoject.R
import com.chenna.lloydsamplepoject.ui.theme.LLoydSamplePojectTheme
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable full-screen mode and customize status/navigation bar
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            LLoydSamplePojectTheme(darkTheme = true) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var elevation: Dp by remember { mutableStateOf(3.dp) }
    var appBarTitle by remember { mutableStateOf("") }
    var isBottomNavVisible by remember { mutableStateOf(true) }

    androidx.compose.material.MaterialTheme {
        Scaffold(
            topBar = {
                if (appBarTitle.isNotEmpty()) {
                    Surface(shadowElevation = elevation) {
                        TopAppBar(
                            title = {
                                Text(
                                    text = appBarTitle,
                                    fontFamily = FontFamily.SansSerif,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 20.sp,
                                    letterSpacing = TextUnit(1F, TextUnitType.Sp)
                                )
                            }, colors = TopAppBarColors(
                                containerColor = Color.White,
                                scrolledContainerColor = Color.Transparent,
                                navigationIconContentColor = Color.Black,
                                titleContentColor = Color.Black,
                                actionIconContentColor = Color.Black
                            ),
                            modifier = Modifier
                                .background(color = Color.White)
                                .shadow(0.dp)
                        )
                    }
                }
            },
            bottomBar = {
                if (isBottomNavVisible) {
                    NavigationBar(
                        containerColor = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = Color.White)
                            .height(120.dp)
                    ) {
                        val currentDestination by navController.currentBackStackEntryAsState()
                        val currentRoute = currentDestination?.destination?.route

                        bottomNavItems.forEach { navItem ->
                            NavigationBarItem(
                                icon = {
                                    Icon(
                                        painter = painterResource(id = navItem.icon),
                                        contentDescription = navItem.label
                                    )
                                },
                                label = {
                                    Text(
                                        navItem.label,
                                        color = Color.Black,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = FontFamily.Default,
                                    )
                                },
                                selected = currentRoute == navItem.route,
                                onClick = {
                                    navController.navigate(navItem.route) {
                                        // Avoid multiple copies of the same destination
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                colors = NavigationBarItemColors(
                                    selectedIconColor = Color.White,
                                    selectedTextColor = Color.Black,
                                    selectedIndicatorColor = Color(0xFFff6823),
                                    unselectedIconColor = Color.Black,
                                    unselectedTextColor = Color(0xFFFFFFFF),
                                    disabledIconColor = Color.Black,
                                    disabledTextColor = Color.Black
                                ),
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = "shows",
                Modifier.padding(paddingValues)
            ) {
                composable("shows") {
                    ShowsScreen { navigationEvent ->
                        if (navigationEvent.route == Constants.AppRoute.SHOW_DETAILS) {
                            val showEntity =
                                navigationEvent.any as? ShowEntity ?: return@ShowsScreen
                            val serializedEntity = Uri.encode(Gson().toJson(showEntity))
                            navController.navigate("showDetails/$serializedEntity")
                        }
                    }
                    appBarTitle = "TVShows"
                    isBottomNavVisible = true
                }
                composable("bookmark") {
                    BookmarkScreen() { navigationEvent ->
                        if (navigationEvent.route == Constants.AppRoute.SHOW_DETAILS) {
                            val showEntity =
                                navigationEvent.any as? ShowEntity ?: return@BookmarkScreen
                            val serializedEntity = Uri.encode(Gson().toJson(showEntity))
                            navController.navigate("showDetails/$serializedEntity")
                        }
                    }
                    appBarTitle = "Bookmarks"
                    isBottomNavVisible = true
                }
                composable("showDetails/{showEntity}") { backStackEntry ->
                    val serializedEntity = backStackEntry.arguments?.getString("showEntity")
                    val showEntity = Gson().fromJson(serializedEntity, ShowEntity::class.java)
                    ShowDetailsScreen(showEntity)
                    appBarTitle = ""
                    isBottomNavVisible = false
                }
            }
        }
    }
}

data class BottomNavItem(
    val label: String,
    val icon: Int,
    val route: String,
)

// Define bottom navigation items
val bottomNavItems = listOf(
    BottomNavItem("Shows", R.drawable.ic_tvshow, "shows"),
    BottomNavItem("Bookmark", R.drawable.ic_watchlist, "bookmark")
)

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
