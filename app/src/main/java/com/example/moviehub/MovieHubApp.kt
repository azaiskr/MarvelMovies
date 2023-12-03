package com.example.moviehub


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.moviehub.ui.components.MovieHubBottomBar
import com.example.moviehub.ui.components.MovieHubTopBar
import com.example.moviehub.ui.navigation.Screen
import com.example.moviehub.ui.screens.ProfileScreen
import com.example.moviehub.ui.screens.bookmark.BookmarkScreen
import com.example.moviehub.ui.screens.detail.DetailScreen
import com.example.moviehub.ui.screens.home.HomeScreen
import com.example.moviehub.ui.screens.search.SearchScreen
import com.example.moviehub.ui.theme.Maroon
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieHubApp(
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val systemUiController = rememberSystemUiController()

    systemUiController.setStatusBarColor(
        color = Color(Maroon.value),
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MovieHubTopBar(
                scrollBehavior = scrollBehavior,
                navController = navController,
            )
        },
        bottomBar = {
            if (currentRoute != Screen.DetailMovie.route) {
                MovieHubBottomBar(
                    navController = navController,
                )
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                )
            }
            composable(
                route = Screen.DetailMovie.route,
                arguments = listOf(navArgument("movieId") { type = NavType.StringType }),
            ) {
                val movieId = it.arguments?.getString("movieId") ?: ""
                DetailScreen(
                    movieId = movieId,
                    modifier = Modifier.fillMaxSize(),
                )
            }
            composable(
                route = Screen.Profile.route
            ) {
                ProfileScreen()
            }
            composable(
                route = Screen.Bookmark.route
            ) {
                BookmarkScreen(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                )
            }
            composable(
                route = Screen.Search.route
            ) {
                SearchScreen(
                    navController = navController,
                    modifier = Modifier.fillMaxSize(),
                )
            }
        }
    }

}




