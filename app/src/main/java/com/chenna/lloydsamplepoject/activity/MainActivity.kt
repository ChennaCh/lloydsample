package com.chenna.lloydsamplepoject.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.TextStyle
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
import com.chenna.lloydsamplepoject.R
import com.chenna.lloydsamplepoject.screens.DashboardNavComp
import com.chenna.lloydsamplepoject.screens.utils.NavigationGraphBuilder.DashboardGraph
import com.chenna.lloydsamplepoject.ui.theme.LLoydSamplePojectTheme
import com.chenna.lloydsamplepoject.util.Constants
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
    var isSearchVisible by remember { mutableStateOf(false) }
    var isShowSearchTextField by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    MaterialTheme {
        Scaffold(
            topBar = {
                if (appBarTitle.isNotEmpty()) {
                    Surface(shadowElevation = elevation) {
                        TopAppBar(
                            title = {
                                if (isShowSearchTextField) {
                                    // Render search input when search is active
                                    OutlinedTextField(
                                        textStyle = TextStyle(
                                            color = Color.Black.copy(alpha = 0.5f),
                                            letterSpacing = TextUnit(0.7F, TextUnitType.Sp),
                                            fontFamily = FontFamily.Default,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight(500)
                                        ),
                                        value = searchQuery,
                                        onValueChange = { searchQuery = it },
                                        placeholder = {
                                            Text(
                                                text = "Search shows...",
                                                letterSpacing = TextUnit(0.7F, TextUnitType.Sp),
                                                color = Color.Gray,
                                                fontFamily = FontFamily.Default,
                                                fontSize = 16.sp
                                            )
                                        },
                                        singleLine = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.White)
                                            .padding(4.dp),
                                        colors = TextFieldDefaults.colors(
                                            unfocusedIndicatorColor = Color.White,
                                            disabledIndicatorColor = Color.White,
                                            focusedIndicatorColor = Color.White,
                                            focusedContainerColor = Color.White,
                                            disabledContainerColor = Color.White,
                                            unfocusedContainerColor = Color.White
                                        )
                                    )
                                } else {
                                    Text(
                                        text = appBarTitle,
                                        fontFamily = FontFamily.SansSerif,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 20.sp,
                                        letterSpacing = TextUnit(1F, TextUnitType.Sp)
                                    )
                                }
                            },
                            actions = {
                                if (appBarTitle == DashboardNavComp.Shows.title) {
                                    IconButton(
                                        onClick = {
                                            isSearchVisible = !isSearchVisible
                                            isShowSearchTextField = !isShowSearchTextField
                                            if (!isSearchVisible) searchQuery =
                                                "" // Reset search query if closed
                                        }
                                    ) {
                                        Icon(
                                            imageVector = if (isShowSearchTextField) Icons.Outlined.Close else Icons.Outlined.Search,
                                            contentDescription = if (isShowSearchTextField) "Close Search" else "Search"
                                        )
                                    }
                                }
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
                updateSearch = {
                    isSearchVisible = it
                    isShowSearchTextField = false
                },
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
    updateSearch: (Boolean) -> Unit,
    updateBottomNavVisibility: (Boolean) -> Unit,
) {
    DashboardGraph(
        navHostController = navHostController,
        modifier = modifier,
        updateTitle = updateTitle,
        updateElevation = updateElevation,
        updateSearch = updateSearch,
        updateBottomNavVisibility = updateBottomNavVisibility // Pass the function directly
    ) { navigationEvent ->
        when (navigationEvent.route) {
            Constants.AppRoute.SHOW_DETAILS -> {
                navHostController.currentBackStackEntry?.savedStateHandle?.set(
                    "showModel",
                    navigationEvent.any
                )
                navHostController.navigate(DashboardNavComp.ShowDetails.route)
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
    BottomNavItem("Cast", R.drawable.ic_cast_crew_icon, DashboardNavComp.Cast.route),
    BottomNavItem("Bookmarks", R.drawable.ic_watchlist, DashboardNavComp.Bookmark.route)
)

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
