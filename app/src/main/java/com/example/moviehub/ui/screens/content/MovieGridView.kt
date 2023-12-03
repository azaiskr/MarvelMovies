package com.example.moviehub.ui.screens.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.moviehub.model.MovieItem
import com.example.moviehub.ui.components.MovieItem
import com.example.moviehub.ui.navigation.Screen

@Composable
fun MovieGridView(
    movies: List<MovieItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier,

    updateMovieState: (String, Boolean) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        content = {
            this.items(
                items = movies, key = ({ movies -> movies.movieData.id })
            ) { data ->
                MovieItem(
                    onClick = {
                        navController.navigate(Screen.DetailMovie.createRoute(data.movieData.id))
                    },
                    movie = data,
                    updateMovieState = updateMovieState,
                )
            }
        },
        modifier = modifier.fillMaxWidth()
    )
}