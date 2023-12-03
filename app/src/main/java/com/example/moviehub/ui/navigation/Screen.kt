package com.example.moviehub.ui.navigation

sealed class Screen(val route: String){
    object Home: Screen("home")
    object DetailMovie: Screen ("home/{movieId}"){
        fun createRoute(movieId: String) = "home/$movieId"
    }
    object Profile: Screen("profile")
    object Bookmark: Screen("bookmark")
    object Search: Screen("search")
}
