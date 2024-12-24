package com.chenna.lloydsamplepoject.activity

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.chenna.domain.model.ShowModel
import com.chenna.lloydsamplepoject.R
import com.chenna.lloydsamplepoject.screens.DashboardNavComp
import com.chenna.lloydsamplepoject.screens.utils.NavigationGraphBuilder.DashboardGraph
import com.chenna.lloydsamplepoject.ui.theme.LLoydSamplePojectTheme
import com.chenna.lloydsamplepoject.util.Constants
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
    var appBarTitle by remember { mutableStateOf(DashboardNavComp.Shows.title) }
    var elevation: Dp by remember { mutableStateOf(3.dp) }
    var isBottomNavVisible by remember { mutableStateOf(true) }

    MaterialTheme {
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
                                    Log.d("NavigationDebug", "Navigating to: ${navItem.route}")
                                    navController.navigate(navItem.route) {
                                        popUpTo(navController.graph.startDestinationId) {
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
        ) {
            BottomNavGraph(navHostController = navController,
                modifier = Modifier.padding(it),
                updateTitle = { appBarTitle = it },
                updateElevation = { elevation = it },
                updateBottomNavVisibility = { isBottomNavVisible = it }
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "RestrictedApi")
@Composable
fun BottomNavGraph(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    updateTitle: (title: String) -> Unit,
    updateElevation: (elevation: Dp) -> Unit,
    updateBottomNavVisibility: (Boolean) -> Unit,
) {
    DashboardGraph(
        navHostController = navHostController,
        modifier = modifier,
        updateTitle = updateTitle,
        updateElevation = updateElevation,
        updateBottomNavVisibility = updateBottomNavVisibility // Pass the function directly
    ) { navigationEvent ->
        when (navigationEvent.route) {
            Constants.AppRoute.SHOW_DETAILS -> {
                val showModel = navigationEvent.any as? ShowModel ?: return@DashboardGraph
                val serializedShow = Uri.encode(Gson().toJson(showModel))
                navHostController.navigate("${Constants.Route.Show_Details}/$serializedShow")
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
    BottomNavItem("Shows", R.drawable.ic_tvshow, DashboardNavComp.Shows.route),
    BottomNavItem("Bookmarks", R.drawable.ic_watchlist, DashboardNavComp.Bookmark.route)
)

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
