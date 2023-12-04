package com.example.moviehub.ui.screens.home


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.moviehub.injection.Injection
import com.example.moviehub.ui.UiState
import com.example.moviehub.ui.ViewModelFactory
import com.example.moviehub.ui.screens.content.ErrorScreen
import com.example.moviehub.ui.screens.content.LoadingScreen
import com.example.moviehub.ui.screens.content.MovieGridView

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
    navController: NavHostController,
    modifier: Modifier,
) {
    val homeUiState by homeViewModel.homeUiState.collectAsState(initial = UiState.Loading)

    LaunchedEffect(true) {
        homeViewModel.getMovieList()
    }

    when (val state = homeUiState) {
        is UiState.Loading -> {
            LoadingScreen(modifier = modifier)
        }
        is UiState.Success -> MovieGridView(
            movies = state.data,
            modifier = modifier,
            navController = navController,
            updateMovieState = { movieId, isBookmarked ->
                homeViewModel.updateMovieState(movieId, isBookmarked)
            }
        )
        is UiState.Error -> ErrorScreen(
            retryAction = { homeViewModel.getMovieList() },
            modifier = modifier,
        )
        else -> {}
    }
}