package com.example.moviehub.ui.screens.bookmark

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.moviehub.R
import com.example.moviehub.injection.Injection
import com.example.moviehub.ui.ViewModelFactory
import com.example.moviehub.ui.screens.content.MovieGridView

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    bookmarkViewModel: BookmarkViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
) {
    val movieItem by bookmarkViewModel.movieItem.collectAsState()
    if (movieItem.isEmpty()) {
        EmptyCollection(modifier = modifier.fillMaxSize())
    } else {
        MovieGridView(
            movies = movieItem,
            modifier = modifier,
            navController = navController,
            updateMovieState = { movieId, isBookmarked ->
                bookmarkViewModel.updateMovieState(movieId, isBookmarked)
            }
        )
    }
}

@Composable
fun EmptyCollection(
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.outline_collections_bookmark),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp)
                .padding(bottom = 16.dp),
            tint = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = stringResource(R.string.bookmark_screen_placeholder),
            modifier = Modifier
                .padding(horizontal = 56.dp),
            fontSize = 16.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
        )
    }
}
