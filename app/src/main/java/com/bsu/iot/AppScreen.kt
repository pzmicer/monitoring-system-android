package com.bsu.iot

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bsu.iot.ui.HomeScreen
import com.bsu.iot.ui.OptionsScreen

sealed class AppScreen(val route: String, val icon: Int, val titleId: Int) {
    object Home : AppScreen("home", R.drawable.home64, R.string.home_label)
    object Settings : AppScreen("settings", R.drawable.gear64, R.string.settings_label)
}

@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
    )
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        AppScreen.Home,
        AppScreen.Settings,
    )
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(screen.icon),
                        contentDescription = stringResource(screen.titleId)
                    )
                },
                label = {
                    Text(
                        stringResource(screen.titleId),
//                        modifier = Modifier.padding(top = 50.dp)
                    )
                },
                alwaysShowLabel = true,
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) },
        content = { padding ->
            NavHost(
                navController,
                startDestination = AppScreen.Home.route,
                modifier = Modifier.padding(padding)
            ) {
                composable(AppScreen.Home.route) {
                    HomeScreen()
                }
                composable(AppScreen.Settings.route) {
                    OptionsScreen()
                }
            }
        }
    )
}