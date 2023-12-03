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
import com.example.moviehub.ui.UiState
import com.example.moviehub.ui.ViewModelFactory
import com.example.moviehub.ui.screens.content.ErrorScreen
import com.example.moviehub.ui.screens.content.LoadingScreen
import com.example.moviehub.ui.screens.content.MovieGridView

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    bookmarkViewModel: BookmarkViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
) {
    bookmarkViewModel.bookmarkUiState.collectAsState(initial = UiState.Loading).value.let { bookmarkUiState ->
        when (bookmarkUiState) {
            is UiState.Loading -> {
                LoadingScreen(modifier = modifier)
                bookmarkViewModel.showBookmarkedMovies()
            }
            is UiState.Success ->
                if (bookmarkUiState.data.isEmpty()) {
                    EmptyCollection(modifier = modifier.fillMaxSize())
                } else {
                    MovieGridView(
                        movies = bookmarkUiState.data,
                        modifier = modifier,
                        navController = navController,
                        setBookmark = { movieItem ->   bookmarkViewModel.setBookmark(movieItem.movieData.id)}
                    )

                }
            is UiState.Error -> ErrorScreen(
                retryAction = { bookmarkViewModel.showBookmarkedMovies() },
                modifier = modifier,
            )
            else ->{}
        }}
}

@Composable
fun EmptyCollection(
    modifier: Modifier,
) {
    Column (
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Icon(
            painter = painterResource(id = R.drawable.outline_collections_bookmark),
            contentDescription = null,
            modifier = Modifier
                .size(80.dp),
            tint = MaterialTheme.colorScheme.onSurface,
        )
        Text(
            text = stringResource(R.string.bookmark_screen_placeholder),
            modifier = Modifier
                .padding(horizontal = 48.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,)
    }
}